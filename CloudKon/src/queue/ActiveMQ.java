package queue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

import utility.QueueDetails;

public class ActiveMQ implements DistributedQueue {

	@Override
	public void pushToQueue(ByteArrayOutputStream bos) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Session session = null;
		try {
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			props.setProperty("queue.master","master");
			InitialContext ctx = new InitialContext(props);
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("");
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup("master");
			MessageProducer producer = session.createProducer(queue);
			BytesMessage message = session.createBytesMessage();
			message.setStringProperty("byte", bos.toString());
			producer.send(message);
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

	@Override
	public QueueDetails pullFromQueue() {
		// TODO Auto-generated method stub
		QueueDetails details =null;
		Connection connection = null;
		Session session = null;
		try {
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			props.setProperty("queue.master","master");
			InitialContext ctx = new InitialContext(props);
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("");
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup("");
			MessageConsumer consumer = session.createConsumer(queue);
			Message message = consumer.receive();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(message
				.getStringProperty("byte").getBytes());
		ObjectInputStream oInputStream = new ObjectInputStream(bis);
		Object obj = oInputStream.readObject();
		if (obj instanceof QueueDetails) {
			 details = (QueueDetails) obj;
		}
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

	

}
