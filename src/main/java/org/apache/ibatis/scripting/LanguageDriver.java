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
package org.apache.ibatis.scripting;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

public interface LanguageDriver {

  /**
   * 创建一个 {@link ParameterHandler} 将实际参数传递给 JDBC 语句。
   *
   * 翻译：https://github.com/g1335333249/mybatis-3
   * @author Frank D. Martinez [mnesarco]
   * @param mappedStatement 正在执行的映射语句
   * @param parameterObject 输入参数对象（可以为空）
   * @param boundSql 执行动态语言后生成的 SQL。
   * @return 参数处理程序
   * @see DefaultParameterHandler
   */
  ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);

  /**
   * 创建一个 {@link SqlSource}，它将保存从映射器 xml 文件中读取的语句。
   * 它在启动期间调用，当映射语句从类或 xml 文件中读取时。
   *
   * @param configuration MyBatis 配置
   * @param script 从 XML 文件解析的 XNode
   * @param parameterType 从映射器方法获得或在 parameterType xml 属性中指定的输入参数类型。可以为空。
   * @return sql源码
   */
  SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);

  /**
   * 创建一个 {@link SqlSource} 来保存从注释中读取的语句。
   * 它在启动期间调用，当映射语句从类或 xml 文件中读取时。
   *
   * @param configuration MyBatis 配置
   * @param script 注释的内容
   * @param parameterType 从映射器方法获得或在 parameterType xml 属性中指定的输入参数类型。可以为空。
   * @return sql源码
   */
  SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType);

}
