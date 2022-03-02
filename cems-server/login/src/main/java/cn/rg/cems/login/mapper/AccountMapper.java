package cn.rg.cems.login.mapper;

import cn.rg.cems.common.entity.Account;
import cn.rg.cems.common.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM account WHERE email = #{email}")
    Account findByEmail(String email);

    @Select("SELECT * FROM account WHERE phone = #{phone}")
    Account findByPhone(String phone);

    @Select("SELECT role.* FROM role,account WHERE role.id  = account.role_id AND account.id = #{id}")
    Role findRolesById(Integer id);

    @Insert("INSERT INTO account(email,phone,`password`,role_id,`lock`) " +
            "VALUES(#{email},#{phone},#{password},#{roleId},#{lock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addItem(Account account);

    @Update("<script>" +
            "UPDATE account" +
            "<set>" +
            "<if test='email!=null'>email=#{email},</if> " +
            "<if test='phone!=null'>phone=#{phone},</if> " +
            "<if test='password!=null'>password=#{password},</if>" +
            "</set> " +
            "WHERE id=#{id}" +
            "</script>")
    Integer update(Account account);
}
