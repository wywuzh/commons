/*
 * Copyright 2015-2020 the original author or authors.
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
package com.wuzh.commons.components.okadmin;

import com.wuzh.commons.core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类UIComponent的实现描述：组件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-05-05 16:03:01
 * @version v2.2.6
 * @since JDK 1.8
 */
public class UIComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(UIComponent.class);

    public static List<Map<String, Object>> transformTree(List<Tree> treeList)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>(treeList.size());
        for (Tree tree : treeList) {
            try {
                Map<String, Object> map = BeanUtils.convertBeanToMap(tree);
                map.put("parentId", tree.getParentId());
                resultMap.add(map);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw e;
            }
        }
        return resultMap;
    }

}