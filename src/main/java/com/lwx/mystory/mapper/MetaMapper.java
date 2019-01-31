package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Meta;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 22:22 2018/12/5
 **/
@Component
public interface MetaMapper {

    Meta getMetaByTypeAndName(@Param("type") String type, @Param("name") String name);

    /**
     * 友链
     */
    List<Meta> getMetasByType(@Param("type") String type);

    /**
     * 查询标签下的文章数量
     */
    int countWithSql(Integer mid);

    /**
     * 联合relationship进行查询，返回的是子类，子类属于父类！
     */
    List<Meta> getMetasBySql(@Param("type") String type);


    List<Meta> selectMetaListByConditions(@Param("type") String type,@Param("name") String name);

    void saveMeta(Meta meta);

    /**
     * 后台更新meta
     */
    void updateMeta(Meta meta);

    /**
     *
     */
    Meta getMetaById(@Param("id") Integer id);

    /**
     * 根据id删除meta
     */
    void delMetaById(@Param("id") Integer id);
}
