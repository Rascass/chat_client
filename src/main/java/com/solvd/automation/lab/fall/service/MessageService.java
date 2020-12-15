package com.solvd.automation.lab.fall.service;

import com.solvd.automation.lab.fall.dao.MessageDao;
import com.solvd.automation.lab.fall.dao.impl.sqlite.MessageImplSqlite;
import com.solvd.automation.lab.fall.domain.Message;
import com.solvd.automation.lab.fall.util.SqliteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageService extends BaseService{
    private static final Logger LOGGER = LoggerFactory.getLogger(SqliteHandler.class);

    MessageDao messageDao = new MessageImplSqlite();

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
