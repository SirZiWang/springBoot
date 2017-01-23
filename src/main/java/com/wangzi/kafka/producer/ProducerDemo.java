package com.wangzi.kafka.producer;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
/**
 * 详细可以参考：https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+Producer+
 * @author ziwang
 *
 */
@Component
public class ProducerDemo {
	private Logger logger = LogManager.getLogger(ProducerDemo.class);
	private Producer<String, String> producer;
	private static final String TOPIC = "springBoot";
	private static final String BROKERLIST = "127.0.0.1:9092";
	public ProducerDemo(){
		//设置属性
		Properties properties = new Properties();
		properties.put("metadata.broker.list", BROKERLIST);
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		properties.put("key.serializer.class", "kafka.serializer.StringEncoder");
		properties.put("partitioner.class", "kafka.producer.DefaultPartitioner");
		properties.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(properties);
		producer = new Producer<String, String>(config);//创建poducer
	}
	
	public boolean sendKafka(String msg){
		long start = System.currentTimeMillis();
		// 如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(
				TOPIC, msg);
		producer.send(data);
		logger.info(msg + ",最终的耗时:" + (System.currentTimeMillis() - start)
				+ "毫秒");
		return true;
	} 
	/*public static void main(String[] args) {
		ProducerDemo producerDemo = new ProducerDemo();
		String msg = "{\"name\":\"zhangsan\", \"age\":23}";
		producerDemo.sendKafka(msg);
	}*/
}
