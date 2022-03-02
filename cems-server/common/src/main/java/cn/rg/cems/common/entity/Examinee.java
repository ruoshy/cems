package cn.rg.cems.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Examinee {

    private Integer id;
    private String name;
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("identity_type")
    private String identityType;
    @JsonProperty("identity_card")
    private String identityCard;
    private Account account;

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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
