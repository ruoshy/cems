package cn.rg.cems.enter.mapper;

import cn.rg.cems.common.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO `order`(id,date,money) " +
            "VALUES(#{id},#{date},#{money})")
    Integer add(Order order);
}
