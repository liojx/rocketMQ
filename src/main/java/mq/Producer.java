package mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author: liaosijun
 * @Time: 2020/3/4 10:47
 */
public class Producer {

	public static void main(String[] args) throws Exception {
		// 创建一个producer。名为group
		DefaultMQProducer producer = new DefaultMQProducer("group");
		// 指定nameserver的地址
		producer.setNamesrvAddr("localhost:9876");
		// 启动服务
		producer.start();
		for (int i = 1; i <= 10; i++) {
			// 创建消息，orders为topic，相当于给消息分类，("order"+i).getBytes()为消息实体
			Message message = new Message("orders", ("order" + i).getBytes());
			// 发送消息
			SendResult result = producer.send(message);
			System.out.println(result);
			System.out.println(message + "send out");
			Thread.sleep(500);
		}
		// 关闭服务
		producer.shutdown();
	}
}
