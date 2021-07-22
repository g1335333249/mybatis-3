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
 * 从连接或数据源创建 {@link SqlSession}
 *
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 */
public interface SqlSessionFactory {

  /**
   * 打开数据库连接
   * @return
   */
  SqlSession openSession();

  /**
   * 打开数据库连接
   * @param autoCommit 是否自动提交
   * @return
   */
  SqlSession openSession(boolean autoCommit);

  /**
   * 打开数据库连接
   * @param connection 指定数据库连接
   * @return
   */
  SqlSession openSession(Connection connection);

  /**
   * 打开数据库连接
   * @param level 事务隔离级别
   * @return
   */
  SqlSession openSession(TransactionIsolationLevel level);

  /**
   * 打开数据库连接
   * @param execType 执行类型
   * @return
   */
  SqlSession openSession(ExecutorType execType);

  /**
   *
   * @param execType 执行类型
   * @param autoCommit 是否自动提交
   * @return
   */
  SqlSession openSession(ExecutorType execType, boolean autoCommit);

  /**
   * 打开数据库连接
   * @param execType 执行类型
   * @param level 事务隔离级别
   * @return
   */
  SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

  /**
   * 打开数据库连接
   * @param execType 执行类型
   * @param connection 指定数据库连接
   * @return
   */
  SqlSession openSession(ExecutorType execType, Connection connection);

  /**
   * 获取配置
   * @return
   */
  Configuration getConfiguration();

}
