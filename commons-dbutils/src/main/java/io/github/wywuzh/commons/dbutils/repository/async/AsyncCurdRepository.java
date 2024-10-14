/*
 * Copyright 2015-2024 the original author or authors.
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.github.wywuzh.commons.dbutils.Sql;
import io.github.wywuzh.commons.dbutils.annotation.BeanAnnotationUtil;
import io.github.wywuzh.commons.dbutils.entity.AbstractEntity;
import io.github.wywuzh.commons.dbutils.vo.AbstractVo;
import io.github.wywuzh.commons.pager.PaginationObject;
import io.github.wywuzh.commons.pager.PaginationParameter;

/**
 * 类CURDRepository.java的实现描述：基于Java Bean对象的数据操作
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2017年7月2日 下午8:39:51
 * @version v1.0.0
 * @since JDK 1.7
 */
public class AsyncCurdRepository<E extends AbstractEntity, V extends AbstractVo> extends AsyncPaginationRepository<E, V> {
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * 获取实体类的表名
     *
     * <pre>
     * 注：实体类&lt;E&gt;上必须存在<code>@table</code>元数据标注，否则返回为null.
     *
     * 例子：
     * &#64;Table(name = "SECURITY_USER")
     * public class UserPo implements Serializable {
     *     private static final long serialVersionUID = 1L;
     *
     *     &#64;Column(name = "ID")
     *     private String id;
     *
     *     // 省略getter、setter方法...
     * }
     * </pre>
     *
     * @return 实体类E的表名
     */
    public String getTableName() {
        Class<E> entityClass = getEntityClass();
        if (null == entityClass) {
            return null;
        }
        return BeanAnnotationUtil.getTableName(entityClass);
    }

    /**
     * 取得查询SQL语句中column字段在实体类&lt;E&gt;中<code>@Column</code>对应的field字段。当column中已经存在<code>as</code>关键字时，则过滤此column；
     * 如果实体类&lt;E&gt;中存在<code>@Column</code>对应的field字段，则用此field做别名；否则采用此column的小写作为别名。格式为：COLUMN AS field
     *
     * <pre>
     * 例1（column中包含as关键字）：传入column为<code>ID as id,CODE,NAME</code>，返回则是<code>ID as id,CODE AS code,NAME AS name</code>
     * 例2（实体类&lt;E&gt;中存在<code>@Column</code>对应的field字段）：传入column为<code>ID,CODE,NAME</code>，返回则是<code>ID AS userId,CODE AS code,NAME AS name</code>
     * 例3：传入column为<code>ID,CODE,NAME</code>，返回则是<code>ID AS id,CODE AS code,NAME AS name</code>
     *
     * 例子：
     * &#64;Table(name = "SECURITY_USER")
     * public class UserPo implements Serializable {
     *     private static final long serialVersionUID = 1L;
     *
     *     &#64;Column(name = "ID")
     *     private String userId;
     *
     *     // 省略getter、setter方法...
     * }
     * </pre>
     *
     * @param columns 查询SQL的column字段
     * @return 实体类中field字段对应的SQL字段
     */
    public String mappingQueryColumns(String columns) {
        if (StringUtils.isEmpty(columns)) {
            return "";
        }

        StringBuffer columnBuffer = new StringBuffer();
        String[] columnArr = columns.split(",");
        // 取得<E>实体类中表字段映射数据：key=column,value=field
        Map<String, String> columnsMap = BeanAnnotationUtil.getColumnsMap(getEntityClass());
        for (int i = 0; i < columnArr.length; i++) {
            if (i > 0) {
                columnBuffer.append(",");
            }
            String column = columnArr[i];
            if (StringUtils.containsIgnoreCase(column, Sql.QUERY_AS)) {
                columnBuffer.append(column);
                continue;
            }

            String field = null;
            if (columnsMap.containsKey(column.toUpperCase())) {
                field = columnsMap.get(column.toUpperCase());
            }
            if (StringUtils.isEmpty(field)) {
                field = column.toLowerCase();
            }
            columnBuffer.append(column).append(Sql.QUERY_AS).append(field);
        }
        return columnBuffer.toString();
    }

    /**
     * 获取实体类&lt;E&gt;中查询column
     *
     * @return 实体类E中&#64;Column映射的表字段
     */
    public String getQueryColumns() {
        return getQueryColumns(getEntityClass());
    }

