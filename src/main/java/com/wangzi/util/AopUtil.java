package com.wangzi.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wangzi.kafka.producer.ProducerQueue;

import net.sf.json.JSONObject;

@Aspect
@Component
public class AopUtil {
	
	@Autowired
	private ProducerQueue producerQueue;
	
	@Pointcut("execution(public * com.wangzi.controller..*.*(..))")
	public void intercepterAop(){}
	
	@Around("intercepterAop()")
	public Object doAround(ProceedingJoinPoint pjp){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		int status = attributes.getResponse().getStatus();
		String url = request.getRequestURI().toString();
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String parameter = request.getQueryString();
		String ip = getIpAddr(request);
		Object[] paramsArray = pjp.getArgs();//包括get和post的参数
		String reqBody=Arrays.toString(paramsArray);//当Object数组内的数据都是一个类型时，得到的是个数组；当不一致时得到的是一个String
		//用这种方式可以保证得到的是String,所有参数用#分隔
		
		System.out.println("url:" + url);
		System.out.println("method:" + method);
		System.out.println("parameter:" + parameter);
		System.out.println("ip:"+ip);
		Object result=null;
		long startTime=System.currentTimeMillis();//开始时间
		String timestamp=new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String exception=null;
		try {
			// result的值就是被拦截方法的返回值
			result = pjp.proceed();
		} catch (Throwable e) {
			 exception=e.getMessage();
		}
		long endTime=System.currentTimeMillis();
		
		long dealTime=endTime-startTime;//处理时间

		System.out.println("result:" + result);
		
		JSONObject jsonKafka = new JSONObject();
		jsonKafka.put("uri", uri);
		jsonKafka.put("parameter", parameter);
		jsonKafka.put("reqBody", reqBody);
		jsonKafka.put("respBody", result);
		jsonKafka.put("status", status);
		jsonKafka.put("method", method);
		jsonKafka.put("dealTime", dealTime);
		jsonKafka.put("timestamp", timestamp);
		jsonKafka.put("ip", ip);
		jsonKafka.put("exception", exception);
		System.out.println("Json------" + jsonKafka.toString());
		producerQueue.putRequest(jsonKafka.toString());
		return result;
	}
	
	/**
	 * 获取登录用户远程主机ip地址
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
