package cn.rg.cems.manage.mapper;

import cn.rg.cems.common.entity.Examinee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExamineeMapper {

    @Select("SELECT * FROM examinee WHERE id=#{id}")
    Examinee findById(Integer id);

    @Select("SELECT * FROM examinee WHERE account_id=#{accountId}")
    Examinee findByAccountId(Integer accountId);
}
