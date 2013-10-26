package queue;

import java.io.FileInputStream;
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

import utility.Constants;
import entity.QueueDetails;
import entity.Task;

public class ActiveMQ implements DistributedQueue, TaskQueue {

	@Override
	public void pushToQueue(QueueDetails queueDetails) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Session session = null;
		try (FileInputStream fis = new FileInputStream("activemq.properties")) {
			Properties props = new Properties();
			props.load(fis);
			InitialContext ctx = new InitialContext(props);
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
					queueDetails.getUrl());
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup(Constants.MASTER);
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage(queueDetails);
			producer.send(msg);
			System.out.println("Messages sent to Distributed ActiveMq");
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

	@Override
	public QueueDetails pullFromQueue() {
		// TODO Auto-generated method stub
		QueueDetails details = null;
		Connection connection = null;
		Session session = null;
		try (FileInputStream fis = new FileInputStream("activemq.properties")) {
			Properties props = new Properties();
			props.load(fis);
			InitialContext ctx = new InitialContext(props);
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
					props.getProperty("distributedQueueUrl"));
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup(Constants.MASTER);
			MessageConsumer consumer = session.createConsumer(queue);
			Message message = consumer.receive();

			if (message instanceof ObjectMessage) {
				ObjectMessage obj = (ObjectMessage) message;
				details = (QueueDetails) obj.getObject();
			}
			System.out.println("Messages received from Distributed ActiveMq");
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
		return details;
	}

	@Override
	public Task retrieveTask(String qName, String url) {
		// TODO Auto-generated method stub
		Task task = null;
		Connection connection = null;
		Session session = null;
		try (FileInputStream fis = new FileInputStream("activemq.properties")) {
			Properties props = new Properties();
			props.load(fis);
			InitialContext ctx = new InitialContext(props);
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

	@Override
	public void postTask(List<Task> objects, String qName, String url) {
		// TODO Auto-generated method stub

		Connection connection = null;
		Session session = null;
		try (FileInputStream fis = new FileInputStream("activemq.properties")) {
			Properties props = new Properties();
			props.load(fis);
			InitialContext ctx = new InitialContext(props);
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
			System.out.println("Messages sent to Task Queue");
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

}
