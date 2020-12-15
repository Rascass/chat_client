package com.solvd.automation.lab.fall.service;

import com.solvd.automation.lab.fall.dao.MessageDao;
import com.solvd.automation.lab.fall.dao.impl.sqlite.MessageImplSqlite;
import com.solvd.automation.lab.fall.io.PropertyReader;

import java.util.Map;

public abstract class BaseService {
    protected static final PropertyReader PROPERTY_READER = PropertyReader.getInstance();

    protected static final Map<String, MessageDao> MESSAGE_DAOS = Map.of(
            "sqlite", new MessageImplSqlite()
    );
}
