package com.solvd.automation.lab.fall.dao.impl.sqlite;

import com.solvd.automation.lab.fall.config.SessionFactory;
import com.solvd.automation.lab.fall.dao.MessageDao;
import com.solvd.automation.lab.fall.domain.message.Message;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MessageImplSqlite implements MessageDao {

    private final static String namespace = "message_mapper";

    @Override
    public Message create(Message o) {
        SqlSession session = SessionFactory.getSession();
        session.insert(namespace + ".create", o);
        session.commit();
        session.close();
        return o;
    }

    @Override
    public Message findById(int id) {
        SqlSession session = SessionFactory.getSession();
        Message message = session.selectOne(namespace + ".findById", id);
        session.close();
        return message;
    }

    @Override
    public List<Message> findAll() {
        SqlSession session = SessionFactory.getSession();
        List<Message> list = session.selectList(namespace + ".findAll");
        session.close();
        return list;
    }

    @Override
    public Message update(Message o) {

        SqlSession session = SessionFactory.getSession();
        session.update(namespace + ".update", o);
        session.commit();
        session.close();
        return o;
    }

    @Override
    public int deleteById(int id) {
        SqlSession session = SessionFactory.getSession();
        session.delete(namespace + ".deleteById", id);
        session.commit();
        session.close();
        return id;

    }
}
