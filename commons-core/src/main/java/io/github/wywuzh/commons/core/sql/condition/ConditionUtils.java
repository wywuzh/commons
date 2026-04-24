/*
 * Copyright 2015-2026 the original author or authors.
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
package io.github.wywuzh.commons.core.sql.condition;

import io.github.wywuzh.commons.core.common.Constants;
import io.github.wywuzh.commons.core.util.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 类ConditionUtils的实现描述：SQL Where条件工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-03-05 15:36:40
 * @version v3.5.0
 * @since JDK 17
 */
@Slf4j
public class ConditionUtils {


    /**
     * SQL Where条件字段匹配类型
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @return Where条件字段匹配类型
     */
    public static String getConditionType(Map<String, String> conditionColumnMap, String field) {
        // 1、精确匹配
        String columnName = conditionColumnMap.get(field);
        if (StringUtils.isNotBlank(columnName)) {
            return "equals";
        }
        // 2、模糊匹配
        String columnNameLike = conditionColumnMap.get(StringUtils.replace(field, "Like", ""));
        if (StringUtils.isNotBlank(columnNameLike)) {
            return "like";
        }
        columnNameLike = conditionColumnMap.get(StringUtils.replaceIgnoreCase(field, "_LIKE", ""));
        if (StringUtils.isNotBlank(columnNameLike)) {
            return "like";
        }
        // 3、foreach匹配
        if (!StringUtils.startsWith(field, "notIn") && StringUtils.endsWith(field, "List")) {
            return "list";
        }
        if (!StringUtils.startsWith(field, "NOT_IN_") && StringUtils.endsWith(field, "_LIST")) {
            return "list";
        }
        // 4、foreach匹配超过1000时改用union查询 or 模糊查询改用in查询
        if (StringUtils.endsWith(field, "Unions")) {
            return "unions";
        }
        if (StringUtils.endsWith(field, "_UNIONS")) {
            return "unions";
        }
        // 5、精确匹配表字段名：此方式出现在以表字段名为key传入的场景
        if (conditionColumnMap.containsValue(field)) {
            return "column";
        }
        // 6、notIn匹配
        if (StringUtils.startsWith(field, "notIn")) {
            return "notIn";
        }
        if (StringUtils.startsWith(field, "NOT_IN_")) {
            return "notIn";
        }
        return null;
    }

    /**
     * SQL Where条件字段名称
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @return Where条件字段名称
     */
    public static String getConditionColumnName(Map<String, String> conditionColumnMap, String field) {
        // Where条件字段匹配类型
        String conditionType = ConditionUtils.getConditionType(conditionColumnMap, field);
        return ConditionUtils.getConditionColumnName(conditionColumnMap, field, conditionType);
    }

    /**
     * SQL Where条件字段名称
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @param conditionType      Where条件字段匹配类型
     * @return Where条件字段名称
     */
    public static String getConditionColumnName(Map<String, String> conditionColumnMap, String field, String conditionType) {
        String conditionColumnName = null;
        if (StringUtils.isBlank(conditionType)) {
            return null;
        }
        switch (conditionType) {
            case "equals":
                // 1、精确匹配
                String columnName = conditionColumnMap.get(field);
                conditionColumnName = columnName;
                break;
            case "like":
                // 2、模糊匹配
                String columnNameLike = getConditionColumnNameLike(conditionColumnMap, field);
                conditionColumnName = columnNameLike;
                break;
            case "list":
                // 3、foreach匹配
                String columnNameForeach = getConditionColumnNameForeach(conditionColumnMap, field);
                conditionColumnName = columnNameForeach;
                break;
            case "unions":
                // 4、foreach匹配超过1000时改用union查询 or 模糊查询改用in查询
                String columnNameForeachUnions = getConditionColumnNameForeachUnions(conditionColumnMap, field);
                conditionColumnName = columnNameForeachUnions;
                break;
            case "column":
                // 5、精确匹配表字段名：此方式出现在以表字段名为key传入的场景，例如导出时的子页签
                conditionColumnName = field;
                break;
            case "notIn":
                // 6、notIn匹配
                String columnNameNotIn = getConditionColumnNameNotIn(conditionColumnMap, field);
                conditionColumnName = columnNameNotIn;
                break;
            default:
                break;
        }
        return conditionColumnName;
    }

