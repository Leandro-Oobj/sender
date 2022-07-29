package escalar.oobj.avaliacao.mensageria;


import escalar.oobj.avaliacao.io.CriaSaida;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.IOException;

public class Receiver implements Runnable{

    @Override
    public void run() {
        synchronized (this){

        InitialContext context = null;
        try {
            context = new InitialContext();
            ConnectionFactory factory = null;
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");
            Connection connection = null;
            connection = factory.createConnection();
            connection.start();
            Session session = null;
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination fila = null;
            fila = (Destination) context.lookup("sender");
            MessageConsumer consumer = null;
            consumer = session.createConsumer(fila);
            Message message = null;
            message = consumer.receive(500);
            TextMessage textMessage = (TextMessage) message;

            session.close();
            connection.close();
            context.close();


            if (textMessage != null) {
                CriaSaida criaSaida = new CriaSaida();
                criaSaida.criaSaida(textMessage.getText());
                run();
            }else {
                return;
            }

        } catch (NamingException | JMSException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    }
}
