package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Subject {

    private Integer id;
    private String name;
    private Integer duration;
    @JsonProperty("arrange_times")
    private List<ArrangeTime> arrangeTimes;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<ArrangeTime> getArrangeTimes() {
        return arrangeTimes;
    }

    public void setArrangeTimes(List<ArrangeTime> arrangeTimes) {
        this.arrangeTimes = arrangeTimes;
    }
}
