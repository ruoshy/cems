package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubjectMapper {

    @Insert("INSERT INTO `subject`(name,duration) VALUES(#{name},#{duration})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer add(Subject subject);

    @Update("<script>" +
            "UPDATE `subject` " +
            "<set>" +
            "<if test='name!=null'>name=#{name},</if> " +
            "<if test='duration!=null'>duration=#{duration},</if> " +
            "</set> " +
            "WHERE id=#{id}" +
            "</script>")
    Integer update(Subject subject);

    @Select("<script>" +
            "SELECT * FROM `subject` " +
            "<where>" +
            "<if test='name!=null'>name LIKE CONCAT('%',#{name},'%')</if>" +
            "</where>" +
            "</script>")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "arrangeTimes", many = @Many(select = "cn.rg.cems.manage.mapper.ArrangeTimeMapper.findBySubjectId"))
    })
    List<Subject> findAll(Subject subject);
}
