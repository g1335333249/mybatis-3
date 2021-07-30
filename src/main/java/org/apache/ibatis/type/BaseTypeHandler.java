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
package org.apache.ibatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.session.Configuration;

/**
 * 用于引用泛型类型的基础 {@link TypeHandler}。
 * <p>
 * 重要提示：从 3.5.0 开始，此类从不调用 {@link ResultSet#wasNull()} 和 {@link CallableStatement#wasNull()} 方法来处理 SQL {@code NULL} 值。
 * 换句话说，{@code null} 值处理应该在子类上执行。
 * </p>
 *
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 * @author Simone Tripodi
 * @author Kzuki Shimizu
 */
public abstract class BaseTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

  /**
   * @deprecated Since 3.5.0 - See https://github.com/mybatis/mybatis-3/issues/1203. 此字段未来将删除。
   * 过时的，该属性未来将移除
   */
  @Deprecated
  protected Configuration configuration;

  /**
   * 设置配置。
   *
   * @param c
   *          新配置
   * @deprecated Since 3.5.0 - See https://github.com/mybatis/mybatis-3/issues/1203. 此属性未来将删除。
   */
  @Deprecated
  public void setConfiguration(Configuration c) {
    this.configuration = c;
  }

  @Override
  public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      if (jdbcType == null) {
        throw new TypeException("JDBC 要求必须为所有可为空的参数指定 JdbcType。");
      }
      try {
        ps.setNull(i, jdbcType.TYPE_CODE);
      } catch (SQLException e) {
        throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
          + "尝试为此参数设置不同的 JdbcType 或不同的 jdbcTypeForNull 配置属性。"
          + "原因: " + e, e);
      }
    } else {
      try {
        setNonNullParameter(ps, i, parameter, jdbcType);
      } catch (Exception e) {
        throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . "
          + "尝试为此参数设置不同的 JdbcType 或不同的配置属性。"
          + "原因: " + e, e);
      }
    }
  }

  @Override
  public T getResult(ResultSet rs, String columnName) throws SQLException {
    try {
      return getNullableResult(rs, columnName);
    } catch (Exception e) {
      throw new ResultMapException("Error attempting to get column '" + columnName + "' from result set.  Cause: " + e, e);
    }
  }

  @Override
  public T getResult(ResultSet rs, int columnIndex) throws SQLException {
    try {
      return getNullableResult(rs, columnIndex);
    } catch (Exception e) {
      throw new ResultMapException("Error attempting to get column #" + columnIndex + " from result set.  Cause: " + e, e);
    }
  }

  @Override
  public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
    try {
      return getNullableResult(cs, columnIndex);
    } catch (Exception e) {
      throw new ResultMapException("Error attempting to get column #" + columnIndex + " from callable statement.  Cause: " + e, e);
    }
  }

  public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

  /**
   * Gets the nullable result.
   * 获取空值结果
   * @param rs
   *          the rs
   * @param columnName
   *          Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
   * @return the nullable result
   * @throws SQLException
   *           the SQL exception
   */
  public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

  /**
   * 获取空值结果
   * @param rs
   * @param columnIndex
   * @return
   * @throws SQLException
   */
  public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

  /**
   * 获取空值结果
   * @param cs
   * @param columnIndex
   * @return
   * @throws SQLException
   */
  public abstract T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException;

}
