package org.example;

import com.rabbitmq.client.*;

public class NotificationProducer {
    private final static String QUEUE_NAME = "notifications";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {


            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 1; i <= 10; i++) {
                String message = "Notification " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}

