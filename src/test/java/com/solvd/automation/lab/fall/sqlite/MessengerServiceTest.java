package com.solvd.automation.lab.fall.sqlite;

import com.solvd.automation.lab.fall.domain.message.Message;
import com.solvd.automation.lab.fall.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


import java.util.List;


public class MessengerServiceTest {

    private static final Logger LOGGER = LogManager.getLogger();

    MessageService messageService = new MessageService();
    Message message = new Message(false, "Hello", 14, 22);

    @Test
    public void create() {
        messageService.create(message);
    }

    @Test
    public void findById() {
        Message actualMessage = messageService.findById(12);
        LOGGER.info(actualMessage.toString());
    }

    @Test
    public void findAll() {
        List<Message> messages = messageService.findAll();
        LOGGER.info("Test messages list: " + messages);
    }

    @Test
    public void update() {
        message.setId(13);
        message.setContent("Goodbye");

        LOGGER.info(messageService.update(message).toString());
    }

    @Test
    public void deleteById() {
        messageService.deleteById(14);
    }
}
