package com.solvd.automation.lab.fall.sqlite;

import com.solvd.automation.lab.fall.domain.Message;
import com.solvd.automation.lab.fall.service.MessageService;
import com.solvd.automation.lab.fall.util.SqliteHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.OffsetDateTime;
import java.util.List;


public class MessengerServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteHandler.class);

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
