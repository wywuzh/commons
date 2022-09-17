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
package com.github.wywuzh.commons.dingtalk.api.contacts;

import com.github.wywuzh.commons.core.json.jackson.JsonMapper;
import com.github.wywuzh.commons.dingtalk.api.AbstractTest;
import com.github.wywuzh.commons.dingtalk.enums.Language;
import com.github.wywuzh.commons.dingtalk.request.contacts.UserCreateRequest;
import com.github.wywuzh.commons.dingtalk.request.contacts.UserListsimpleRequest;
import com.github.wywuzh.commons.dingtalk.request.contacts.UserUpdateRequest;
import com.github.wywuzh.commons.dingtalk.request.contacts.V2UserListRequest;
import com.github.wywuzh.commons.dingtalk.response.BaseResponse;
import com.github.wywuzh.commons.dingtalk.response.contacts.*;
import com.github.wywuzh.commons.dingtalk.response.contacts.userget.UserCreate;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
 * 类UserV2APITest的实现描述：用户管理2.0
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2021-01-30 21:31:14
 * @version v2.3.8
 * @since JDK 1.8
 */
@Slf4j
public class UserV2APITest extends AbstractTest {

//    public static void main(String[] args) {
//        String result = "{\"errcode\":0,\"errmsg\":\"ok\",\"result\":{\"active\":true,\"admin\":true,\"avatar\":\"https:\\/\\/static-legacy.dingtalk.com\\/media\\/lADPDgQ9rgLt7arNCfbNCfY_2550_2550.jpg\",\"boss\":false,\"dept_id_list\":[1],\"dept_order_list\":[{\"dept_id\":1,\"order\":176305862627517512}],\"email\":\"\",\"hide_mobile\":false,\"leader_in_dept\":[{\"dept_id\":1,\"leader\":false}],\"mobile\":\"14706660503\",\"name\":\"伍章红\",\"real_authed\":true,\"role_list\":[{\"group_name\":\"默认\",\"id\":1836390562,\"name\":\"主管理员\"}],\"senior\":false,\"state_code\":\"86\",\"unionid\":\"VKxwWW2JCj2V3oBOcwiiDLAiEiE\",\"userid\":\"manager8283\"},\"request_id\":\"wicpx8ls1gp3\"}";
//        ContactsResponse<UserGet> response = GsonUtil.parse(result, new TypeToken<ContactsResponse<UserGet>>() {
//        }.getType());
//        System.out.println(response);
//
////        ContactsResponse<UserGet> userGet = new ContactsResponse<>();
////        Type type = userGet.getClass().getGenericSuperclass();
////        //将type强转成Parameterized
////        ParameterizedType pt = (ParameterizedType) type;
////        // 得到父类(参数化类型)中的泛型(实际类型参数)的实际类型。
////        // getActualTypeArguments()返回一个Type数组，之所以返回Type数组,是因为一个类上有可能出现多个泛型，比如:Map<Integer,String>
////        Type[] types = pt.getActualTypeArguments();
////        Class<?>[] parameterClasses = new Class[types.length];
////        for (int i = 0; i < types.length; i++) {
////            parameterClasses[i] = (Class<?>) types[i];
////        }
////        ContactsResponse<UserGet> response = JsonMapper.buildNormalMapper().fromJson(result, ContactsResponse.class, parameterClasses);
//
////        Method method = MethodUtils.getAccessibleMethod(UserV2API.class, "get", String.class);
////        Class<?> clazz = method.getReturnType();
////        Type returnType = method.getGenericReturnType();
////        Type[] types = ((ParameterizedType) returnType).getActualTypeArguments();// 泛型类型列表
////        Class<?>[] classes = new Class[types.length];
////        for (int i = 0; i < types.length; i++) {
////            classes[i] = (Class<?>) types[i];
////        }
////        ContactsResponse<UserGet> response = JsonMapper.buildNonNullMapper().fromJson(result, clazz, classes);
//    }

  @Test
  public void create() {
    UserV2API userV2API = new UserV2API(apiConfig);

    // 创建用户 userid=103559512720455311
    UserCreateRequest request = new UserCreateRequest();
    request.setMobile("18576689629");
    request.setName("伍章红");
    request.setDeptIdList("457359137");
    ContactsResponse<UserCreate> response = userV2API.create(request);
    log.info("创建用户：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
  }

  @Test
  public void update() {
    UserV2API userV2API = new UserV2API(apiConfig);

    // 创建用户 userid=103559512720455311
    UserUpdateRequest request = new UserUpdateRequest();
    request.setUserid("103559512720455311");
    request.setMobile("18576689629");
    request.setName("伍章红");
    request.setLanguage("zh_CN");
    request.setDeptIdList("457359137");
    BaseResponse response = userV2API.update(request);
    log.info("更新用户信息：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
  }

  @Test
  public void get() {
    UserV2API userV2API = new UserV2API(apiConfig);
    // 根据userid获取用户详情
    String userid = "manager8283"; // userid=manager8283
    ContactsResponse<UserGet> get = userV2API.get(userid, Language.zh_CN);
    log.info("根据userid获取用户详情：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(get));
  }

  @Test
  public void listSimple() {
    UserV2API userV2API = new UserV2API(apiConfig);
    UserListsimpleRequest request = new UserListsimpleRequest();
    // 部门ID，根部门ID为1
    request.setDeptId(1L);
    // 分页查询的游标，最开始传0
    request.setCursor(0L);
    request.setSize(20L);

    ContactsResponse<PageResult<ListUserSimple>> response = userV2API.listSimple(request);
    log.info("获取部门用户基础信息：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
  }

  @Test
  public void listId() {
    UserV2API userV2API = new UserV2API(apiConfig);
    Long deptId = 1L;
    ContactsResponse<ListUserByDept> listId = userV2API.listId(deptId);
    log.info("获取部门用户userid列表：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(listId));
  }

  @Test
  public void v2UserList() {
    UserV2API userV2API = new UserV2API(apiConfig);
    V2UserListRequest request = new V2UserListRequest();
    // 部门ID，根部门ID为1
    request.setDeptId(1L);
    // 分页查询的游标，最开始传0
    request.setCursor(0L);
    request.setSize(20L);

    ContactsResponse<PageResult<ListUserSimple>> response = userV2API.v2UserList(request);
    log.info("获取部门用户详情：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(response));
  }

  @Test
  public void getByMobile() {
    UserV2API userV2API = new UserV2API(apiConfig);

    // 根据手机号获取userid
    String mobile = "14706660503"; // userid=manager8283
    ContactsResponse<UserGetByMobile> getByMobile = userV2API.getByMobile(mobile);
    log.info("根据手机号获取用户信息：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(getByMobile));
  }

  @Test
  public void getByUnionID() {
    UserV2API userV2API = new UserV2API(apiConfig);

    // 根据unionid获取用户userid
    String unionID = "VKxwWW2JCj2V3oBOcwiiDLAiEiE";
    ContactsResponse<UserGetByUnionid> getByUnionId = userV2API.getByUnionid(unionID);
    log.info("根据unionid获取用户userid：{}", JsonMapper.DEFAULT_JSON_MAPPER.toJsonFormat(getByUnionId));
  }

}
