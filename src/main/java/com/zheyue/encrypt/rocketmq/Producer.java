package com.zheyue.encrypt.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * @author FD
 * @date 2016/12/2
 */
public class Producer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        //nameserver服务,多个以;分开
        producer.setNamesrvAddr("192.168.133.128:9876");
        try{
            producer.start();

            Message msg = new Message("PushTopic","push","1","Just for test.".getBytes());
            SendResult result = producer.send(msg);
            System.out.println("id:"+result.getMsgId()+" result:" +result.getSendStatus());;

            msg = new Message("PushTopic","push","2","Just for test.".getBytes());
            result = producer.send(msg);
            System.out.println("id:"+result.getMsgId()+" result:" +result.getSendStatus());;

            msg = new Message("PullTopic","pull","1","Just for test.".getBytes());
            result = producer.send(msg);
            System.out.println("id:"+result.getMsgId()+" result:" +result.getSendStatus());;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            producer.shutdown();
        }
    }
}
