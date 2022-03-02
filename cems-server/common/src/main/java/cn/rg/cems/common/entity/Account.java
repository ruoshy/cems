package cn.rg.cems.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Account implements UserDetails, Serializable {

    private Integer id;
    private String email;
    private String password;
    private String phone;
    @JsonProperty("role_id")
    private Integer roleId;
    private Role role;
    private Boolean lock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.roleId = role.getId();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public String getUsername() {
        return null;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return "ROLE_admin".equals(role.getName()) || lock;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
