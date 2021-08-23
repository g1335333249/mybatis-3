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
package org.apache.ibatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 包装一个数据库连接。
 * 处理连接生命周期，包括：创建、准备、提交回滚和关闭。
 *
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 */
public interface Transaction {

  /**
   * 检索内部数据库连接。
   * @return DataBase connection
   * @throws SQLException
   *           SQL异常
   */
  Connection getConnection() throws SQLException;

  /**
   * 提交内部数据库连接。
   * @throws SQLException
   *           SQL异常
   */
  void commit() throws SQLException;

  /**
   * 回滚内部数据库连接。
   * @throws SQLException
   *           SQL异常
   */
  void rollback() throws SQLException;

  /**
   * 关闭内部数据库连接。
   * @throws SQLException
   *           SQL异常
   */
  void close() throws SQLException;

  /**
   * 如果设置，则获取事务超时。
   *
   * @return 超时
   * @throws SQLException
   *           SQL异常
   */
  Integer getTimeout() throws SQLException;

}
