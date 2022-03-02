package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.ExamRoom;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ExamRoomMapper {

    @Insert("<script>" +
            "INSERT INTO exam_room(name,examinee_capacity,monitor_capacity" +
            "<if test='mark!=null'>,mark</if> " +
            ") VALUES(#{name},#{examineeCapacity},#{monitorCapacity}" +
            "<if test='mark!=null'>,#{mark}</if> " +
            ")" +
            "</script>")
    Integer add(ExamRoom examRoom);

    @Update("<script>" +
            "UPDATE exam_room " +
            "<set>" +
            "<if test='name!=null'>name=#{name},</if> " +
            "<if test='examineeCapacity!=null'>examinee_capacity=#{examineeCapacity},</if> " +
            "<if test='monitorCapacity!=null'>monitor_capacity=#{monitorCapacity},</if> " +
            "<if test='lock!=null'>`lock`=#{lock},</if> " +
            "<if test='mark!=null'>mark=#{mark}</if> " +
            "</set> " +
            " WHERE id=#{id}" +
            "</script>")
    Integer update(ExamRoom examRoom);

    @Select("SELECT * FROM exam_room")
    List<ExamRoom> findAll();

    @Select("SELECT * FROM exam_room WHERE id=#{id}")
    ExamRoom findById(Integer id);
}
