package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamRoom {

    private Integer id;
    private String name;
    @JsonProperty("examinee_capacity")
    private Integer examineeCapacity;
    @JsonProperty("monitor_capacity")
    private Integer monitorCapacity;
    private String mark;
    private Boolean lock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExamineeCapacity() {
        return examineeCapacity;
    }

    public void setExamineeCapacity(Integer examineeCapacity) {
        this.examineeCapacity = examineeCapacity;
    }

    public Integer getMonitorCapacity() {
        return monitorCapacity;
    }

    public void setMonitorCapacity(Integer monitorCapacity) {
        this.monitorCapacity = monitorCapacity;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
}