    /**
     * 获取指定实体类clazz中查询column
     *
     * <pre>
     * 实体类：
     * public class JobDetailPo implements Serializable {
     *     private static final long serialVersionUID = 1L;
     *
     *     &#64;Column(name = "JOB_KEY_ID")
     *     private String jobKeyId;
     *
     *     &#64;Column(name = "JOB_CLASS")
     *     private String jobClass;
     *
     *     &#64;Column(name = "IS_DELETE")
     *     private String isDelete;
     *
     *     &#64;Column(name = "DESCRIPTION")
     *     private String description;
     *
     *     // 省略getter、setter方法...
     * }
     *
     * 对应的查询SQL column：
     * KEY_NAME AS keyName,CREATE_TIME AS createTime,DESCRIPTION AS description,CREATE_USER AS createUser,KEY_GROUP AS keyGroup
     * </pre>
     *
     * @param clazz 指定实体类
     * @return Class类中&#64;Column映射的表字段
     */
    public String getQueryColumns(Class<?> clazz) {
        Map<String, String> columnsMap = BeanAnnotationUtil.getColumnsMap(clazz);
        if (columnsMap == null || columnsMap.size() == 0) {
            return "";
        }

        StringBuffer columnBuffer = new StringBuffer();
        for (String column : columnsMap.keySet()) {
            if (columnBuffer.length() > 0) {
                columnBuffer.append(",");
            }
            columnBuffer.append(column).append(Sql.QUERY_AS).append(columnsMap.get(column));
        }
        return columnBuffer.toString();
    }

