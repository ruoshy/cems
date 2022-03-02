package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account(email,phone,`password`,role_id) " +
            "VALUES(#{email},#{phone},#{password},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addItem(Account account);

    @Update("<script>" +
            "UPDATE account" +
            "<set>" +
            "<if test='email!=null'>email=#{email},</if> " +
            "<if test='phone!=null'>phone=#{phone},</if> " +
            "<if test='password!=null'>password=#{password},</if>" +
            "<if test='lock!=null'>`lock`=#{lock},</if>" +
            "</set> " +
            "WHERE id=#{id}" +
            "</script>")
    Integer update(Account account);
}
