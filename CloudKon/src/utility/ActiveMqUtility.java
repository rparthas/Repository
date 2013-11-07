package utility;

import static utility.Constants.REQUESTQ;
import static utility.Constants.RESPONSEQ;

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

import entity.Task;

public class ActiveMqUtility implements Serializable {

	InitialContext ctx = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActiveMqUtility() {
		buildContext();
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
			PrintManager.PrintMessage("Messages sent");
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

	public void buildContext() {
		try {
			ctx = new InitialContext(formProperties());
		} catch (Exception e) {
			PrintManager.PrintException(e);
		}

	}

	public static Properties formProperties() {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		props.setProperty("queue." + REQUESTQ, REQUESTQ);
		props.setProperty("queue." + RESPONSEQ, RESPONSEQ);
		return props;
	}

}
