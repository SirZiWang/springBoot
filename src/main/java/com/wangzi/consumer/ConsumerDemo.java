package com.wangzi.consumer;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class ConsumerDemo implements Runnable{
	
	private static final  Logger logger = LogManager.getLogger(ConsumerDemo.class);
	private Properties props = new Properties();
	private String topic = null;
	private ConsumerConnector consumer = null;
	private Integer streamSize = 1;
	private Integer analysorSize = 100;
	
	public ConsumerDemo(String properties, Integer streamSize, Integer analysorSize) 
			throws Exception{
		props.load(new FileReader(properties));
		this.topic = props.getProperty("topic");
		ConsumerConfig  config = new ConsumerConfig(props);
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.streamSize = streamSize;
		this.analysorSize = analysorSize;
	}
	@Override
	public void run() {
		Map<String,Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(this.topic, streamSize);
		StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
		Map<String,List<KafkaStream<String, String>>> consumerMap =
				consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		List<KafkaStream<String, String>> streams = consumerMap.get(this.topic);
		KafkaStream<String, String> stream = streams.get(0);
		ConsumerIterator<String, String> it = stream.iterator();
		while(it.hasNext()){
			String msg = null;
			//TODO 消费数据逻辑
			try {
				msg = it.next().message();
			} catch (Exception e) {
				logger.info("Consumer detail:" + e.getMessage());
			}
		}
		
	}

}