    /**
     * 获取实体类中的字段数据。key为field，value为value
     *
     * @param entity    实体类
     * @param hasSerial 是否含有序列化serialVersionUID字段
     * @return 返回包含filed字段及其value值的Map集合
     */
    public <T> Map<String, Object> getFields(T entity, boolean hasSerial) {
        if (null == entity) {
            return null;
        }

        Map<String, Object> fieldMap = new HashMap<String, Object>();
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(entity.getClass());
        for (Field field : allFieldsList) {
            if (!hasSerial) {
                if (StringUtils.equalsIgnoreCase("serialVersionUID", field.getName())) {
                    continue;
                }
            }

            field.setAccessible(true);
            try {
                fieldMap.put(field.getName(), field.get(entity));
            } catch (IllegalArgumentException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
        }
        return fieldMap;
    }

    /**
     * 新增数据
     *
     * @param entity 实体类对象
     * @return 返回null表示新增失败，否则新增成功
     * @throws SQLException
     */
    public E insert(E entity) throws SQLException {
        Map<String, String> columnsMap = BeanAnnotationUtil.getColumnsMap(getEntityClass());
        String[] columns = new String[columnsMap.size()];
        Object[] values = new Object[columnsMap.size()];
        int index = 0;
        for (String column : columnsMap.keySet()) {
            columns[index] = column;
            Object value = null;
            try {
                value = PropertyUtils.getProperty(entity, columnsMap.get(column));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            values[index] = value;
            index++;
        }

        if (this.insert(getTableName(), columns, values) > 0) {
            return entity;
        }
        return null;
    }

    /**
     * 批量新增数据
     *
     * @param list 实体类对象集合
     * @return 返回null表示新增失败，否则新增成功
     * @throws SQLException
     */
    public int[] batchInsert(List<E> list) throws SQLException {
        Map<String, String> columnsMap = BeanAnnotationUtil.getColumnsMap(getEntityClass());
        String[] columns = new String[columnsMap.size()];
        Object[][] valueslist = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            Object[] values = new Object[columnsMap.size()];
            int index = 0;
            for (String column : columnsMap.keySet()) {
                columns[index] = column;
                Object value = null;
                try {
                    value = PropertyUtils.getProperty(list.get(i), columnsMap.get(column));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                values[index] = value;
                index++;
            }
            valueslist[i] = values;
        }

        return this.batchInsert(getTableName(), columns, valueslist);
    }

    /**
     * 修改数据
     *
     * @param entity 实体类对象
     * @return 返回null表示更新失败，否则更新成功
     * @throws SQLException
     */
    public E update(E entity) throws SQLException {
        Map<String, String> columnsMap = BeanAnnotationUtil.getColumnsMap(entity.getClass());
        String[] columns = new String[columnsMap.size()];
        Object[] values = new Object[columnsMap.size()];
        String[] conditionColumns = {
                "ID"
        };
        Object[] conditionObjs = {
                entity.getId()
        };

        int index = 0;
        for (String column : columnsMap.keySet()) {
            columns[index] = column;
            Object value = null;
            try {
                value = PropertyUtils.getProperty(entity, columnsMap.get(column));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            values[index] = value;
            index++;
        }

        if (this.update(getTableName(), columns, values, conditionColumns, conditionObjs) > 0) {
            return entity;
        }
        return null;
    }

    /**
     * 根据主键id删除数据
     *
     * @param id 主键id
     * @return 返回1表示删除成功，否则删除失败
     * @throws SQLException
     */
    public int delete(String id) throws SQLException {
        return this.delete(getTableName(), " AND ID=?", new Object[] {
                id
        });
    }

    /**
     * 批量删除数据
     *
     * @param ids 主键id集合
     * @return 返回1表示删除成功，否则删除失败
     * @throws SQLException
     */
    public int[] batchDelete(String[] ids) throws SQLException {
        String sql = MessageFormat.format(Sql.DELETE, getTableName(), " AND ID=?");
        Object[][] params = new Object[ids.length][];
        for (int i = 0; i < ids.length; i++) {
            params[i] = new Object[] {
                    ids[i]
            };
        }
        return this.batchExecute(sql, params);
    }

    /**
     * 根据主键id查询实体类对应的表数据信息
     *
     * @param id 主键id
     * @return 返回主键id对应的实体类
     * @throws SQLException
     */
    public E get(String id) throws SQLException {
        return this.get(getTableName(), getQueryColumns(), id);
    }

    /**
     * 根据条件查询实体表数据列表
     *
     * @param vo 查询条件
     * @return 返回查询结果数据列表
     * @throws SQLException
     */
    public List<E> queryEntityList(V vo) throws SQLException {
        // 取得vo类的查询字段数据
        Map<String, Object> fields = getFields(vo, false);
        // 取得po类的字段映射数据
        Map<String, String> fieldsMap = BeanAnnotationUtil.getFieldsMap(getEntityClass());

        List<String> conditionColumns = new ArrayList<String>();
        List<Object> conditionObjs = new ArrayList<Object>();
        for (String field : fields.keySet()) {
            Object value = fields.get(field);
            if (null == value) {
                continue;
            }
            if (value instanceof String) {
                if (StringUtils.isBlank(value.toString())) {
                    continue;
                }
            }
            // 条件字段
            conditionColumns.add(fieldsMap.get(field));
            // 条件
            conditionObjs.add(value);
        }

        return this.queryForList(getTableName(), getQueryColumns(), conditionColumns.toArray(new String[0]), conditionObjs.toArray());
    }

    /**
     * 根据条件查询实体表数据列表总数
     *
     * @param vo 查询条件
     * @return 返回查询结果数据列表总数
     * @throws SQLException
     */
    public long queryEntityTotal(V vo) throws SQLException {
        // 取得vo类的查询字段数据
        Map<String, Object> fields = getFields(vo, false);
        // 取得po类的字段映射数据
        Map<String, String> fieldsMap = BeanAnnotationUtil.getFieldsMap(getEntityClass());

        List<String> conditionColumns = new ArrayList<String>();
        List<Object> conditionObjs = new ArrayList<Object>();
        for (String field : fields.keySet()) {
            Object value = fields.get(field);
            if (null == value) {
                continue;
            }
            if (value instanceof String) {
                if (StringUtils.isBlank(value.toString())) {
                    continue;
                }
            }
            // 条件字段
            conditionColumns.add(fieldsMap.get(field));
            // 条件
            conditionObjs.add(value);
        }
        return this.queryForLong(getTableName(), "COUNT(1)", conditionColumns.toArray(new String[0]), conditionObjs.toArray());
    }

    /**
     * 根据条件查询分页数据
     *
     * @param paginationParamter 分页参数
     * @return 返回分页数据信息
     * @throws SQLException
     */
    public PaginationObject<E, V> queryForPager(PaginationParameter<V> paginationParamter) throws SQLException {
        // 取得vo类的查询字段数据
        Map<String, Object> fields = getFields(paginationParamter.getVo(), false);
        // 取得po类的字段映射数据
        Map<String, String> fieldsMap = BeanAnnotationUtil.getFieldsMap(getEntityClass());

        List<String> conditionColumns = new ArrayList<String>();
        List<Object> conditionObjs = new ArrayList<Object>();
        for (String field : fields.keySet()) {
            Object value = fields.get(field);
            if (null == value) {
                continue;
            }
            if (value instanceof String) {
                if (StringUtils.isBlank(value.toString())) {
                    continue;
                }
            }
            // 条件字段
            conditionColumns.add(fieldsMap.get(field));
            // 条件
            conditionObjs.add(value);
        }

        // 查询数据总数
        Long rowCount = this.queryForLong(getTableName(), "COUNT(1)", conditionColumns.toArray(new String[0]), conditionObjs.toArray());
        List<E> resultList = new ArrayList<E>();
        if (rowCount > 0) {
            // 查询数据
            resultList = this.queryForList(getTableName(), getQueryColumns(), conditionColumns.toArray(new String[0]), conditionObjs.toArray(), paginationParamter.getPageNo(),
                    paginationParamter.getPageSize());
        }

        PaginationObject<E, V> paginationObject = new PaginationObject<E, V>(rowCount, resultList);
        paginationObject.setPageNo(paginationParamter.getPageNo());
        paginationObject.setPageSize(paginationParamter.getPageSize());
        paginationObject.setVo(paginationParamter.getVo());
        return paginationObject;
    }

}
