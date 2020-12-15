package com.solvd.automation.lab.fall.dao;

import java.util.List;

public interface DAO <T>{
    T create(T o);
    T findById(int id);
    List<T> findAll();
    T update(T o);
    int deleteById(int id);
}
