package mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author: liaosijun
 * @Time: 2020/3/4 10:51
 */
public class Consumer {

	public static void main(String[] args) {

		// 创建consumer，名为consumer
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer");
		// 指定nameserver的地址
		consumer.setNamesrvAddr("localhost:9876");
		try {
			// 设置要订阅的消息，orders为topic，*为该topic下的所有消息
			consumer.subscribe("orders", "*");
			// 设置消费位
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
					// 通过getBody()输出消息实体内容
					System.out.printf(Thread.currentThread().getName() + "收到新消息：" + new String(msgs.get(0).getBody()) + "%n");
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			// 开启服务
			consumer.start();
			System.out.println("消费者方已经启动...");
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		// 关闭服务
		consumer.shutdown();
	}
}
