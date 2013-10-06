package Utility;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

import client.Task;

public class QueueUtility implements Serializable {

	InitialContext ctx = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueueUtility()  {
	
	}
	
	public QueueUtility(Properties prop){
		buildContext(prop);
	}

	public Task retrieveMessage(String qName, String url) {
		Task task = null;
		Connection connection = null;
		Session session = null;
		try {

			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
			connection.start();
			Queue queue = (Queue) ctx.lookup(qName);
			MessageConsumer consumer = session.createConsumer(queue);
			Message message = consumer.receive();
			if (message instanceof ObjectMessage) {
				ObjectMessage obj = (ObjectMessage) message;
				task = (Task) obj.getObject();
			}
			System.out.println("Messages received");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return task;
	}

	public void postMessage(List<Task> objects, String qName, String url) {
		Connection connection = null;
		Session session = null;
		try {
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup(qName);
			MessageProducer producer = session.createProducer(queue);
			for (Task obj : objects) {
				ObjectMessage msg = session.createObjectMessage(obj);
				producer.send(msg);
			}
			System.out.println("Messages sent");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void buildContext(Properties props){
		try{
			 ctx = new InitialContext(props);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
