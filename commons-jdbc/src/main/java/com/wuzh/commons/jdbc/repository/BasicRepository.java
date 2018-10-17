/*
 * Copyright 2015-2017 the original author or authors.
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
package com.wuzh.commons.jdbc.repository;

import com.wuzh.commons.jdbc.Sql;
import com.wuzh.commons.jdbc.entity.AbstractEntity;
import com.wuzh.commons.jdbc.vo.AbstractVo;
import com.wuzh.commons.pager.Page;
import com.wuzh.commons.pager.Sort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 类BasicRepository.java的实现描述：持久层基类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015-9-14 下午4:49:33
 * @version v1.0.0
 * @since JDK 1.7
 */
public class BasicRepository<E extends AbstractEntity, V extends AbstractVo> {

    /**
     * 数据库连接池
     */
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    /**
     * 新增数据
     * 
     * @author 伍章红 2015年11月8日 上午10:42:28
     * @param tableName
     *            表名
     * @param columnsSql
     *            新增字段SQL
     * @param values
     *            字段对应的值。数组值的顺序需要和新增字段SQL的顺序一致
     * @return
     */
    public int insert(String tableName, String columnsSql, final Object[] values) {
        return this.insert(tableName, columnsSql.split(","), values);
    }

    /**
     * 新增数据
     * 
     * @author 伍章红 2015年11月8日 上午10:42:29
     * @param tableName
     *            表名
     * @param columns
     *            新增字段
     * @param values
     *            字段对应的值。数组值的顺序需要和新增字段数组的顺序一致
     * @return
     */
    public int insert(String tableName, String[] columns, final Object[] values) {
        StringBuilder columnsSql = new StringBuilder();
        StringBuilder valuesSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnsSql.length() > 0) {
                columnsSql.append(",");
                valuesSql.append(",");
            }
            columnsSql.append(columns[i]);
            valuesSql.append("?");
        }
        String sql = MessageFormat.format(Sql.INSERT, tableName, columnsSql, valuesSql);
        return getJdbcTemplate().execute(sql, new PreparedStatementCallback<Integer>() {

            @Override
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                for (int i = 0; i < values.length; i++) {
                    // JDBC字段索引从1开始
                    ps.setObject(i + 1, values[i]);
                }

                return ps.executeUpdate();
            }
        });
    }

    /**
     * 批量新增数据
     * 
     * @author 伍章红 2015年11月11日 下午9:01:20
     * @param tableName
     * @param columnsSql
     * @param values
     * @return
     */
    public int[] batchInsert(String tableName, String columnsSql, List<Object[]> values) {
        return this.batchInsert(tableName, columnsSql.split(","), values);
    }

    /**
     * 批量新增数据
     * 
     * @author 伍章红 2015年11月11日 下午9:01:22
     * @param tableName
     * @param columns
     * @param values
     * @return
     */
    public int[] batchInsert(String tableName, final String[] columns, final List<Object[]> values) {
        StringBuilder columnsSql = new StringBuilder();
        StringBuilder valuesSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnsSql.length() > 0) {
                columnsSql.append(",");
                valuesSql.append(",");
            }
            columnsSql.append(columns[i]);
            valuesSql.append("?");
        }
        String sql = MessageFormat.format(Sql.INSERT, tableName, columnsSql, valuesSql);
        return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                for (int j = 0; j < columns.length; j++) {
                    // JDBC字段索引从1开始
                    ps.setObject(j + 1, values.get(i)[j]);
                }
            }

            @Override
            public int getBatchSize() {
                return values.size();
            }
        });
    }

    /**
     * 修改数据
     * 
     * @author 伍章红 2015年11月8日 下午12:29:33
     * @param tableName
     *            表名
     * @param columnSql
     *            修改字段。如 column1=?,column2=?,column3=?
     * @param values
     *            修改字段值
     * @param conditionSql
     *            修改条件SQL
     * @param conditionObjs
     *            修改条件值
     * @return
     */
    public int update(String tableName, String columnSql, Object[] values, String conditionSql,
                      Object[] conditionObjs) {
        // 封装要修改的字段
        String sql = MessageFormat.format(Sql.UPDATE, tableName, columnSql, conditionSql);

        // 封装条件值
        Object[] args = new Object[values.length + conditionObjs.length];
        for (int i = 0; i < values.length; i++) {
            args[i] = values[i];
        }
        for (int i = 0; i < conditionObjs.length; i++) {
            args[values.length + i] = conditionObjs[i];
        }

        return getJdbcTemplate().update(sql, args);
    }

    /**
     * 修改数据
     * 
     * @author 伍章红 2015年11月8日 下午12:29:42
     * @param tableName
     *            表名
     * @param columnSql
     *            修改字段。如 column1=?,column2=?,column3=?
     * @param values
     *            修改字段值
     * @param conditionColumns
     *            修改条件字段集合
     * @param conditionObjs
     *            修改条件值
     * @return
     */
    public int update(String tableName, String columnSql, Object[] values, String[] conditionColumns,
                      Object[] conditionObjs) {
        // 封装条件
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }

        return this.update(tableName, columnSql, values, conditionSql.toString(), conditionObjs);
    }

    /**
     * 修改数据
     * 
     * @author 伍章红 2015年11月8日 下午12:29:45
     * @param tableName
     *            表名
     * @param columns
     *            修改字段集合
     * @param values
     *            修改字段值
     * @param conditionSql
     *            修改条件SQL
     * @param conditionObjs
     *            修改条件值
     * @return
     */
    public int update(String tableName, String[] columns, Object[] values, String conditionSql,
                      Object[] conditionObjs) {
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
     * @author 伍章红 2015年11月8日 下午12:29:47
     * @param tableName
     *            表名
     * @param columns
     *            修改字段集合
     * @param values
     *            修改字段值
     * @param conditionColumns
     *            修改条件字段集合
     * @param conditionObjs
     *            修改条件值
     * @return
     */
    public int update(String tableName, String[] columns, Object[] values, String[] conditionColumns,
                      Object[] conditionObjs) {
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
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }

        return this.update(tableName, columnSql.toString(), values, conditionSql.toString(), conditionObjs);
    }

    /**
     * 批量修改数据
     * 
     * @author 伍章红 2015年11月11日 下午9:30:33
     * @param tableName
     *            表名
     * @param columnSql
     *            修改字段。如 column1=?,column2=?,column3=?
     * @param valuesList
     *            修改字段值集合
     * @param conditionSql
     *            修改条件SQL
     * @param conditionObjsList
     *            修改条件值集合
     * @return
     */
    public int[] batchUpdate(String tableName, String columnSql, List<Object[]> valuesList, String conditionSql,
                             List<Object[]> conditionObjsList) {
        // 封装要修改的字段
        String sql = MessageFormat.format(Sql.UPDATE, tableName, columnSql, conditionSql);

        // 组装修改数据信息
        List<Object[]> argsList = new ArrayList<Object[]>();
        for (int i = 0; i < valuesList.size(); i++) {
            Object[] values = valuesList.get(i);
            Object[] conditionObjs = conditionObjsList.get(i);

            // 封装条件值
            Object[] args = new Object[values.length + conditionObjs.length];
            for (int j = 0; j < values.length; j++) {
                args[j] = values[j];
            }
            for (int j = 0; j < conditionObjs.length; j++) {
                args[values.length + j] = conditionObjs[j];
            }
            argsList.add(args);
        }

        SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(argsList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 批量修改数据
     * 
     * @author 伍章红 2015年11月11日 下午9:31:09
     * @param tableName
     *            表名
     * @param columnSql
     *            修改字段。如 column1=?,column2=?,column3=?
     * @param valuesList
     *            修改字段值集合
     * @param conditionColumns
     *            修改条件字段集合
     * @param conditionObjsList
     *            修改条件值集合
     * @return
     */
    public int[] batchUpdate(String tableName, String columnSql, List<Object[]> valuesList, String[] conditionColumns,
                             List<Object[]> conditionObjsList) {
        // 封装条件
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.batchUpdate(tableName, columnSql, valuesList, conditionSql.toString(), conditionObjsList);
    }

    /**
     * 批量修改数据
     * 
     * @author 伍章红 2015年11月11日 下午9:33:03
     * @param tableName
     *            表名
     * @param columns
     *            修改字段集合
     * @param valuesList
     *            修改字段值集合
     * @param conditionSql
     *            修改条件SQL
     * @param conditionObjsList
     *            修改条件值集合
     * @return
     */
    public int[] batchUpdate(String tableName, String[] columns, List<Object[]> valuesList, String conditionSql,
                             List<Object[]> conditionObjsList) {
        // 封装要修改的字段
        StringBuilder columnSql = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            if (columnSql.length() > 0) {
                columnSql.append(",");
            }
            columnSql.append(" ").append(columns[i]).append("=?");
        }
        return this.batchUpdate(tableName, columnSql.toString(), valuesList, conditionSql, conditionObjsList);
    }

    /**
     * 批量修改数据
     * 
     * @author 伍章红 2015年11月11日 下午9:33:05
     * @param tableName
     *            表名
     * @param columns
     *            修改字段集合
     * @param valuesList
     *            修改字段值集合
     * @param conditionColumns
     *            修改条件字段集合
     * @param conditionObjsList
     *            修改条件值集合
     * @return
     */
    public int[] batchUpdate(String tableName, String[] columns, List<Object[]> valuesList, String[] conditionColumns,
                             List<Object[]> conditionObjsList) {
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
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.batchUpdate(tableName, columnSql.toString(), valuesList, conditionSql.toString(),
                conditionObjsList);
    }

    /**
     * 删除数据
     * 
     * @author 伍章红 2015年11月8日 下午12:37:58
     * @param tableName
     *            表名
     * @param conditionSql
     *            删除条件SQL
     * @param conditionObjs
     *            删除条件值
     * @return
     */
    public int delete(String tableName, String conditionSql, Object[] conditionObjs) {
        String sql = MessageFormat.format(Sql.DELETE, tableName, conditionSql);

        return getJdbcTemplate().update(sql, conditionObjs);
    }

    /**
     * 删除数据
     * 
     * @author 伍章红 2015年11月8日 下午12:37:56
     * @param tableName
     *            表名
     * @param conditionColumns
     *            删除条件字段集合
     * @param conditionObjs
     *            删除条件值
     * @return
     */
    public int delete(String tableName, String[] conditionColumns, Object[] conditionObjs) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }

        return this.delete(tableName, conditionSql.toString(), conditionObjs);
    }

    /**
     * 批量删除数据
     * 
     * @author 伍章红 2015年11月11日 下午10:16:01
     * @param tableName
     *            表名
     * @param conditionSql
     *            删除条件SQL
     * @param conditionObjsList
     *            删除条件值集合
     * @return
     */
    public int[] batchDelete(String tableName, String conditionSql, List<Object[]> conditionObjsList) {
        String sql = MessageFormat.format(Sql.DELETE, tableName, conditionSql);

        return getJdbcTemplate().batchUpdate(sql, conditionObjsList);
    }

    /**
     * 批量删除数据
     * 
     * @author 伍章红 2015年11月11日 下午10:16:03
     * @param tableName
     *            表名
     * @param conditionColumns
     *            删除条件字段集合
     * @param conditionObjsList
     *            删除条件值集合
     * @return
     */
    public int[] batchDelete(String tableName, String[] conditionColumns, List<Object[]> conditionObjsList) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.batchDelete(tableName, conditionSql.toString(), conditionObjsList);
    }

    /**
     * 根据主键id查询数据
     * 
     * @author 伍章红 2015年11月8日 上午11:29:24
     * @param tableName
     *            表名
     * @param columnsSql
     *            查询字段名。如 column1,column2,column3
     * @param id
     *            主键id
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public E get(String tableName, String columnsSql, String id, RowMapper<E> rowMapper) {
        String conditionSql = " AND ID=?";
        String sql = MessageFormat.format(Sql.QUERY, columnsSql, tableName, conditionSql);
        Object[] args = new Object[] {
                id
        };
        return getJdbcTemplate().queryForObject(sql, args, rowMapper);
    }

    /**
     * 根据唯一键code查询数据
     * 
     * @author 伍章红 2015年11月8日 上午11:31:46
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param code
     *            唯一键code
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public E getUnique(String tableName, String columns, String code, RowMapper<E> rowMapper) {
        String conditionSql = " AND CODE=?";
        String sql = MessageFormat.format(Sql.QUERY, columns, tableName, conditionSql);
        Object[] args = new Object[] {
                code
        };
        return getJdbcTemplate().queryForObject(sql, args, rowMapper);
    }

    /**
     * 根据条件查询数据列表
     * 
     * @author 伍章红 2015年11月8日 上午11:22:54
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionSql
     *            查询条件SQL
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public List<E> findEntityListByParam(String tableName, String columns, String conditionSql, Object[] conditionObjs,
                                         RowMapper<E> rowMapper) {
        String sql = MessageFormat.format(Sql.QUERY, columns, tableName, conditionSql);
        return getJdbcTemplate().query(sql, conditionObjs, rowMapper);
    }

    /**
     * 根据条件查询数据列表
     * 
     * @author 伍章红 2015年11月8日 上午11:22:52
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionColumns
     *            查询条件字段集合
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public List<E> findEntityListByParam(String tableName, String columns, String[] conditionColumns,
                                         Object[] conditionObjs, RowMapper<E> rowMapper) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.findEntityListByParam(tableName, columns, conditionSql.toString(), conditionObjs, rowMapper);
    }

    /**
     * @author 伍章红 2015年12月4日 上午10:04:17
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionSql
     *            查询条件SQL
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sorts
     *            排序字段集合
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public List<E> findEntityListByParam(String tableName, String columns, String conditionSql, Object[] conditionObjs,
                                         List<Sort> sorts, RowMapper<E> rowMapper) {
        String sql = MessageFormat.format(Sql.QUERY, columns, tableName, conditionSql);
        StringBuilder sortSql = new StringBuilder();
        if (null != sorts && sorts.size() > 0) {
            for (Sort sort : sorts) {
                if (StringUtils.isNotBlank(sortSql)) {
                    sortSql.append(",");
                }
                sortSql.append(sort.getSort()).append(" ").append(sort.getOrder().getValue());
            }
        }
        return getJdbcTemplate().query(sql + " ORDER BY " + sortSql, conditionObjs, rowMapper);
    }

    /**
     * @author 伍章红 2015年12月4日 上午10:04:17
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionColumns
     *            查询条件字段集合
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sorts
     *            排序字段集合
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public List<E> findEntityListByParam(String tableName, String columns, String[] conditionColumns,
                                         Object[] conditionObjs, List<Sort> sorts, RowMapper<E> rowMapper) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.findEntityListByParam(tableName, columns, conditionSql.toString(), conditionObjs, sorts, rowMapper);
    }

    /**
     * 根据条件查询数据列表（分页）
     * 
     * @author 伍章红 2015年11月8日 上午11:23:47
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionSql
     *            查询条件SQL
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和条件SQL字段的顺序一致
     * @param page
     *            分页条件信息
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public List<E> findEntityPagerByParam(String tableName, String columns, String conditionSql, Object[] conditionObjs,
                                          Page page, RowMapper<E> rowMapper) {
        String sql = MessageFormat.format(Sql.QUERY, columns, tableName, conditionSql);
        sql = sql + " LIMIT ?,?";
        // 组装查询条件
        Object[] args = new Object[conditionObjs.length + 2];
        for (int i = 0; i < conditionObjs.length; i++) {
            args[i] = conditionObjs[i];
        }
        args[args.length - 2] = page.getOffSet();
        args[args.length - 1] = page.getPageSize();
        return getJdbcTemplate().query(sql, args, rowMapper);
    }

    /**
     * 根据条件查询数据列表（分页）
     * 
     * @author 伍章红 2015年11月8日 上午11:23:51
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionColumns
     *            查询条件字段集合
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param page
     *            分页条件信息
     * @param rowMapper
     *            结果对象映射
     * @return
     */
    public List<E> findEntityPagerByParam(String tableName, String columns, String[] conditionColumns,
                                          Object[] conditionObjs, Page page, RowMapper<E> rowMapper) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.findEntityPagerByParam(tableName, columns, conditionSql.toString(), conditionObjs, page, rowMapper);
    }

    /**
     * 根据条件查询数据列表总数
     * 
     * @author 伍章红 2015年11月8日 上午10:25:25
     * @param tableName
     *            表名
     * @param conditionSql
     *            查询条件SQL。如 and column1=? and column2=?
     * @param conditionObjs
     *            查询条件，条件数组字段的顺序需要和条件SQL字段的顺序一致
     * @return
     */
    public long findEntityTotalByParam(String tableName, String conditionSql, Object[] conditionObjs) {
        String sql = MessageFormat.format(Sql.QUERY, " COUNT(1)", tableName, conditionSql);
        return getJdbcTemplate().queryForObject(sql, conditionObjs, Long.class);
    }

    /**
     * 根据条件查询数据列表总数
     * 
     * @author 伍章红 2015年11月8日 上午10:25:28
     * @param tableName
     *            表名
     * @param conditionColumns
     *            查询条件字段集合
     * @param conditionObjs
     *            查询条件集合。conditionColumns和conditionObjs数组的排列顺序需要一致
     * @return
     */
    public long findEntityTotalByParam(String tableName, String[] conditionColumns, Object[] conditionObjs) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.findEntityTotalByParam(tableName, conditionSql.toString(), conditionObjs);
    }

}
