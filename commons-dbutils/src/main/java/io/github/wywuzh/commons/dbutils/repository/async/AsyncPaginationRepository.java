/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.dbutils.repository.async;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

import io.github.wywuzh.commons.core.sql.DataBase;
import io.github.wywuzh.commons.core.sql.Type;
import io.github.wywuzh.commons.dbutils.JdbcUtils;
import io.github.wywuzh.commons.dbutils.Sql;
import io.github.wywuzh.commons.dbutils.entity.AbstractEntity;
import io.github.wywuzh.commons.dbutils.vo.AbstractVo;
import io.github.wywuzh.commons.pager.PaginationParameter;
import io.github.wywuzh.commons.pager.Sort;

/**
 * 类PaginationRepository.java的实现描述：基于SQL的数据分页操作
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年7月2日 下午8:39:37
 * @version v1.0.0
 * @since JDK 1.7
 */
public class AsyncPaginationRepository<E extends AbstractEntity, V extends AbstractVo> extends AsyncBasicRepository<E, V> {
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * 根据条件查询数据列表
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param pageNo        页码，页码从1开始
     * @param pageSize      每页显示数据量
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String conditionSql, Object[] conditionObjs, int pageNo, int pageSize) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        return this.queryForList(tableName, columnSql, conditionSql, conditionObjs, null, pageNo, pageSize);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段数组
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param pageNo           页码，页码从1开始
     * @param pageSize         每页显示数据量
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String[] conditionColumns, Object[] conditionObjs, int pageNo, int pageSize) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForList(tableName, columnSql, conditionSql.toString(), conditionObjs, null, pageNo, pageSize);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sorts         排序
     * @param pageNo        页码，页码从1开始
     * @param pageSize      每页显示数据量
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String conditionSql, Object[] conditionObjs, Sort[] sorts, int pageNo, int pageSize) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        // 查询SQL
        StringBuilder querySql = new StringBuilder(MessageFormat.format(Sql.QUERY, columnSql, tableName, StringUtils.stripToEmpty(conditionSql)));

        PaginationParameter<V> paginationParameter = new PaginationParameter<>(pageNo, pageSize);
        // 排序
        querySql.append(paginationParameter.generateOrderSql());
        /*
         * if (null != sorts && sorts.length > 0) {
         * StringBuilder sortStr = new StringBuilder();
         * for (int i = 0; i < sorts.length; i++) {
         * if (StringUtils.isNotBlank(sortStr)) {
         * sortStr.append(",");
         * }
         * sortStr.append(sorts[i].getSort()).append(" ").append(sorts[i].getOrder().getValue());
         * }
         * if (StringUtils.isNotBlank(sortStr)) {
         * querySql.append(" ORDER BY ").append(sortStr);
         * }
         * }
         */

        Connection connection = JdbcUtils.getConnection(); // DataSourceUtils.getConnection(getDataSource());
        try {
            return getAsyncQueryRunner().query(connection, generatePageSql(querySql.toString(), paginationParameter), new BeanListHandler<E>(getEntityClass()), conditionObjs).get();
        } catch (InterruptedException e) {
            logger.error(e);
        } catch (ExecutionException e) {
            logger.error(e);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            // 注意，当我们显式地指定了Connection时，QueryRunner默认是不会关闭Connection连接的，这里我们要手动的关掉
            JdbcUtils.close();
            // DbUtils.close(connection);
            DataSourceUtils.releaseConnection(connection, getDataSource());
        }
        return null;
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段数组
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sorts            排序
     * @param pageNo           页码，页码从1开始
     * @param pageSize         每页显示数据量
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String[] conditionColumns, Object[] conditionObjs, Sort[] sorts, int pageNo, int pageSize) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForList(tableName, columnSql, conditionSql.toString(), conditionObjs, sorts, pageNo, pageSize);
    }

    /**
     * 生成分页SQL语句
     *
     * @param querySql           查询语句
     * @param paginationParamter 分页参数
     * @return 根据Connection连接信息生成分页SQL语句
     */
    public String generatePageSql(String querySql, PaginationParameter<V> paginationParamter) {
        StringBuilder pageSql = new StringBuilder();
        try {
            DataBase dataBase = new DataBase(getDataSource().getConnection());
            Type type = dataBase.getProduct().getType();
            if (Type.MySQL.equals(type)) {
                pageSql.append(querySql);
                pageSql.append(" LIMIT ").append(paginationParamter.getOffSet()).append(",").append(paginationParamter.getPageSize());
            } else if (Type.Oracle.equals(type)) {
                pageSql.append("SELECT * FROM (SELECT A.*,ROWNUM RN FROM (");
                pageSql.append(querySql);
                pageSql.append(")A WHERE ROWNUM <= ").append(paginationParamter.getEndSet());
                pageSql.append(") WHERE RN > ").append(paginationParamter.getOffSet());
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return pageSql.toString();
    }
}
