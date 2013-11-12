package queue;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Semaphore;

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
import utility.PrintManager;
import entity.QueueDetails;
import entity.Task;

public class ActiveMQ implements DistributedQueue, TaskQueue {

	@Override
	public void pushToQueue(QueueDetails queueDetails) {
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
			PrintManager.PrintMessage("Messages sent to Distributed ActiveMq");
		} catch (Exception e) {
			PrintManager.PrintException(e);
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}
		}
	}

	@Override
	public QueueDetails pullFromQueue() {
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
			PrintManager.PrintMessage("Messages received from Distributed ActiveMq");
		} catch (Exception e) {
			PrintManager.PrintException(e);
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}
		}
		return details;
	}

	@Override
	public Task retrieveTask(String qName, String url,String clientId) {
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
			PrintManager.PrintMessage("Messages received");
		} catch (Exception e) {
			PrintManager.PrintException(e);
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}
		}
		return task;
	}

	@Override
	public void postTask(Semaphore objSemaphore,Set<Task> objects, String qName, String url,String clientId) {
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
			PrintManager.PrintMessage("Messages sent to Task Queue");
		} catch (Exception e) {
			PrintManager.PrintException(e);
		} finally {
			try {
				session.close();
				connection.stop();
				connection.close();
			} catch (Exception e) {
				PrintManager.PrintException(e);
			}
		}
	}

}
