package cn.rg.cems.login.config;

import cn.rg.cems.common.handle.*;
import cn.rg.cems.login.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @Resource
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;
    @Resource
    private LogoutHandlerImpl logoutHandler;
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Resource
    private AccountService accountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers("/test/**").hasAnyRole("admin")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .usernameParameter("u").passwordParameter("p")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .addLogoutHandler(logoutHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
