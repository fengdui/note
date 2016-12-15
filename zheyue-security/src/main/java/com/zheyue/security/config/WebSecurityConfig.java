package com.zheyue.security.config;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author FD
 * @date 2016/11/18
 */

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置拦截规则
        //自定义accessDecisionManager访问控制器,并开启表达式语言
        http.authorizeRequests()
                .antMatchers("/**").authenticated();
        http.csrf().disable();

//        http.exceptionHandling().accessDeniedPage("/403");
        //自定义登录页面
        http.formLogin().loginPage("/login")
//                .failureUrl("/error2")
                .loginProcessingUrl("/login_check")
                .defaultSuccessUrl("/hello")
                .usernameParameter("username")
                .passwordParameter("password").permitAll();

        // 自定义注销
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login")
//                .invalidateHttpSession(true);

        // session管理
        http.sessionManagement().sessionFixation().changeSessionId()
                .maximumSessions(1).expiredUrl("/");

        // RemeberMe
        http.rememberMe().key("webmvc#FD637E6D9C0F1A5A67082AF56CE32485");

        //basic
//        http.httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("fd").password("fd").roles("fd");
    }

    public static void main(String[] args) {

    }
}
