package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Exam {

    private Integer id;
    @JsonProperty("start_date")
    private Date startDate;
    @JsonProperty("end_date")
    private Date endDate;
    private String name;
    @JsonProperty("subject_id")
    private Integer subjectId;
    private Boolean option;
    private String mark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getOption() {
        return option;
    }

    public void setOption(Boolean option) {
        this.option = option;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
