package com.solvd.automation.lab.fall.service;

import com.solvd.automation.lab.fall.constant.PropertyConstant;
import com.solvd.automation.lab.fall.dao.MessageDao;
import com.solvd.automation.lab.fall.domain.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageService extends BaseService{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    MessageDao messageDao = MESSAGE_DAOS.get(PROPERTY_READER.getValue(PropertyConstant.ENV_KEY));

    public Message create(Message message) {
        LOGGER.info("Creating message: " + message);
        return messageDao.create(message);
    }

    public Message findById(int id) {
        LOGGER.info("Finding message with id: " + id);
        return messageDao.findById(id);
    }

    public List<Message> findAll() {
        LOGGER.info("Finding all records in messages");
        return messageDao.findAll();
    }

    public Message update(Message message) {
        LOGGER.info("Updating message: " + message);
        return messageDao.update(message);
    }

    public int deleteById(int id) {
        LOGGER.info("Deleting message with id: " + id);
        return messageDao.deleteById(id);
    }
}