    /**
     * SQL Where条件字段名称：模糊匹配
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @return
     */
    public static String getConditionColumnNameLike(Map<String, String> conditionColumnMap, String field) {
        // 2、模糊匹配
        // 2.1 剔除“Like”后缀。eg：idLike -> id
        String after = StringUtils.replace(field, "Like", "");
        if (!conditionColumnMap.containsValue(after)) {
            // 2.2 剔除“_LIKE”后缀。eg：ID_LIKE -> ID
            after = StringUtils.replace(after, "_LIKE", "");
        }
        String columnNameLike = conditionColumnMap.get(after);
        return columnNameLike;
    }

    /**
     * SQL Where条件字段名称：foreach匹配
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @return
     */
    public static String getConditionColumnNameForeach(Map<String, String> conditionColumnMap, String field) {
        // 3、foreach匹配
        // 3.1 剔除“List”后缀。eg：idList -> id
        String after = StringUtils.replace(field, "List", "");
        if (!conditionColumnMap.containsValue(after)) {
            // 3.2 剔除“_LIST”后缀。eg：ID_LIST -> ID
            after = StringUtils.replace(after, "_LIST", "");
        }
        String columnNameForeach = conditionColumnMap.get(after);
        return columnNameForeach;
    }

    /**
     * SQL Where条件字段名称：foreachUnion匹配
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @return
     */
    public static String getConditionColumnNameForeachUnions(Map<String, String> conditionColumnMap, String field) {
        // 4、foreach匹配超过1000时改用union查询 or 模糊查询改用in查询
        // 4.1 剔除“Unions”后缀。eg：idUnions -> id
        String after = StringUtils.replace(field, "Unions", "");
        if (!conditionColumnMap.containsValue(after)) {
            // 4.2 剔除“UnionList”后缀。eg：idUnionList -> id
            after = StringUtils.replace(after, "UnionList", "");
        }
        if (!conditionColumnMap.containsValue(after)) {
            // 4.3 剔除“_UNION_LIST”后缀。eg：ID_UNION_LIST -> ID
            after = StringUtils.replace(after, "_UNION_LIST", "");
        }
        String columnNameForeachUnions = conditionColumnMap.get(after);
        return columnNameForeachUnions;
    }

    /**
     * SQL Where条件字段名称：notIn匹配
     *
     * @param conditionColumnMap 查询条件字段：key=查询条件字段名, value=表字段名
     * @param field              查询条件字段名
     * @return
     */
    public static String getConditionColumnNameNotIn(Map<String, String> conditionColumnMap, String field) {
        // 6、notIn匹配
        // 6.1 剔除“notIn”前缀。eg：notInIdList -> IdList
        String after = StringUtils.replace(field, "notIn", "");
        if (!conditionColumnMap.containsValue(after)) {
            // 6.2 剔除“NOT_IN_”前缀。eg：NOT_IN_ID_LIST -> ID_LIST
            after = StringUtils.replace(after, "NOT_IN_", "");
        }
        if (!conditionColumnMap.containsValue(after) && StringUtils.endsWith(after, "List")) {
            // 6.3 剔除“List”后缀。eg：notInIdList -> IdList -> Id
            after = StringUtils.replace(after, "List", "");
            // 首字母改为小写。eg：notInIdList -> IdList -> Id -> id
            after = StringHelper.firstCharToLowerCase(after);
        }
        if (!conditionColumnMap.containsValue(after)) {
            // 6.4 剔除“_LIST”后缀。eg：NOT_IN_ID_LIST -> ID_LIST -> ID
            after = StringUtils.replace(after, "_LIST", "");
        }
        String columnNameNotIn = conditionColumnMap.get(after);
        return columnNameNotIn;
    }

