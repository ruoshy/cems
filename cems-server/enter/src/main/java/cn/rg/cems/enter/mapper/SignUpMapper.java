package cn.rg.cems.enter.mapper;

import cn.rg.cems.common.entity.ArrangeTime;
import cn.rg.cems.common.entity.SignUp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignUpMapper {

    @Insert("INSERT INTO sign_up(exam_id,examinee_id,order_id) " +
            "VALUES(#{examId},#{examineeId},#{orderId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer add(SignUp signUp);

    @Update("<script>" +
            "UPDATE `sign_up` " +
            "<set>" +
            "<if test='grade!=null'>grade=#{grade},</if> " +
            "<if test='seat!=null'>seat=#{seat},</if> " +
            "<if test='admission!=null'>admission=#{admission},</if> " +
            "<if test='examRoomId!=null'>exam_room_id=#{examRoomId},</if> " +
            "<if test='examId!=null'>exam_id=#{examId},</if> " +
            "<if test='examineeId!=null'>examinee_id=#{examineeId},</if> " +
            "<if test='orderId!=null'>order_id=#{orderId},</if> " +
            "</set>" +
            " WHERE id=#{id}" +
            "</script>")
    Integer update(SignUp signUp);

    @Select("SELECT * FROM sign_up WHERE examinee_id = #{id}")
    List<SignUp> findByExamineeId(Integer id);

    @Select("SELECT * FROM sign_up")
    List<SignUp> findAll(SignUp signUp);
}
