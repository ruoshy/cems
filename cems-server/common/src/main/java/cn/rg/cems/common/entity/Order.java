package cn.rg.cems.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private String id;
    private Date date;
    private Float money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
