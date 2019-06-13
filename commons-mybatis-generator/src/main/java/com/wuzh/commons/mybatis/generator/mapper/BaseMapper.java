package com.wuzh.commons.mybatis.generator.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 类BaseMapperPlugin的实现描述：BaseMapper基类插件
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2019/1/26 9:35
 * @version v2.1.1
 * @since JDK 1.8
 */
public interface BaseMapper<T extends Serializable, PK extends Serializable> {

    /**
     * 新增数据
     *
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 选择新增数据
     *
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 批量新增数据
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<T> list);

    /**
     * 修改数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 选择修改数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据主键id删除数据
     *
     * @param id 主键id
     * @return
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 根据主键查询数据
     *
     * @param id 主键id
     * @return
     */
    T selectByPrimaryKey(PK id);

    /**
     * 根据条件查询数据列表总数
     *
     * @param searchMap 查询参数
     * @return
     */
    Long selectTotalByParams(@Param("map") java.util.Map<String, Object> searchMap);

    /**
     * 根据条件查询数据列表，支持排序
     *
     * @param searchMap 查询参数
     * @return
     */
    java.util.List<T> selectListByParams(@Param("map") java.util.Map<String, Object> searchMap);

    /**
     * 根据条件查询数据列表（分页），支持排序
     *
     * @param searchMap 查询参数
     * @param offset    查询数据开始行
     * @param pageSize  每页查询数据量
     * @return
     */
    java.util.List<T> selectPagerByParams(@Param("map") java.util.Map<String, Object> searchMap, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
