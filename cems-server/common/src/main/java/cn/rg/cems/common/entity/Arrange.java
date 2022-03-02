package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Arrange {

    private Integer id;
    @JsonProperty("exam_id")
    private Integer examId;
    @JsonProperty("exam_room_id")
    private Integer examRoomId;
    private List<Monitor> monitors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getExamRoomId() {
        return examRoomId;
    }

    public void setExamRoomId(Integer examRoomId) {
        this.examRoomId = examRoomId;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }
}
