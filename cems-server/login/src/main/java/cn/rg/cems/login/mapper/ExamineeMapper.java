package cn.rg.cems.login.mapper;

import cn.rg.cems.common.entity.Examinee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamineeMapper {

    @Insert("INSERT INTO examinee(`name`,account_id,identity_type,identity_card) " +
            "VALUES(#{name},#{accountId},#{identityType},#{identityCard})")
    Integer add(Examinee examinee);
}
