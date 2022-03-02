package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Staff;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StaffMapper {

    @Insert("INSERT INTO staff(`name`,account_id) " +
            "VALUES(#{name},#{accountId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addItem(Staff staff);

    @Select("SELECT staff.*,account.email,account.phone,account.`password`,account.`lock`" +
            "FROM account,staff WHERE account.id = staff.account_id")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "email", property = "account.email"),
            @Result(column = "lock", property = "account.lock"),
            @Result(column = "phone", property = "account.phone"),
            @Result(column = "account_id", property = "accountId")
    })
    List<Staff> findAll();

    @Update("UPDATE staff SET name=#{name} WHERE id=#{id}")
    Integer update(Staff staff);
}
