package queue;

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

import utility.QueueDetails;

public class ActiveMQ implements DistributedQueue {

	@Override
	public void pushToQueue(QueueDetails queueDetails) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Session session = null;
		try {
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			props.setProperty("queue.master","master");
			InitialContext ctx = new InitialContext(props);
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup("master");
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage(queueDetails);
			producer.send(msg);
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
			ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = cf.createQueueConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			Queue queue = (Queue) ctx.lookup("master");
			MessageConsumer consumer = session.createConsumer(queue);
			Message message = consumer.receive();
		
			if (message instanceof ObjectMessage) {
				ObjectMessage obj = (ObjectMessage) message;
				details = (QueueDetails) obj.getObject();
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
		return details;
	}

	

}
