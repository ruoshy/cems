package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Arrange;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArrangeMapper {

    @Insert("INSERT INTO arrange(exam_id,exam_room_id) " +
            "VALUES(#{examId},#{examRoomId})")
    Integer add(Arrange arrange);

    @Delete("delete from arrange where id=#{id}")
    Integer delete(Arrange arrange);

    @Select("SELECT * FROM arrange WHERE exam_id=#{examId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "monitors", many = @Many(select = "cn.rg.cems.manage.mapper.MonitorMapper.findByArrangeId"))
    })
    List<Arrange> findByExamId(Arrange arrange);
}
