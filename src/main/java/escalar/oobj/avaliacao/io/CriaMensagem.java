package escalar.oobj.avaliacao.io;


import escalar.oobj.avaliacao.mensageria.Receiver;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CriaMensagem {

    public String criaMensagem(String processado) throws NamingException, JMSException {

        synchronized (this) {

            InitialContext context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination fila = (Destination) context.lookup("sender");

            MessageProducer producer = session.createProducer(fila);

            Message message = session.createTextMessage(processado);
            producer.send(message);

            session.close();
            connection.close();
            context.close();

            return processado;
        }
    }
}
