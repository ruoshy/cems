package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Monitor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MonitorMapper {

    @Insert("INSERT INTO monitor(arrange_id,invigilate_id) " +
            "VALUES(#{arrangeId},#{invigilateId})")
    Integer add(Monitor monitor);

    @Delete("DELETE FROM monitor WHERE id=#{id}")
    Integer delete(Monitor monitor);

    @Select("SELECT * FROM monitor WHERE arrange_id=#{arrangeId}")
    List<Monitor> findByArrangeId(Integer arrangeId);

}
