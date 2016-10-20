package com.cf.persistence;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceMapper<T> {

	public void insert(T b) throws SQLException; 
	public void delete(T b) throws SQLException;
	public void update(T b) throws SQLException;
	public List<T> find() throws SQLException;
	public T findById(int id) throws SQLException;


}