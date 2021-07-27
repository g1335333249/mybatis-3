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
package org.apache.ibatis.cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 用于缓存提供程序的 SPI。
 * <p>
 * 将为每个命名空间创建一个缓存实例。
 * <p>
 * 缓存实现必须有一个构造函数，它接收缓存 ID 作为字符串参数。
 * <p>
 * MyBatis 会将命名空间作为 id 传递给构造函数。
 *
 * <pre>
 * public MyCache(final String id) {
 *   if (id == null) {
 *     throw new IllegalArgumentException("Cache instances require an ID");
 *   }
 *   this.id = id;
 *   initialize();
 * }
 * </pre>
 *
 * 翻译：https://github.com/g1335333249/mybatis-3
 * @author Clinton Begin
 */

public interface Cache {

  /**
   * @return The identifier of this cache
   */
  String getId();

  /**
   * @param key
   *          可以是任何对象，但通常是 {@link CacheKey}
   * @param value
   *          选择的结果。
   */
  void putObject(Object key, Object value);

  /**
   * @param key
   *          The key
   * @return The object stored in the cache.
   */
  Object getObject(Object key);

  /**
   * 从 3.3.0 开始，仅在回滚缓存中丢失的任何先前值期间调用此方法。
   * 这允许任何阻塞缓存释放先前可能已放在密钥上的锁。
   * 阻塞缓存在值为空时放置一个锁，并在该值再次返回时释放它。
   * 这样其他线程将等待该值可用而不是访问数据库。
   *
   * @param key
   *          The key
   * @return Not used
   */
  Object removeObject(Object key);

  /**
   * 清除此缓存实例。
   */
  void clear();

  /**
   * 可选的。这个方法不被核心调用。
   *
   * @return 存储在缓存中的元素数量（不是它的容量）。
   */
  int getSize();

  /**
   * 可选的。从 3.2.6 开始，核心不再调用此方法。
   * <p>
   * 缓存所需的任何锁定都必须由缓存提供者在内部提供。
   *
   * @return 读写锁
   */
  default ReadWriteLock getReadWriteLock() {
    return null;
  }

}
