package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SignUp implements Serializable {

    private Integer id;
    private Integer grade;
    private Integer seat;
    private String admission;
    @JsonProperty("exam_room_id")
    private Integer examRoomId;
    @JsonProperty("exam_id")
    private Integer examId;
    @JsonProperty("examinee_id")
    private Integer examineeId;
    @JsonProperty("order_id")
    private String orderId;
    private Order order;
    private Exam exam;
    private Examinee examinee;
    @JsonProperty("exam_room")
    private ExamRoom examRoom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public Integer getExamRoomId() {
        return examRoomId;
    }

    public void setExamRoomId(Integer examRoomId) {
        this.examRoomId = examRoomId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(Integer examineeId) {
        this.examineeId = examineeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Examinee getExaminee() {
        return examinee;
    }

    public void setExaminee(Examinee examinee) {
        this.examinee = examinee;
    }

    public ExamRoom getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(ExamRoom examRoom) {
        this.examRoom = examRoom;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
