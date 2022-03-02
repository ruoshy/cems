package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.ArrangeTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArrangeTimeMapper {

    @Insert("<script>" +
            "INSERT INTO arrange_time(time,subject_id) VALUES" +
            "<foreach collection='arrangeTimes' item='item' separator=','>" +
            "(#{item.time},#{subjectId})" +
            "</foreach>" +
            "</script>")
    Integer add(List<ArrangeTime> arrangeTimes, Integer subjectId);

    @Update("UPDATE `arrange_time` " +
            "SET time=#{time} " +
            "WHERE id=#{id}")
    Integer update(ArrangeTime arrangeTime);

    @Select("SELECT * FROM arrange_time")
    List<ArrangeTime> findAll();

    @Select("SELECT * FROM arrange_time WHERE subject_id = #{id}")
    List<ArrangeTime> findBySubjectId(Integer id);

    @Delete("DELETE FROM arrange_time WHERE id=#{id}")
    Integer delete(ArrangeTime arrangeTime);
}
