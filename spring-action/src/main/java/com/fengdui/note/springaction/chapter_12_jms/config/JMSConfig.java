package com.fengdui.note.springaction.chapter_12_jms.config;

import com.fengdui.note.springaction.chapter_12_jms.MyListener;
import com.fengdui.note.springaction.chapter_12_jms.listener.BatchMessageListenerContainer;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * @author FD
 * @date 2017/3/24
 */
@Configuration
public class JMSConfig {
    @Bean
    public JndiTemplate jndiTemplate() {
        Properties prop = new Properties();
        prop.put("java.naming.factory.initial", 'A');
        prop.put("java.naming.provider.url", 'A');
        prop.put("java.naming.factory.url.pkgs", 'A');
        return new JndiTemplate(prop);
    }

    @Bean
    public ConnectionFactory connectionFactory(JndiTemplate jndiTemplate) throws IllegalArgumentException, NamingException {
        ConnectionFactory connectionFactory = null;

//        jndi方式连接
//        if(Constants.NON_CLUSTERED.equals(jmsType)){
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("/ConnectionFactory");
        bean.setJndiTemplate(jndiTemplate);
        bean.afterPropertiesSet();
        connectionFactory = (ConnectionFactory) bean.getObject();
//        }
//        else if(Constants.CLUSTERED.equals(jmsType)){
//            if (urls == null || urls.size() == 0) {
//                return null;
//            }
//            List<TransportConfiguration> servers = new ArrayList<TransportConfiguration>();
//            for (String url : urls) {
//                if (url != null && url.contains(":")) {
//                    String[] turl = url.split(":");
//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    map.put("host", turl[0]);
//                    map.put("port", turl[1]);
//                    TransportConfiguration server = new TransportConfiguration(NettyConnectorFactory.class.getName(), map);
//                    servers.add(server);
//                }
//            }
//        netyy方式连接
        TransportConfiguration server = new TransportConfiguration(NettyConnectorFactory.class.getName());
        HornetQConnectionFactory hConnectionFactory = HornetQJMSClient.createConnectionFactoryWithHA(JMSFactoryType.CF, server);
//            hConnectionFactory.setConnectionTTL(connectionTTL);
//            hConnectionFactory.setConsumerWindowSize(consumerWindowSize);
//	        hConnectionFactory.setRetryInterval(1000);
//	        hConnectionFactory.setRetryIntervalMultiplier(1.5);
//	        hConnectionFactory.setMaxRetryInterval(60000);
//	        hConnectionFactory.setReconnectAttempts(1000);

        connectionFactory = (ConnectionFactory) hConnectionFactory;
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachedConnectionFactory(@Qualifier("connectionFactory") ConnectionFactory factory) {
        CachingConnectionFactory cFactory = new CachingConnectionFactory();
        cFactory.setTargetConnectionFactory(factory);
        cFactory.setSessionCacheSize(10);
        cFactory.setReconnectOnException(true);
        cFactory.setCacheConsumers(true);
        cFactory.setCacheProducers(true);
        return cFactory;
    }

    @Bean
    public BatchMessageListenerContainer batchMessageListener(@Qualifier("cachedConnectionFactory") CachingConnectionFactory cFactory, MyListener listener) {
        BatchMessageListenerContainer container = new BatchMessageListenerContainer();
        container.setConnectionFactory(cFactory);
        container.setDestinationName("destination");
        //设置动态的线程数
        container.setConcurrency("10");
//        //设置固定的线程数
//        container.setConcurrentConsumers(concurrentConsumers);
//        //设置最大的线程数
//        container.setMaxConcurrentConsumers(maxConcurrentConsumers);

        container.setMessageListener(listener);
        return container;
    }
}
