package br.univali.celine.lms.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import org.apache.commons.dbcp.ConnectionFactory;
//import org.apache.commons.dbcp.DriverManagerConnectionFactory;
//import org.apache.commons.dbcp.PoolableConnectionFactory;
//import org.apache.commons.dbcp.PoolingDriver;
//import org.apache.commons.pool.ObjectPool;
//import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionPool {

	//private String driver;
	private String url;
	private String user;
	private String password;
	//private String poolName;
	
	public ConnectionPool(String poolName, String driver, String url, String user, String password) throws ClassNotFoundException, SQLException {

		//this.poolName = poolName;
		//this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		
		
		Class.forName(driver);
		
		/*
		ObjectPool connectionPool = new GenericObjectPool(null);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, password);
		
		@SuppressWarnings("unused")
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
		
		Class.forName("org.apache.commons.dbcp.PoolingDriver");
		
		PoolingDriver poolingDriver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:"); 
		poolingDriver.registerPool(poolName, connectionPool);
		
		*/
		
	}



	public Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(url, user, password);
		
	}		
}
