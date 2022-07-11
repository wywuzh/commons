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
package com.github.wywuzh.commons.dbutils.repository;

import com.github.wywuzh.commons.core.util.StringHelper;
import com.github.wywuzh.commons.dbutils.JdbcUtils;
import com.github.wywuzh.commons.dbutils.Sql;
import com.github.wywuzh.commons.dbutils.entity.AbstractEntity;
import com.github.wywuzh.commons.dbutils.handlers.ColumnHandler;
import com.github.wywuzh.commons.dbutils.vo.AbstractVo;
import com.github.wywuzh.commons.pager.Sort;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 类BasicRepository.java的实现描述：基于SQL的数据操作
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2016年12月28日 下午7:56:04
 * @version v1.0.0
 * @since JDK 1.7
 */
@SuppressWarnings("unchecked")
public class BasicRepository<E extends AbstractEntity, V extends AbstractVo> {

    private DataSource dataSource;

    private QueryRunner queryRunner;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
    }

    public QueryRunner getQueryRunner() {
        return queryRunner;
    }

    public Class<E> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) parameterizedType.getActualTypeArguments()[0];
    }

    public Class<V> getVoClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<V>) parameterizedType.getActualTypeArguments()[1];
    }

    /**
     * 处理新增数据请求
     *
     * @param sql     新增字段SQL。SQL格式：INSERT INTO tbl_name(col1, col2, col3, col4, ...) VALUES(?, ?, ?, ?, ...)
     * @param handler 返回结果处理
     * @param params  新增字段参数
     * @param <E>     返回结果
     * @return
     * @throws SQLException
     */
    public <E> E doInsert(String sql, ResultSetHandler<E> handler, Object... params) throws SQLException {
        try {
            E result = queryRunner.insert(JdbcUtils.getConnection(), sql, handler, params);

            JdbcUtils.commitQuietly();
            return result;
        } catch (SQLException ex) {
            JdbcUtils.rollbackAndCloseQuietly();
            throw new SQLException("; uncategorized SQLException for SQL [" + sql + "]; SQL state [" + ex.getSQLState() + "]; error code [" + ex.getErrorCode() + "]; " + ex.getMessage(), ex);
        } finally {
            JdbcUtils.closeQuietly();
        }
    }

    /**
     * 处理修改数据请求
     *
     * @param sql    修改数据SQL。SQL格式：UPDATE tbl_name SET name=?, update_user=?, update_time=? WHERE id=?
     * @param params 修改字段参数
     * @return
     * @throws SQLException
     */
    public int doUpdate(String sql, Object[] params) throws SQLException {
        try {
            int result = queryRunner.update(JdbcUtils.getConnection(), sql, params);

            JdbcUtils.commitQuietly();
            return result;
        } catch (SQLException ex) {
            JdbcUtils.rollbackAndCloseQuietly();
            throw new SQLException("; uncategorized SQLException for SQL [" + sql + "]; SQL state [" + ex.getSQLState() + "]; error code [" + ex.getErrorCode() + "]; " + ex.getMessage(), ex);
        } finally {
            JdbcUtils.closeQuietly();
        }
    }

    /**
     * 批量处理请求
     *
     * @param sql    SQL字符串，可以是insert、update、delete语句
     * @param params 请求参数
     * @return
     * @throws SQLException
     */
    public int[] doBatch(String sql, Object[][] params) throws SQLException {
        try {
            int[] result = queryRunner.batch(JdbcUtils.getConnection(), sql, params);

            JdbcUtils.commitQuietly();
            return result;
        } catch (SQLException ex) {
            JdbcUtils.rollbackAndCloseQuietly();
            throw new SQLException("; uncategorized SQLException for SQL [" + sql + "]; SQL state [" + ex.getSQLState() + "]; error code [" + ex.getErrorCode() + "]; " + ex.getMessage(), ex);
        } finally {
            JdbcUtils.closeQuietly();
        }
    }

    /**
     * 处理查询
     *
     * @param sql
     * @param handler
     * @param params
     * @param <E>
     * @return
     * @throws SQLException
     */
    public <E> E doQuery(String sql, ResultSetHandler<E> handler, Object... params) throws SQLException {
        try {
            E result = queryRunner.query(JdbcUtils.getConnection(), sql, handler, params);

            JdbcUtils.commitQuietly();
            return result;
        } catch (SQLException ex) {
            JdbcUtils.rollbackAndCloseQuietly();
            throw new SQLException("; uncategorized SQLException for SQL [" + sql + "]; SQL state [" + ex.getSQLState() + "]; error code [" + ex.getErrorCode() + "]; " + ex.getMessage(), ex);
        } finally {
            JdbcUtils.closeQuietly();
        }
    }

    /**
     * 新增数据
     *
     * @param tableName 表名
     * @param columnSql 新增字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param values    字段对应的值。数组值的顺序需要和新增字段SQL的顺序一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int insert(String tableName, String columnSql, Object[] values) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String valueSql = StringHelper.spellToStr(values.length, "?", ",");
        String sql = MessageFormat.format(Sql.INSERT, tableName, columnSql, valueSql);
        return doUpdate(sql, values);
    }

    /**
     * 新增数据
     *
     * @param tableName 表名
     * @param columns   新增字段数组集合
     * @param values    字段对应的值。数组值的顺序需要和新增字段SQL的顺序一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int insert(String tableName, String[] columns, Object[] values) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notEmpty(columns, "columns must not be empty");

        StringBuilder columnSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnSql.length() > 0) {
                columnSql.append(",");
            }
            columnSql.append(columns[i]);
        }
        return this.insert(tableName, columnSql.toString(), values);
    }

    /**
     * 批量新增数据
     *
     * @param tableName 表名
     * @param columnSql 新增字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param values    字段对应的值。数组值的顺序需要和新增字段SQL的顺序一致
     * @return 返回结果数组。每个元素返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int[] batchInsert(String tableName, String columnSql, Object[][] values) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String valueSql = StringHelper.spellToStr(values[0].length, "?", ",");
        String sql = MessageFormat.format(Sql.INSERT, tableName, columnSql, valueSql);
        return doBatch(sql, values);
    }

    /**
     * 批量新增数据
     *
     * @param tableName 表名
     * @param columns   新增字段数组集合
     * @param values    字段对应的值。数组值的顺序需要和新增字段SQL的顺序一致
     * @return 返回结果数组。每个元素返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int[] batchInsert(String tableName, String[] columns, Object[][] values) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notEmpty(columns, "columns must not be empty");

        StringBuilder columnSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnSql.length() > 0) {
                columnSql.append(",");
            }
            columnSql.append(columns[i]);
        }
        return this.batchInsert(tableName, columnSql.toString(), values);
    }

    /**
     * 修改数据
     *
     * @param tableName     表名
     * @param columnSql     修改字段SQL。SQL格式：NAME=?,UPDATE_USER=?,UPDATE_TIME=?
     * @param values        修改字段值数组集合
     * @param conditionSql  修改条件字段SQL。sql格式：AND ID=? AND NAME=?
     * @param conditionObjs 条件条件字段值数组集合。条件数组值的顺序要和条件字段的顺序保持一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int update(String tableName, String columnSql, Object[] values, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String sql = MessageFormat.format(Sql.UPDATE, tableName, columnSql, StringUtils.stripToEmpty(conditionSql));

        // 封装条件值
        int valueLength = (values == null ? 0 : values.length);
        int objsLength = (conditionObjs == null ? 0 : conditionObjs.length);
        Object[] args = new Object[valueLength + objsLength];
        for (int i = 0; i < valueLength; i++) {
            args[i] = values[i];
        }
        for (int i = 0; i < objsLength; i++) {
            args[valueLength + i] = conditionObjs[i];
        }
        return doUpdate(sql, args);
    }

    /**
     * 修改数据
     *
     * @param tableName        表名
     * @param columnSql        修改字段SQL。SQL格式：NAME=?,UPDATE_USER=?,UPDATE_TIME=?
     * @param values           修改字段值数组集合
     * @param conditionColumns 修改条件字段数组集合
     * @param conditionObjs    条件条件字段值数组集合。条件数组值的顺序要和条件字段的顺序保持一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int update(String tableName, String columnSql, Object[] values, String[] conditionColumns,
                      Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        // 封装条件
        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }

        return this.update(tableName, columnSql, values, conditionSql.toString(), conditionObjs);
    }

    /**
     * 修改数据
     *
     * @param tableName     表名
     * @param columns       修改字段数组集合
     * @param values        修改字段值数组集合
     * @param conditionSql  修改条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 条件条件字段值数组集合。条件数组值的顺序要和条件字段的顺序保持一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int update(String tableName, String[] columns, Object[] values, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notEmpty(columns, "columns must not be empty");

        // 封装要修改的字段
        StringBuilder columnSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnSql.length() > 0) {
                columnSql.append(",");
            }
            columnSql.append(" ").append(columns[i]).append("=?");
        }
        return this.update(tableName, columnSql.toString(), values, conditionSql, conditionObjs);
    }

    /**
     * 修改数据
     *
     * @param tableName        表名
     * @param columns          修改字段数组集合
     * @param values           修改字段值数组集合
     * @param conditionColumns 修改条件字段数组集合
     * @param conditionObjs    条件条件字段值数组集合。条件数组值的顺序要和条件字段的顺序保持一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int update(String tableName, String[] columns, Object[] values, String[] conditionColumns,
                      Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notEmpty(columns, "columns must not be empty");

        // 封装要修改的字段
        StringBuilder columnSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnSql.length() > 0) {
                columnSql.append(",");
            }
            columnSql.append(" ").append(columns[i]).append("=?");
        }
        // 封装条件
        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }

        return this.update(tableName, columnSql.toString(), values, conditionSql.toString(), conditionObjs);
    }

    /**
     * 删除数据
     *
     * @param tableName     表名
     * @param conditionSql  删除条件SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 删除条件值。条件数组值的顺序要和条件SQL中的顺序保持一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int delete(String tableName, String conditionSql, Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");

        String sql = MessageFormat.format(Sql.DELETE, tableName, StringUtils.stripToEmpty(conditionSql));
        return doUpdate(sql, conditionObjs);
    }

    /**
     * 删除数据
     *
     * @param tableName        表名
     * @param conditionColumns 删除条件字段数组集合
     * @param conditionObjs    删除条件值。条件数组值的顺序要和条件字段的顺序保持一致
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int delete(String tableName, String[] conditionColumns, Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.delete(tableName, conditionSql.toString(), conditionObjs);
    }

    /**
     * 更新数据操作。支持insert、update、delete类型
     *
     * @param sql    更新数据SQL。SQL格式：INSERT INTO table_name(column1,column2,column3...) VALUES(?,?,?...)
     * @param params 更新条件
     * @return 返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int execute(String sql, Object[] params) throws SQLException {
        Assert.notNull(sql, "sql must not be null");
        Assert.notEmpty(params, "params must not be empty");

        return doUpdate(sql, params);
    }

    /**
     * 批量更新数据操作。支持insert、update、delete类型
     *
     * @param sql    更新数据SQL。SQL格式：INSERT INTO table_name(column1,column2,column3...) VALUES(?,?,?...)
     * @param params 更新条件
     * @return 返回结果数组。每个元素返回结果为1表示成功，返回为0表示失败
     * @throws SQLException
     */
    public int[] batchExecute(String sql, Object[][] params) throws SQLException {
        Assert.notNull(sql, "sql must not be null");
        Assert.notEmpty(params, "params must not be empty");

        return doBatch(sql, params);
    }

    /**
     * 根据主键id查询数据
     *
     * @param tableName 表名
     * @param columnSql 查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param id        主键id
     * @return 返回实体对象，数据都封装在实体对象中
     * @throws SQLException
     */
    public E get(String tableName, String columnSql, String id) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");
        Assert.notNull(id, "id must not be null");

        String sql = MessageFormat.format(Sql.QUERY, columnSql, tableName, " AND ID=?");
        return doQuery(sql, new BeanHandler<E>(getEntityClass()), id);
    }

    /**
     * 根据主键id查询数据
     *
     * @param tableName 表名
     * @param columns   查询字段数组
     * @param id        主键id
     * @return 返回实体对象，数据都封装在实体对象中
     * @throws SQLException
     */
    public E get(String tableName, String[] columns, String id) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notEmpty(columns, "columns must not be empty");
        Assert.notNull(id, "id must not be null");

        StringBuilder columnSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnSql.length() > 0) {
                columnSql.append(",");
            }
            columnSql.append(columns[i]);
        }
        return this.get(tableName, columnSql.toString(), id);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String sql = MessageFormat.format(Sql.QUERY, columnSql, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new BeanListHandler<E>(getEntityClass()),
                conditionObjs);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段数组
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String[] conditionColumns, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForList(tableName, columnSql, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sorts         排序
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String conditionSql, Object[] conditionObjs,
                                Sort[] sorts) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        // 查询SQL
        StringBuilder querySql = new StringBuilder(
                MessageFormat.format(Sql.QUERY, columnSql, tableName, StringUtils.stripToEmpty(conditionSql)));

        // 排序
        if (null != sorts && sorts.length > 0) {
            StringBuilder sortStr = new StringBuilder();
            for (int i = 0; i < sorts.length; i++) {
                if (StringUtils.isNotBlank(sortStr)) {
                    sortStr.append(",");
                }
                sortStr.append(sorts[i].getSort()).append(" ").append(sorts[i].getOrder().getValue());
            }
            if (StringUtils.isNotBlank(sortStr)) {
                querySql.append(" ORDER BY ").append(sortStr);
            }
        }
        return doQuery(querySql.toString(),
                new BeanListHandler<E>(getEntityClass()), conditionObjs);
    }

    /**
     * 根据条件查询数据列表
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段数组
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sorts            排序
     * @return 返回实体对象集合，每个查询结果行的数据都封装在每个实体对象中
     * @throws SQLException
     */
    public List<E> queryForList(String tableName, String columnSql, String[] conditionColumns, Object[] conditionObjs,
                                Sort[] sorts) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForList(tableName, columnSql, conditionSql.toString(), conditionObjs, sorts);
    }

    /**
     * 根据条件查询数据，返回Long型数值
     *
     * @param tableName     表名
     * @param column        查询结果字段
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件
     * @return 返回Long型对象数据
     * @throws SQLException
     */
    public Long queryForLong(String tableName, String column, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        String sql = MessageFormat.format(Sql.QUERY, column, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new ColumnHandler<Long>(1), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回Long型数值
     *
     * @param tableName        表名
     * @param column           查询结果字段
     * @param conditionColumns 查询条件字段数组
     * @param conditionObjs    查询条件
     * @return 返回Long型对象数据
     * @throws SQLException
     */
    public Long queryForLong(String tableName, String column, String[] conditionColumns, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForLong(tableName, column, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回Double型数值
     *
     * @param tableName     表名
     * @param column        查询结果字段
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件
     * @return 返回Double型对象数据
     * @throws SQLException
     */
    public Double queryForDouble(String tableName, String column, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        String sql = MessageFormat.format(Sql.QUERY, column, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new ColumnHandler<Double>(1), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回Double型数值
     *
     * @param tableName        表名
     * @param column           查询结果字段
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件
     * @return 返回Double型对象数据
     * @throws SQLException
     */
    public Double queryForDouble(String tableName, String column, String[] conditionColumns, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForDouble(tableName, column, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回Integer型数值
     *
     * @param tableName     表名
     * @param column        查询结果字段
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件
     * @return 返回Integer型对象数据
     * @throws SQLException
     */
    public Integer queryForInteger(String tableName, String column, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        String sql = MessageFormat.format(Sql.QUERY, column, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new ColumnHandler<Integer>(1), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回Integer型数值
     *
     * @param tableName        表名
     * @param column           查询结果字段
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件
     * @return 返回Integer型对象数据
     * @throws SQLException
     */
    public Integer queryForInteger(String tableName, String column, String[] conditionColumns, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForInteger(tableName, column, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回BigDecimal型数值
     *
     * @param tableName     表名
     * @param column        查询结果字段
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件
     * @return 返回BigDecimal型对象数据
     * @throws SQLException
     */
    public BigDecimal queryForBigDecimal(String tableName, String column, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        String sql = MessageFormat.format(Sql.QUERY, column, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new ColumnHandler<BigDecimal>(1), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回BigDecimal型数值
     *
     * @param tableName        表名
     * @param column           查询结果字段
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件
     * @return 返回BigDecimal型对象数据
     * @throws SQLException
     */
    public BigDecimal queryForBigDecimal(String tableName, String column, String[] conditionColumns,
                                         Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForBigDecimal(tableName, column, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回String型数值
     *
     * @param tableName     表名
     * @param column        查询结果字段
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件
     * @return 返回String型对象数据
     * @throws SQLException
     */
    public String queryForString(String tableName, String column, String conditionSql, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        String sql = MessageFormat.format(Sql.QUERY, column, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new ColumnHandler<String>(1), conditionObjs);
    }

    /**
     * 根据条件查询数据，返回String型数值
     *
     * @param tableName        表名
     * @param column           查询结果字段
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件
     * @return 返回String型对象数据
     * @throws SQLException
     */
    public String queryForString(String tableName, String column, String[] conditionColumns, Object[] conditionObjs)
            throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(column, "column must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForString(tableName, column, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param rsh           查询结果处理器
     * @return 返回rsh结果处理器指定类型的数据
     * @throws SQLException
     */
    public <T> T queryForObject(String tableName, String columnSql, String conditionSql, Object[] conditionObjs,
                                ResultSetHandler<T> rsh) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String sql = MessageFormat.format(Sql.QUERY, columnSql, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, rsh, conditionObjs);
    }

    /**
     * 根据条件查询数据
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param rsh              查询结果处理器
     * @return 返回rsh结果处理器指定类型的数据
     * @throws SQLException
     */
    public <T> T queryForObject(String tableName, String columnSql, String[] conditionColumns, Object[] conditionObjs,
                                ResultSetHandler<T> rsh) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForObject(tableName, columnSql, conditionSql.toString(), conditionObjs, rsh);
    }

    /**
     * 根据条件查询数据。查询单行记录，需要注意的是，如果根据条件查询到的结果有多行，将只返回第一行结果记录
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @return 将查询结果封装在Map集合中，key为查询字段，value则是查询的结果值
     * @throws SQLException
     */
    public Map<String, Object> queryForMap(String tableName, String columnSql, String conditionSql,
                                           Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String sql = MessageFormat.format(Sql.QUERY, columnSql, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new MapHandler(), conditionObjs);
    }

    /**
     * 根据条件查询数据。查询单行记录，需要注意的是，如果根据条件查询到的结果有多行，将只返回第一行结果记录
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @return 将查询结果封装在Map集合中，key为查询字段，value则是查询的结果值
     * @throws SQLException
     */
    public Map<String, Object> queryForMap(String tableName, String columnSql, String[] conditionColumns,
                                           Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForMap(tableName, columnSql, conditionSql.toString(), conditionObjs);
    }

    /**
     * 根据条件查询数据
     *
     * @param tableName     表名
     * @param columnSql     查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionSql  查询条件字段SQL。SQL格式：AND ID=? AND NAME=?
     * @param conditionObjs 查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @return 将查询结果封装在Map集合中，key为查询字段，value则是查询的结果值
     * @throws SQLException
     */
    public List<Map<String, Object>> queryForMapList(String tableName, String columnSql, String conditionSql,
                                                     Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        String sql = MessageFormat.format(Sql.QUERY, columnSql, tableName, StringUtils.stripToEmpty(conditionSql));
        return doQuery(sql, new MapListHandler(), conditionObjs);
    }

    /**
     * 根据条件查询数据
     *
     * @param tableName        表名
     * @param columnSql        查询字段SQL。SQL格式：ID, IS_DELETE, DESCRIPTION, CREATE_USER, CREATE_TIME, UPDATE_USER, UPDATE_TIME
     * @param conditionColumns 查询条件字段
     * @param conditionObjs    查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @return 将查询结果封装在Map集合中，key为查询字段，value则是查询的结果值
     * @throws SQLException
     */
    public List<Map<String, Object>> queryForMapList(String tableName, String columnSql, String[] conditionColumns,
                                                     Object[] conditionObjs) throws SQLException {
        Assert.notNull(tableName, "tableName must not be null");
        Assert.notNull(columnSql, "columnSql must not be null");

        StringBuilder conditionSql = new StringBuilder();
        int length = (conditionColumns == null ? 0 : conditionColumns.length);
        for (int i = 0; i < length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.queryForMapList(tableName, columnSql, conditionSql.toString(), conditionObjs);
    }
}
