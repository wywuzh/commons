/*
 * Copyright 2015-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wywuzh.commons.dbutils.transaction;

import com.github.wywuzh.commons.dbutils.JdbcUtils;

import java.sql.SQLException;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * 类DataSourceTransactionManager.java的实现描述：事物管理
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年1月15日 下午3:28:39
 * @version v1.0.0
 * @since JDK 1.7
 */
public class DbUtilsTransactionManager extends DataSourceTransactionManager {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doCommit(DefaultTransactionStatus status) {
    try {
      if (status.isDebug()) {
        logger.debug("Committing JDBC transaction on Connection [" + JdbcUtils.getConnection() + "]");
      }
      JdbcUtils.commit();
    } catch (SQLException ex) {
      throw new TransactionSystemException("Could not commit JDBC transaction", ex);
    }
  }

  @Override
  protected void doRollback(DefaultTransactionStatus status) {
    try {
      if (status.isDebug()) {
        logger.debug("Rolling back JDBC transaction on Connection [" + JdbcUtils.getConnection() + "]");
      }
      JdbcUtils.rollback();
    } catch (SQLException ex) {
      throw new TransactionSystemException("Could not roll back JDBC transaction", ex);
    }
  }

}
