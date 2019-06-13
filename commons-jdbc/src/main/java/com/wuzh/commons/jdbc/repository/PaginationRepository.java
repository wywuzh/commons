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

import com.wuzh.commons.jdbc.DataBase;
import com.wuzh.commons.jdbc.Sql;
import com.wuzh.commons.jdbc.Type;
import com.wuzh.commons.jdbc.entity.AbstractEntity;
import com.wuzh.commons.jdbc.vo.AbstractVo;
import com.wuzh.commons.pager.PaginationObject;
import com.wuzh.commons.pager.PaginationParameter;
import com.wuzh.commons.pager.Sort;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 类PaginationRepository.java的实现描述：自定义框架持久化接口实现类（含分页信息）
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2015年11月6日 下午3:05:24
 * @version v1.0.0
 * @since JDK 1.7
 */
public class PaginationRepository<E extends AbstractEntity, V extends AbstractVo> extends BasicRepository<E, V> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @author 伍章红 2015年11月6日 下午5:21:31
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionSql
     *            查询条件字段SQL。如 and code=? and name=?
     * @param conditionObjs
     *            查询条件值。需要注意，条件数组字段的顺序需要和条件SQL字段的顺序一致
     * @param paginationParameter
     *            分页查询请求，包含分页信息、查询条件信息
     * @param rowMapper
     *            查询结果映射
     * @return
     */
    public PaginationObject<E, V> findPaginationObjectByNativeSQL(String tableName, String columns, String conditionSql,
                                                                  Object[] conditionObjs, PaginationParameter<V> paginationParameter, RowMapper<E> rowMapper) {
        // 查询数据总数
        String rowCountSql = MessageFormat.format(Sql.QUERY, "COUNT(1)", tableName, conditionSql);
        Long rowCount = getJdbcTemplate().queryForObject(rowCountSql, Long.class, conditionObjs);

        // 查询分页列表
        StringBuilder resultListSql = new StringBuilder(
                MessageFormat.format(Sql.QUERY, columns, tableName, conditionSql));
        // 添加排序信息
        List<Sort> sorts = paginationParameter.getSorts();
        if (null != sorts && sorts.size() > 0) {
            StringBuilder sortStr = new StringBuilder();
            for (int i = 0; i < sorts.size(); i++) {
                if (StringUtils.isNotBlank(sortStr)) {
                    sortStr.append(",");
                }
                sortStr.append(sorts.get(i).getSort()).append(" ").append(sorts.get(i).getOrder().getValue());
            }
            if (StringUtils.isNotBlank(sortStr)) {
                resultListSql.append(" ORDER BY ").append(sortStr);
            }
        }
        // 添加分页信息
        resultListSql.append(" LIMIT ?,?");

        // 组装查询条件
        Object[] args = new Object[conditionObjs.length + 2];
        for (int i = 0; i < conditionObjs.length; i++) {
            args[i] = conditionObjs[i];
        }
        args[args.length - 2] = paginationParameter.getOffSet();
        args[args.length - 1] = paginationParameter.getPageSize();
        List<E> resultList = getJdbcTemplate().query(resultListSql.toString(), args, rowMapper);

        PaginationObject<E, V> paginationObject = new PaginationObject<E, V>(rowCount, resultList);
        paginationObject.setPageNo(paginationParameter.getPageNo());
        paginationObject.setPageSize(paginationParameter.getPageSize());
        paginationObject.setVo(paginationParameter.getVo());
        return paginationObject;
    }

    /**
     * @author 伍章红 2015年11月8日 上午10:48:11
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionColumns
     *            查询条件字段集合
     * @param conditionObjs
     *            查询条件。需要注意，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param paginationParameter
     * @param rowMapper
     * @return
     */
    public PaginationObject<E, V> findPaginationObjectByNativeSQL(String tableName, String columns,
                                                                  String[] conditionColumns, Object[] conditionObjs, PaginationParameter<V> paginationParameter,
                                                                  RowMapper<E> rowMapper) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.findPaginationObjectByNativeSQL(tableName, columns, conditionSql.toString(), conditionObjs,
                paginationParameter, rowMapper);
    }

    /**
     * @author 伍章红 2015年11月9日 下午2:16:28
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionSql
     *            查询条件字段SQL。如 and code=? and name=?
     * @param conditionObjs
     *            查询条件。需要注意，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sortMap
     *            排序映射对象集合。key为实体类字段，value为表字段。如{"code":"CODE","name":"NAME"}
     * @param paginationParameter
     * @param rowMapper
     * @return
     * @deprecated 如需排序，请使用paginationParameter中的sorts字段
     */
    @Deprecated
    public PaginationObject<E, V> findPaginationObjectByNativeSQL(String tableName, String columns, String conditionSql,
                                                                  Object[] conditionObjs, Map<String, String> sortMap, PaginationParameter<V> paginationParameter,
                                                                  RowMapper<E> rowMapper) {
        // 查询数据总数
        String rowCountSql = MessageFormat.format(Sql.QUERY, "COUNT(1)", tableName, conditionSql);
        Long rowCount = getJdbcTemplate().queryForObject(rowCountSql, Long.class, conditionObjs);

        // 查询分页列表
        StringBuilder resultListSql = new StringBuilder(
                MessageFormat.format(Sql.QUERY, columns, tableName, conditionSql));
        // 添加排序信息
        List<Sort> sorts = paginationParameter.getSorts();
        if (null != sorts && sorts.size() > 0) {
            StringBuilder sortStr = new StringBuilder();
            for (int i = 0; i < conditionObjs.length; i++) {
                if (StringUtils.isNotBlank(sortStr)) {
                    sortStr.append(",");
                }
                sortStr.append(sorts.get(i).getSort()).append(" ").append(sorts.get(i).getOrder().getValue());
            }
            if (StringUtils.isNotBlank(sortStr)) {
                resultListSql.append(" ORDER BY ").append(sortStr);
            }
        }

        // 添加分页信息
        resultListSql.append(" LIMIT ?,?");

        // 组装查询条件
        Object[] args = new Object[conditionObjs.length + 2];
        for (int i = 0; i < conditionObjs.length; i++) {
            args[i] = conditionObjs[i];
        }
        args[args.length - 2] = paginationParameter.getOffSet();
        args[args.length - 1] = paginationParameter.getPageSize();
        List<E> resultList = getJdbcTemplate().query(resultListSql.toString(), args, rowMapper);

        PaginationObject<E, V> paginationObject = new PaginationObject<E, V>(rowCount, resultList);
        paginationObject.setPageNo(paginationParameter.getPageNo());
        paginationObject.setPageSize(paginationParameter.getPageSize());
        paginationObject.setVo(paginationParameter.getVo());
        return paginationObject;
    }

    /**
     * @author 伍章红 2015年11月9日 下午2:16:30
     * @param tableName
     *            表名
     * @param columns
     *            查询字段名。如 column1,column2,column3
     * @param conditionColumns
     *            查询条件字段集合
     * @param conditionObjs
     *            查询条件。需要注意，条件数组字段的顺序需要和查询条件字段的顺序一致
     * @param sortMap
     *            排序映射对象集合。key为实体类字段，value为表字段。如{"code":"CODE","name":"NAME"}
     * @param paginationParameter
     * @param rowMapper
     * @return
     * @deprecated
     */
    @Deprecated
    public PaginationObject<E, V> findPaginationObjectByNativeSQL(String tableName, String columns,
                                                                  String[] conditionColumns, Object[] conditionObjs, Map<String, String> sortMap,
                                                                  PaginationParameter<V> paginationParameter, RowMapper<E> rowMapper) {
        StringBuilder conditionSql = new StringBuilder();
        for (int i = 0; i < conditionColumns.length; i++) {
            conditionSql.append(" AND ").append(conditionColumns[i]).append("=?");
        }
        return this.findPaginationObjectByNativeSQL(tableName, columns, conditionSql.toString(), conditionObjs, sortMap,
                paginationParameter, rowMapper);
    }

    /**
     * @author 伍章红 2015年11月6日 下午5:28:06
     * @param tableName
     *            表名
     * @param sql
     *            查询SQL语句。如 select * from table where column1=? and column2=?
     * @param conditionObjs
     *            查询条件。需要注意，条件数组字段的顺序需要和条件SQL字段的顺序一致
     * @param paginationParameter
     *            分页查询请求，包含分页信息、查询条件信息
     * @param rowMapper
     *            查询结果映射
     * @return
     */
    public PaginationObject<E, V> findPaginationObjectByNativeSQL(String tableName, String sql, Object[] conditionObjs,
                                                                  PaginationParameter<V> paginationParameter, RowMapper<E> rowMapper) {
        // 查询数据总数
        String rowCountSql = MessageFormat.format(Sql.QUERY, "COUNT(1)", tableName);
        Long rowCount = getJdbcTemplate().queryForObject(rowCountSql, Long.class);

        // 查询分页列表
        String resultListSql = sql + " LIMIT ?,?";
        Object[] args = new Object[] {
                conditionObjs, paginationParameter.getOffSet(), paginationParameter.getPageSize()
        };
        List<E> resultList = getJdbcTemplate().query(resultListSql, args, rowMapper);

        PaginationObject<E, V> paginationObject = new PaginationObject<E, V>(rowCount, resultList);
        paginationObject.setPageNo(paginationParameter.getPageNo());
        paginationObject.setPageSize(paginationParameter.getPageSize());
        paginationObject.setVo(paginationParameter.getVo());
        return paginationObject;
    }

    /**
     * 生成分页SQL语句
     * 
     * @param querySql
     *            查询语句
     * @param paginationParameter
     *            分页参数
     * @return 根据Connection连接信息生成分页SQL语句
     */
    public String generatePageSql(String querySql, PaginationParameter<V> paginationParameter) {
        StringBuilder pageSql = new StringBuilder();
        try {
            DataBase dataBase = new DataBase(getDataSource().getConnection());
            Type type = dataBase.getProduct().getType();
            if (Type.MySQL.equals(type)) {
                pageSql.append(querySql);
                pageSql.append(" LIMIT ").append(paginationParameter.getOffSet()).append(",")
                        .append(paginationParameter.getPageSize());
            } else if (Type.Oracle.equals(type)) {
                pageSql.append("SELECT * FROM (SELECT A.*,ROWNUM RN FROM (");
                pageSql.append(querySql);
                pageSql.append(")A WHERE ROWNUM <= ").append(paginationParameter.getEndSet());
                pageSql.append(") WHERE RN > ").append(paginationParameter.getOffSet());
            }
        } catch (SQLException e) {
            logger.error("生成分页SQL语句异常：", e);
        }
        return pageSql.toString();
    }
}
