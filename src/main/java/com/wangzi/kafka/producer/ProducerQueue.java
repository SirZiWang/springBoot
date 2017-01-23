package com.wangzi.kafka.producer;

import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerQueue {
	
	private static final Logger logger  = LoggerFactory.getLogger(ProducerQueue.class);
	private static LinkedBlockingQueue<String> lbqueue = new LinkedBlockingQueue<String>();
	
	@Autowired
	private ProducerDemo kafkaMessageProducer;
	
	public ProducerQueue(){
		super();
	}
	@PostConstruct
	public void readQueue(){
		new Thread(){
			@Override
			public void run(){
				while(true){
					String msg = null;
					boolean result = false;
					long time = 0;
					try {
						msg = lbqueue.take();
						if(msg != null && msg.trim().length() >0){
							System.out.println("消息队列的消息为：" + msg);
							long t1 = System.currentTimeMillis();
							//发送消息到kafka
							kafkaMessageProducer.sendKafka(msg);
							long t2 = System.currentTimeMillis();
							time = t1 - t2;
						}
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
					logger.info(" LogConsumerQueue send msg : "+ msg+" result: "+result + ",poolSize:" + lbqueue.size()
					+ ",freeMemory:" + Runtime.getRuntime().freeMemory()/1024/1024 + ",threads:" + Thread.getAllStackTraces().size()
					+ ",time:" + time);
				}
			}
		}.start();
	}
	public void putRequest(String req){
		try {
			lbqueue.put(req);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public boolean isEmpty(){
		return lbqueue.isEmpty();
	}
	public String getRequest(){
		return lbqueue.poll();
	}
 } 