    /**
     * 创建SQL Where条件
     *
     * @param columnNameAliasMap 表字段别名映射：key=表字段别名, value=字段中文描述。来源自【公共平台-系统字段表】
     * @param conditionColumnMap 查询条件字段：key=页面查询条件字段名, value=表字段名
     * @param field              页面查询条件字段名
     * @param searchMap          页面查询请求条件
     * @return
     */
    public static String createConditionSql(Map<String, String> columnNameAliasMap, Map<String, String> conditionColumnMap, String field,
                                            Map<String, Object> searchMap) {
        List<String> columnNameAliasList = new LinkedList<String>(columnNameAliasMap.keySet());
        return ConditionUtils.createConditionSql(columnNameAliasList, conditionColumnMap, field, searchMap);
    }

    /**
     * 创建SQL Where条件
     *
     * @param columnNameAliasList 表字段别名。来源自【公共平台-系统字段表】
     * @param conditionColumnMap  查询条件字段：key=页面查询条件字段名, value=表字段名
     * @param field               页面查询条件字段名：实体类字段名/表字段名
     * @param searchMap           页面查询请求条件
     * @return
     * @since v2.1.6
     */
    public static String createConditionSql(List<String> columnNameAliasList, Map<String, String> conditionColumnMap, String field,
                                            Map<String, Object> searchMap) {
        // Where条件字段匹配类型
        String conditionType = ConditionUtils.getConditionType(conditionColumnMap, field);
        // SQL Where条件字段名称
        String conditionColumnName = ConditionUtils.getConditionColumnName(conditionColumnMap, field, conditionType);
        if (StringUtils.isBlank(conditionColumnName)) {
            // 【数据字典-TABLE_ENTRY_CONDITION_COLUMN】配置的映射条件字段在【公共平台-系统字段表】表没有，跳过该字段，减少where条件列
            return null;
        }
        if (!columnNameAliasList.contains(conditionColumnName)) {
            if (StringUtils.contains(conditionColumnName, Constants.SEPARATE_SPOT)) { // 包含表别名前缀
                String conditionColumnNameAfter = StringUtils.substringAfter(conditionColumnName, Constants.SEPARATE_SPOT);
                if (!columnNameAliasList.contains(conditionColumnNameAfter)) {
                    return null;
                }
            } else { // 不包含表别名前缀
                // 【数据字典-TABLE_ENTRY_CONDITION_COLUMN】配置的映射条件字段在【公共平台-系统字段表】表没有，跳过该字段，减少where条件列
                return null;
            }
        }

        return createConditionSql(conditionColumnName, conditionType, searchMap, field);
    }

