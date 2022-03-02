package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrangeTime {

    private Integer id;
    private String time;
    @JsonProperty("subject_id")
    private Integer subjectId;
    private Subject subject;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
