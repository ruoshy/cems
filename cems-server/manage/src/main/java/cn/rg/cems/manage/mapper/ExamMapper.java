package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Exam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamMapper {

    @Insert("<script>" +
            "INSERT INTO exam(start_date,end_date,`name`,subject_id" +
            "<if test='option!=null'>,`option`</if>" +
            "<if test='mark!=null'>,mark</if> " +
            ")" +
            "VALUES(#{startDate},#{endDate},#{name},#{subjectId}" +
            "<if test='option!=null'>,#{option}</if>" +
            "<if test='mark!=null'>,#{mark}</if>" +
            ")" +
            "</script>")
    Integer add(Exam exam);

    @Update("<script>" +
            "UPDATE exam " +
            "<set>" +
            "<if test='startDate!=null'>start_date=#{startDate},</if> " +
            "<if test='endDate!=null'>end_date=#{endDate},</if> " +
            "<if test='name!=null'>name=#{name},</if> " +
            "<if test='option!=null'>`option`=#{option},</if> " +
            "<if test='mark!=null'>mark=#{mark}</if> " +
            "</set> " +
            "WHERE id=#{id}" +
            "</script>")
    Integer update(Exam exam);

    @Select("<script>" +
            "SELECT * FROM exam " +
            "<where>" +
            "end_date > NOW()" +
            "<if test='subjectId!=null'>AND subject_id=#{subjectId}</if>" +
            "</where>" +
            "</script>")
    List<Exam> findAll(Exam exam);
}
