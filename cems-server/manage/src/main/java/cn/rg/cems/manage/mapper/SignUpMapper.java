package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.SignUp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignUpMapper {

    @Select("SELECT * FROM sign_up WHERE exam_id=#{examId}")
    @Results({
            @Result(column = "examinee_id", property = "examinee", one = @One(select = "cn.rg.cems.manage.mapper.ExamineeMapper.findById")),
            @Result(column = "exam_room_id", property = "examRoom", one = @One(select = "cn.rg.cems.manage.mapper.ExamRoomMapper.findById"))
    })
    List<SignUp> findByExamId(SignUp signUp);

    @Select("SELECT * FROM sign_up")
    List<SignUp> findAll();
}
