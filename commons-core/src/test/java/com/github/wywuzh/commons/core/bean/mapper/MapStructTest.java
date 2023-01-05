/*
 * Copyright 2015-2023 the original author or authors.
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
package com.github.wywuzh.commons.core.bean.mapper;

import com.github.wywuzh.commons.core.bean.mapper.entity.UserVo;
import com.github.wywuzh.commons.core.json.jackson.JsonMapper;
import com.github.wywuzh.commons.core.poi.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类MapStructTest的实现描述：使用Mapstruct来进行PO与VO之间的映射
 * 
 * <pre>
 * 参考网址：
 * 1. https://blog.csdn.net/u014175005/article/details/72792839
 * 2. https://blog.csdn.net/paincupid/article/details/71247255
 * 3. https://www.jianshu.com/p/3f20ca1a93b0
 * 4. https://blog.csdn.net/chenshun123/article/details/83445438
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-03-01 21:32:22
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class MapStructTest {
    private final Logger logger = LoggerFactory.getLogger(MapStructTest.class);

    // 单个对象映射
    @Test
    public void UserToUserVo() {
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("那家伙！！");
        user.setEmail("wywuzh@163.com");
        user.setMobile("18500000000");
        user.setSex("男");
        user.setBirthdate(new Date());

        UserVo userVo = BeanMapperFactory.MAPPER_FACTORY.UserToUserVo(user);
        log.info("转换结果：{}", userVo);
    }

    // 对象集合映射
    @Test
    public void UserToUserVos() {
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("那家伙！！");
        user.setEmail("wywuzh@163.com");
        user.setMobile("18500000000");
        user.setSex("男");
        user.setBirthdate(new Date());
        List<User> list = new ArrayList<>();
        list.add(user);

        List<UserVo> userVoList = BeanMapperFactory.MAPPER_FACTORY.UserToUserVos(list);
        log.info("转换结果：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJson(userVoList));
    }

    @Test
    public void Inverse() {
        User user = new User();
        user.setUsername("wywuzh");
        user.setNick("那家伙！！");
        user.setEmail("wywuzh@163.com");
        user.setMobile("18500000000");
        user.setSex("男");
        user.setBirthdate(new Date());

        UserVo userVo = BeanMapperFactory.MAPPER_FACTORY.UserToUserVo(user);
        log.info("转换结果：{}", userVo);

        // Inverse 反转
        userVo.setBirthdateFormat("2020-03-01 01:01:01");
        User inverse = BeanMapperFactory.MAPPER_FACTORY.UserVoToUser(userVo);
        log.info("反转结果：{}", inverse);
    }

    @Mapper
    public static interface BeanMapperFactory {
        BeanMapperFactory MAPPER_FACTORY = Mappers.getMapper(BeanMapperFactory.class);

        @Mappings({
                @Mapping(source = "nick", target = "nickname"), // //属性名不一致映射
                @Mapping(target = "birthdateFormat", expression = "java(com.github.wywuzh.commons.core.util.DateUtils.format(user.getBirthdate(), com.github.wywuzh.commons.core.util.DateUtils.PATTERN_DATE_TIME))")
        })
        public UserVo UserToUserVo(User user);

        public List<UserVo> UserToUserVos(List<User> list);

        @Mappings({
                @Mapping(source = "nickname", target = "nick") // //属性名不一致映射
        })
        @InheritInverseConfiguration // Inverse反转
        public User UserVoToUser(UserVo userVo);
    }
}
