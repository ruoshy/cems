package cn.rg.cems.common.config;

import cn.rg.cems.common.model.MenuItem;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    private List<MenuItem> adminMenu;

    private List<MenuItem> staffMenu;

    public List<MenuItem> getAdminMenu() {
        return adminMenu;
    }

    public void setAdminMenu(List<MenuItem> adminMenu) {
        this.adminMenu = adminMenu;
    }

    public List<MenuItem> getStaffMenu() {
        return staffMenu;
    }

    public void setStaffMenu(List<MenuItem> staffMenu) {
        this.staffMenu = staffMenu;
    }
}
