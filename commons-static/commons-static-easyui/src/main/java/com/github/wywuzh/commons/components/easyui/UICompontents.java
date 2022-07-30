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
package com.github.wywuzh.commons.components.easyui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.github.wywuzh.commons.components.easyui.constant.UIConstants;
import com.github.wywuzh.commons.components.easyui.model.CheckboxTree;
import com.github.wywuzh.commons.components.easyui.model.Tree;
import com.github.wywuzh.commons.components.easyui.model.TreeGrid;
import com.github.wywuzh.commons.core.json.gson.GsonUtil;
import com.github.wywuzh.commons.pager.PaginationObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

/**
 * 类UICompontents.java的实现描述：UI组件工具类
 *
 * @author 伍章红 2015年11月12日 下午9:32:16
 * @version v1.0.0
 * @since JDK 1.7
 */
@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class UICompontents {
    private static final Logger logger = LoggerFactory.getLogger(UICompontents.class);

    /**
     * 将bean对象的字段序列化到Map中
     *
     * @param map
     * @param bean
     * @param clazz
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @author 伍章红 2015年11月14日 下午3:35:09
     */
    public static void serialField(Map<String, Object> map, Object bean, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            String name = fields[i].getName();
            Object value = fields[i].get(bean);
            if ("serialVersionUID".equals(name)) {
                continue;
            } else {
                map.put(name, value);
            }
        }
    }

    /**
     * 递归tree
     *
     * @param treeList
     * @param parentId
     * @return
     * @author 伍章红 2015年11月17日 下午4:00:17
     */
    public static List<Tree> recursionTree(List<Tree> treeList, String parentId) {
        List<Tree> nodeList = new ArrayList<Tree>();
        for (Tree tree : treeList) {
            String nodeId = tree.getId();
            String nodePid = tree.getPid();
            if (nodePid.equals(parentId)) {
                tree.setChildren(recursionTree(treeList, nodeId));
                nodeList.add(tree);
            }

        }
        return nodeList;
    }

    /**
     * 递归CheckboxTree
     *
     * @param treeList
     * @param parentId
     * @return
     * @author 伍章红 2015年11月17日 下午4:00:19
     */
    public static List<CheckboxTree> recursionCheckboxTree(List<CheckboxTree> treeList, String parentId) {
        List<CheckboxTree> nodeList = new ArrayList<CheckboxTree>();
        for (CheckboxTree checkboxTree : treeList) {
            String nodeId = checkboxTree.getId();
            String nodePid = checkboxTree.getPid();
            if (nodePid.equals(parentId)) {
                checkboxTree.setChildren(recursionCheckboxTree(treeList, nodeId));
                nodeList.add(checkboxTree);
            }

        }
        return nodeList;
    }

    /**
     * 将PaginationObject分页对象数据转换为Map对象数据
     *
     * @param paginationObject
     * @return
     * @author 伍章红 2015年11月12日 下午10:03:46
     */
    public static <R extends Serializable, P extends Serializable> Map<String, Object> transformDatagrid(PaginationObject<R, P> paginationObject) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result.put(UIConstants.DEFAULT_KEY_TOTAL, paginationObject.getRowCount());
            result.put(UIConstants.DEFAULT_KEY_ROWS, paginationObject.getResultList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put(UIConstants.DEFAULT_KEY_TOTAL, 0);
            result.put(UIConstants.DEFAULT_KEY_ROWS, new ArrayList());
        }
        return result;
    }

    /**
     * 将collection集合对象数据转换为Map对象数据
     *
     * @param collection
     * @return
     * @author 伍章红 2016年1月26日 下午2:37:02
     */
    public static Map<String, Object> transformDatagrid(Collection<? extends Serializable> collection) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result.put(UIConstants.DEFAULT_KEY_TOTAL, collection.size());
            result.put(UIConstants.DEFAULT_KEY_ROWS, collection);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put(UIConstants.DEFAULT_KEY_TOTAL, 0);
            result.put(UIConstants.DEFAULT_KEY_ROWS, new ArrayList());
        }
        return result;
    }

    /**
     * 将json格式的树转换为map
     *
     * @param jsonObject
     * @return
     * @author 伍章红 2015年11月17日 下午4:52:38
     */
    public static Map<String, Object> transformJsonTree(JsonObject jsonObject) {
        Map<String, Object> treeMap = new HashMap<String, Object>();
        for (Iterator<Entry<String, JsonElement>> iterator = jsonObject.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getKey();
            // 将attributes对象实体的信息加入到map中
            if ("attributes".equals(key)) {
                JsonElement attributesValue = entry.getValue();
                if (null != attributesValue && JsonNull.class != attributesValue.getClass()) {
                    JsonObject attribute = attributesValue.getAsJsonObject();
                    for (Iterator<Entry<String, JsonElement>> attributes = attribute.entrySet().iterator(); attributes.hasNext(); ) {
                        Entry<String, JsonElement> attributeEntry = attributes.next();
                        if (!"id".equals(attributeEntry.getKey())) {
                            treeMap.put(attributeEntry.getKey(), attributeEntry.getValue());
                        }
                    }
                }
            } else if ("children".equals(key)) {
                // 子节点
                JsonElement childrenValue = entry.getValue();
                List<Map<String, Object>> childrenMapList = new ArrayList<Map<String, Object>>();

                if (null != childrenValue && JsonNull.class != childrenValue.getClass()) {
                    JsonArray childrenArray = childrenValue.getAsJsonArray();
                    for (Iterator<JsonElement> childrenIterator = childrenArray.iterator(); childrenIterator.hasNext(); ) {
                        JsonObject childrenElement = childrenIterator.next().getAsJsonObject();

                        childrenMapList.add(transformJsonTree(childrenElement));
                    }
                }

                treeMap.put("children", childrenMapList);
            } else {
                treeMap.put(key, entry.getValue());
            }
        }
        return treeMap;
    }

    /**
     * 将Tree对象类型数据转换为Map对象数据
     *
     * @param tree
     * @return
     * @author 伍章红 2015年11月12日 下午10:01:22
     */
    public static Map<String, Object> transformTree(Tree<? extends Serializable> tree) {
        JsonObject jsonObject = GsonUtil.gson.toJsonTree(tree).getAsJsonObject();
        return transformJsonTree(jsonObject);
    }

    /**
     * 将Tree对象类型集合数据转换为Map对象集合数据
     *
     * @param treeList
     * @return
     * @author 伍章红 2015年11月12日 下午10:10:52
     */
    public static List<Map<String, Object>> transformTreeList(List<Tree> treeList) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Tree tree : treeList) {
            mapList.add(transformTree(tree));
        }
        return mapList;
    }

    /**
     * 将TreeGrid对象类型数据转换为Map对象数据
     *
     * @param treeGrid
     * @return
     * @author 伍章红 2015年11月12日 下午9:58:54
     */
    public static Map<String, Object> transformTreeGrid(TreeGrid<? extends Serializable> treeGrid) {
        JsonObject jsonObject = GsonUtil.gson.toJsonTree(treeGrid).getAsJsonObject();
        return transformJsonTree(jsonObject);
    }

    /**
     * 将TreeGrid对象类型集合数据转换为Map对象集合数据
     *
     * @param treeGridList
     * @return
     * @author 伍章红 2015年11月12日 下午10:08:21
     */
    public static List<Map<String, Object>> transformTreeGrid(List<TreeGrid> treeGridList) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (TreeGrid treeGrid : treeGridList) {
            mapList.add(transformTreeGrid(treeGrid));
        }
        return mapList;
    }

    /**
     * 将CheckboxTree类型数据转换为Map对象数据
     *
     * @param checkboxTree
     * @return
     * @author 伍章红 2015年11月12日 下午10:00:52
     */
    public static Map<String, Object> transformCheckboxTree(CheckboxTree<? extends Serializable> checkboxTree) {
        JsonObject jsonObject = GsonUtil.gson.toJsonTree(checkboxTree).getAsJsonObject();
        return transformJsonTree(jsonObject);
    }

    /**
     * 将CheckboxTree类型集合数据转换为Map对象集合数据
     *
     * @param checkboxTreeList
     * @return
     * @author 伍章红 2015年11月12日 下午10:07:26
     */
    public static List<Map<String, Object>> transformCheckboxTree(List<CheckboxTree> checkboxTreeList) {
        checkboxTreeList = recursionCheckboxTree(checkboxTreeList, "-1");

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (CheckboxTree checkboxTree : checkboxTreeList) {
            mapList.add(transformCheckboxTree(checkboxTree));
        }
        return mapList;
    }
}
