/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * 事务隔离级别
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 */
public enum TransactionIsolationLevel {
  /**
   * 无
   */
  NONE(Connection.TRANSACTION_NONE),
  /**
   * 读已提交
   */
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
  /**
   * 读未提交
   */
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
  /**
   * 可重复读
   */
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
  /**
   * 串行化
   */
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE),
  /**
   * SQL Server 的非标准隔离级别
   * Microsoft SQL Server 的非标准隔离级别。
   * 在 SQL Server JDBC 驱动程序中定义 {@link com.microsoft.sqlserver.jdbc.ISQLServerConnection}
   *
   * @since 3.5.6
   */
  SQL_SERVER_SNAPSHOT(0x1000);

  private final int level;

  TransactionIsolationLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }
}
