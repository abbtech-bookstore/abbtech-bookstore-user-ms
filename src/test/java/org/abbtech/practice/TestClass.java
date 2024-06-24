package org.abbtech.practice;

import org.abbtech.practice.solid.inversion.EmailMessageSender;
import org.abbtech.practice.solid.inversion.NotificationService;
import org.abbtech.practice.solid.inversion.NotificationServiceImpl;
import org.abbtech.practice.solid.inversion.SMSMessageSender;
import org.abbtech.practice.solid.liskov.Bird;
import org.abbtech.practice.solid.liskov.BirdUtil;
import org.abbtech.practice.solid.liskov.Parrot;
import org.abbtech.practice.solid.liskov.Penguin;
import org.abbtech.practice.solid.openclose.Connection;
import org.abbtech.practice.solid.openclose.ConnectionServiceImpl;
import org.abbtech.practice.solid.openclose.OracleConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestClass {

    @Test
    void openClose_success() {
        Connection connection = new Connection();
        connection.getConnection("mysql");
        ConnectionServiceImpl connectionService = new ConnectionServiceImpl(new OracleConnection());
        connectionService.getConnection();
    }

    @Test
    void liskov_success() {
        Bird bird = new Bird();
        Parrot parrot = new Parrot();
        Penguin penguin = new Penguin();
        BirdUtil birdUtil = new BirdUtil();
        birdUtil.fly(bird);
        birdUtil.fly(parrot);
        birdUtil.fly(penguin);
    }

    @Test
    void dependencyInversion_success() {
        NotificationService notificationService = new NotificationServiceImpl(new SMSMessageSender());
        NotificationService notificationService1 = new NotificationServiceImpl(new EmailMessageSender());
        notificationService.sendNotification();
        notificationService1.sendNotification();
    }
}
