package org.example;

import com.rabbitmq.client.*;

public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // Crear una conexión con RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        for (int i = 1; i <= 10; i++) {
            String message = "Notification " + i;

            System.out.println(" [x] Received '" + message + "'");
        }
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declarar la cola
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            // Callback para recibir los mensajes
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
    }
}
