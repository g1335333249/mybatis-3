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
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;

/**
 * 创建 {@link Transaction} 实例。
 *
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 */
public interface TransactionFactory {

  /**
   * 设置事务工厂自定义属性。
   * @param props
   *          新的属性
   */
  default void setProperties(Properties props) {
    // NOP
  }

  /**
   * 从现有连接创建 {@link Transaction}。
   * @param conn 现有数据库连接
   * @return Transaction
   * @since 3.1.0
   */
  Transaction newTransaction(Connection conn);

  /**
   * 从数据源创建 {@link Transaction}。
   * @param dataSource 从中获取连接的数据源
   * @param level 所需的隔离级别
   * @param autoCommit 是否自动提交
   * @return Transaction
   * @since 3.1.0
   */
  Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