    /**
     * 创建SQL Where条件
     *
     * @param conditionColumnName 查询条件字段：表字段名
     * @param conditionType       查询条件字段匹配类型：equals=精确匹配, like=模糊匹配, list=foreach匹配, unions=foreach匹配超过1000时改用union查询, column=精确匹配表字段名
     * @param searchMap           页面查询请求条件
     * @param field               页面查询条件字段名：实体类字段名/表字段名
     * @return
     * @since v1.0.1
     */
    public static String createConditionSql(String conditionColumnName, String conditionType, Map<String, Object> searchMap, String field) {
        String conditionSql = null;
        switch (conditionType) {
            case "equals":
                // 1、精确匹配
                String fieldValue = MapUtils.getString(searchMap, field);
                if (StringUtils.isNotBlank(fieldValue)) {
                    conditionSql = StringUtils.join("AND ", conditionColumnName, " = '", fieldValue, "'");
                }
                break;
            case "like":
                // 2、模糊匹配
                String fieldValueLike = MapUtils.getString(searchMap, field);
                if (StringUtils.isNotBlank(fieldValueLike)) {
                    if (StringUtils.equals(fieldValueLike, "空值")) {
                        // 空值格式。eg：AND ( basic.SHARE_MANAGE_REGION_NAME IS NULL or basic.SHARE_MANAGE_REGION_NAME = '' )
                        conditionSql = String.format("AND ( %s IS NULL OR %s = '' )", conditionColumnName, conditionColumnName);
                    } else {
                        // 视图类型：basic=用户页面, monitor=后台监控页面
                        String viewType = MapUtils.getString(searchMap, "viewType", "basic");
                        // 支持逗号分隔的多字段查询
                        if (StringUtils.contains(fieldValueLike, ",") && StringUtils.equals(viewType, "monitor")) {
                            // 按逗号分割并去除空格
                            String[] values = StringUtils.split(fieldValueLike, ",");
                            List<String> likeConditions = new LinkedList<>();
                            for (String value : values) {
                                String trimmedValue = StringUtils.trim(value);
                                if (StringUtils.isNotBlank(trimmedValue)) {
                                    likeConditions.add(StringUtils.join(conditionColumnName, " LIKE '%", trimmedValue, "%'"));
                                }
                            }
                            if (CollectionUtils.isNotEmpty(likeConditions)) {
                                // 多个条件用 OR 连接，并用括号包裹
                                conditionSql = StringUtils.join("AND ( ", StringUtils.join(likeConditions, " OR "), " )");
                            }
                        } else {
                            // 单个值的情况，保持原有逻辑
                            conditionSql = StringUtils.join("AND ", conditionColumnName, " LIKE '%", fieldValueLike, "%'");
                        }
                    }
                }
                break;
            case "list":
                // 3、foreach匹配
                List<String> fieldValueForeach = (List<String>) searchMap.get(field);
                if (CollectionUtils.isNotEmpty(fieldValueForeach)) {
                    // 对查询条件中存在“无”的数据做兼容
                    if (fieldValueForeach.contains(StringUtils.EMPTY) || fieldValueForeach.contains("BLANK_ITEM") || fieldValueForeach.contains("999")) {
                        conditionSql = String.format("AND ( %s in ( '%s' ) OR %s IS NULL OR %s = '' )",
                                conditionColumnName, StringUtils.join(fieldValueForeach, "','"), conditionColumnName, conditionColumnName);
                    } else {
                        conditionSql = StringUtils.join("AND ", conditionColumnName, " in ( '", StringUtils.join(fieldValueForeach, "','"), "' )");
                    }
                }
                break;
            case "unions":
                // 4、foreach匹配超过1000时改用union查询 or 模糊查询改用in查询
                List<List<String>> fieldValueForeachUnions = (List<List<String>>) searchMap.get(field);
                if (CollectionUtils.isNotEmpty(fieldValueForeachUnions)) {
                    List<String> conditions = new LinkedList<>();
                    for (List<String> list : fieldValueForeachUnions) {
                        conditions.add(StringUtils.join(conditionColumnName, " in ( '", StringUtils.join(list, "','"), "' )"));
                    }
                    conditionSql = String.format("AND ( %s )", StringUtils.join(conditions, " or "));
                }
                break;
            case "column":
                // 5、精确匹配表字段名：此方式出现在以表字段名为key传入的场景，例如导出时的子页签
                String fieldValueColumn = MapUtils.getString(searchMap, field);
                if (StringUtils.isNotBlank(fieldValueColumn)) {
                    conditionSql = StringUtils.join("AND ", field, " = '", fieldValueColumn, "'");
                }
                break;
            case "notIn":
                // 6、notIn匹配
                List<String> fieldValueNotIn = (List<String>) searchMap.get(field);
                if (CollectionUtils.isNotEmpty(fieldValueNotIn)) {
                    conditionSql = StringUtils.join("AND ", conditionColumnName, " not in ( '", StringUtils.join(fieldValueNotIn, "','"), "' )");
                }
                break;
            default:
                break;
        }
        return conditionSql;
    }


}
