package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Invigilate;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InvigilateMapper {

    @Insert("INSERT INTO invigilate(`name`,account_id) " +
            "VALUES(#{name},#{accountId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addItem(Invigilate invigilate);

    @Select("SELECT invigilate.*,account.email,account.phone,account.`password`,account.`lock`" +
            "FROM account,invigilate WHERE account.id = invigilate.account_id")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "email", property = "account.email"),
            @Result(column = "lock", property = "account.lock"),
            @Result(column = "phone", property = "account.phone"),
            @Result(column = "account_id", property = "accountId")
    })
    List<Invigilate> findAll();

    @Update("UPDATE invigilate SET name=#{name} WHERE id=#{id}")
    Integer update(Invigilate invigilate);
}
