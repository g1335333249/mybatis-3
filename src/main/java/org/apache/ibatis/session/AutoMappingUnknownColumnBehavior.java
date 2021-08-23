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

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 指定检测到自动映射目标的未知列（或未知属性类型）时的行为。
 *
 * @since 3.4.0
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Kazuki Shimizu
 */
public enum AutoMappingUnknownColumnBehavior {

  /**
   * 什么都不做（默认）。
   */
  NONE {
    @Override
    public void doAction(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
      // 什么都不做
    }
  },

  /**
   * 输出警告日志。
   * Note: {@code 'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'} 的日志级别必须设置为 {@code WARN}。
   */
  WARNING {
    @Override
    public void doAction(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
      LogHolder.log.warn(buildMessage(mappedStatement, columnName, property, propertyType));
    }
  },

  /**
   * 映射失败。
   * Note: 抛出 {@link SqlSessionException}。
   */
  FAILING {
    @Override
    public void doAction(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
      throw new SqlSessionException(buildMessage(mappedStatement, columnName, property, propertyType));
    }
  };

  /**
   * 当检测到自动映射目标的未知列（或未知属性类型）时执行该操作。
   * @param mappedStatement 当前映射语句
   * @param columnName 映射目标的列名
   * @param propertyName 映射目标的属性名称
   * @param propertyType 映射目标的属性类型（如果此参数不为空，则未注册属性类型的{@link org.apache.ibatis.type.TypeHandler}）
   */
  public abstract void doAction(MappedStatement mappedStatement, String columnName, String propertyName, Class<?> propertyType);

  /**
   * 构建错误消息。
   */
  private static String buildMessage(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType) {
    return new StringBuilder("检测到未知列 '")
      .append(mappedStatement.getId())
      .append("' 自动映射. 映射参数是 ")
      .append("[")
      .append("列名=").append(columnName)
      .append(",").append("属性名称=").append(property)
      .append(",").append("属性类型=").append(propertyType != null ? propertyType.getName() : null)
      .append("]")
      .toString();
  }

  private static class LogHolder {
    private static final Log log = LogFactory.getLog(AutoMappingUnknownColumnBehavior.class);
  }

}
