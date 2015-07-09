package com.skcoe18.core.http.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract interface JdbcConnectionFactoryInterface
{
  public abstract void init(Properties paramProperties)
    throws SQLException;

  public abstract Connection getConnection(Properties paramProperties)
    throws SQLException;

  public abstract void destroy();
}

