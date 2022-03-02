package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Monitor {

    private Integer id;
    @JsonProperty("arrange_id")
    private Integer arrangeId;
    @JsonProperty("invigilate_id")
    private Integer invigilateId;
    private Invigilate invigilate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Integer arrangeId) {
        this.arrangeId = arrangeId;
    }

    public Integer getInvigilateId() {
        return invigilateId;
    }

    public void setInvigilateId(Integer invigilateId) {
        this.invigilateId = invigilateId;
    }

    public Invigilate getInvigilate() {
        return invigilate;
    }

    public void setInvigilate(Invigilate invigilate) {
        this.invigilate = invigilate;
    }
}
