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

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;

/**
 * 与MyBatis一起使用的主要Java接口。
 * 通过此接口，您可以执行命令，获取mappers和事务管理。
 *
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 */
public interface SqlSession extends Closeable {

  /**
   * 查询一条结果
   * @param <T> the returned object type 返回的对象类型
   * @param statement 查询的SQL语句
   *          the statement
   * @return Mapped object
   */
  <T> T selectOne(String statement);

  /**
   * 查询一条结果
   * @param <T> the returned object type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @return Mapped object
   */
  <T> T selectOne(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key.
   * 查询列表结果
   * @param <E> the returned list element type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param <E> the returned list element type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter,
   * within the specified row bounds.
   * @param <E> the returned list element type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @param rowBounds  Bounds to limit object retrieval
   * @return List of mapped object
   */
  <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

  /**
   * The selectMap is a special case in that it is designed to convert a list
   * of results into a Map based on one of the properties in the resulting
   * objects.
   * Eg. Return a of Map[Integer,Author] for selectMap("selectAuthors","id")
   * @param <K> the returned Map keys type
   * @param <V> the returned Map values type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param mapKey The property to use as key for each value in the list.
   * @return Map containing key pair data.
   */
  <K, V> Map<K, V> selectMap(String statement, String mapKey);

  /**
   * The selectMap is a special case in that it is designed to convert a list
   * of results into a Map based on one of the properties in the resulting
   * objects.
   * @param <K> the returned Map keys type
   * @param <V> the returned Map values type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @param mapKey The property to use as key for each value in the list.
   * @return Map containing key pair data.
   */
  <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

  /**
   * The selectMap is a special case in that it is designed to convert a list
   * of results into a Map based on one of the properties in the resulting
   * objects.
   * @param <K> the returned Map keys type
   * @param <V> the returned Map values type
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @param mapKey The property to use as key for each value in the list.
   * @param rowBounds  Bounds to limit object retrieval
   * @return Map containing key pair data.
   */
  <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
   * @param <T> the returned cursor element type.
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
   * @param <T> the returned cursor element type.
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement, Object parameter);

  /**
   * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
   * @param <T> the returned cursor element type.
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @param rowBounds  Bounds to limit object retrieval
   * @return Cursor of mapped objects
   */
  <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds);

  /**
   * Retrieve a single row mapped from the statement key and parameter
   * using a {@code ResultHandler}.
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @param handler ResultHandler 将处理每个检索到的行
   */
  void select(String statement, Object parameter, ResultHandler handler);

  /**
   * Retrieve a single row mapped from the statement
   * using a {@code ResultHandler}.
   * @param statement 与要使用的语句匹配的唯一标识符。
   * @param handler ResultHandler 将处理每个检索到的行
   */
  void select(String statement, ResultHandler handler);

  /**
   * 使用 {@code ResultHandler} 检索从语句键和参数映射的单行
   * {@code RowBounds}。
   *
   * @param statement
   *          与要使用的语句匹配的唯一标识符。
   * @param parameter
   *          查询语句
   * @param rowBounds
   *          RowBound 实例限制查询结果
   * @param handler
   *          ResultHandler 将处理每个检索到的行
   */
  void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);

  /**
   * 执行插入语句。
   * @param statement 与要执行的语句匹配的唯一标识符。
   * @return int 受插入影响的行数。
   */
  int insert(String statement);

  /**
   * 使用给定的参数对象执行插入语句。 任何生成
   * 自动增量值或 selectKey 条目将修改给定的参数
   * 对象属性。 仅返回受影响的行数。
   * @param statement 与要执行的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @return int 受插入影响的行数。
   */
  int insert(String statement, Object parameter);

  /**
   * 执行更新语句，将返回受影响的行数。
   * @param statement 与要执行的语句匹配的唯一标识符。
   * @return int 受更新影响的行数。
   */
  int update(String statement);

  /**
   * 执行更新语句，将返回受影响的行数。
   * @param statement 与要执行的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @return int 受更新影响的行数。
   */
  int update(String statement, Object parameter);

  /**
   * 执行删除语句，将返回受影响的行数。
   * @param statement 与要执行的语句匹配的唯一标识符。
   * @return int 受删除影响的行数。
   */
  int delete(String statement);

  /**
   * 执行删除语句，将返回受影响的行数。
   * @param statement 与要执行的语句匹配的唯一标识符。
   * @param parameter 要传递给语句的参数对象。
   * @return int 受删除影响的行数。
   */
  int delete(String statement, Object parameter);

  /**
   * 刷新批处理语句并提交数据库连接。
   * 请注意，如果没有调用更新/删除/插入，则不会提交数据库连接。
   * 强制提交调用 {@link SqlSession#commit(boolean)}
   */
  void commit();

  /**
   * 刷新批处理语句并提交数据库连接。
   * @param force 强制连接提交
   */
  void commit(boolean force);

  /**
   * 丢弃挂起的批处理语句并回滚数据库连接。
   * 请注意，如果没有调用更新/删除/插入，数据库连接将不会回滚。
   * 强制回滚调用 {@link SqlSession#rollback(boolean)}
   */
  void rollback();

  /**
   * 丢弃挂起的批处理语句并回滚数据库连接。
   * 请注意，如果没有调用更新/删除/插入，数据库连接将不会回滚。
   * @param force 强制回滚
   */
  void rollback(boolean force);

  /**
   * 刷新批处理语句。
   * @return 更新记录的 BatchResult 列表
   * @since 3.0.6
   */
  List<BatchResult> flushStatements();

  /**
   * 关闭会话。
   */
  @Override
  void close();

  /**
   * 清除本地会话缓存。
   */
  void clearCache();

  /**
   * 获取当前配置。
   * @return Configuration
   */
  Configuration getConfiguration();

  /**
   * 获取Mapper。
   * @param <T> 待获取Mapper类型
   * @param type Mapper接口的class
   * @return 绑定到此 SqlSession 的Mapper
   */
  <T> T getMapper(Class<T> type);

  /**
   * 获取内部数据库连接。
   * @return Connection
   */
  Connection getConnection();
}
