package cn.hwali.hr.config;

import cn.hwali.hr.model.Hr;
import cn.hwali.hr.model.RespBean;
import cn.hwali.hr.service.HrService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lihua
 * @date 2021/05/06 12:32
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    HrService hrService;
    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;
    @Autowired
    VerificationCodeFilter verificationCodeFilter;
    @Autowired
    DataSource dataSource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

/*    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }*/

    /**
     * 全局 ProviderManager 的修改在 WebSecurityConfigurerAdapter#configure(AuthenticationManagerBuilder) 类中，
     * 这里配置的 AuthenticationManagerBuilder 最终用来生成全局的 ProviderManager
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(hrService);
        //用以下代码 报 java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(hrService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
//                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                })
                .and().formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Hr hr = (Hr) auth.getPrincipal();
                        hr.setPassword(null);
                        RespBean ok = RespBean.ok("登录成功!", hr);
                        String s = new ObjectMapper().writeValueAsString(ok);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBean error = RespBean.error(e.getMessage());
                        if (e instanceof LockedException) {
                            error.setMsg("账号被锁定,请联系管理员!");
                        } else if (e instanceof CredentialsExpiredException) {
                            error.setMsg("密码过期,请联系管理员!");
                        } else if (e instanceof AccountExpiredException) {
                            error.setMsg("账号过期,请联系管理员!");
                        } else if (e instanceof DisabledException) {
                            error.setMsg("账号被禁用,请联系管理员!");
                        } else if (e instanceof BadCredentialsException) {
                            error.setMsg("密码输入错误,请重新输入!");
//                            error.setMsg("用户名或密码输入错误,请重新输入!");
                        }
                        out.write(new ObjectMapper().writeValueAsString(error));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepository())
                .tokenValiditySeconds(7 * 3600)
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功!")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                //没有认证就访问数据，直接重定向到登录页面就行了，但是在前后端分离中
                //这个逻辑明显是有问题的，如果用户没有登录就访问一个需要认证后才能访问的
                //页面，这个时候应该给用户一个尚未登录的提示，前端收到后自行跳转
                //没有认证时,在这里处理结果,不要重定向
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    //有commence()login页面就不需要了
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(401);
                        PrintWriter out = resp.getWriter();
                        RespBean respBean = RespBean.error("访问失败!");
                        if (e instanceof InsufficientAuthenticationException) {
                            respBean.setMsg("请求失败,请联系管理员!");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                });
    /*    http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
            HttpServletResponse resp = event.getResponse();
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(401);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(RespBean.error("您已在另一台设备登录，本次登录已下线!")));
            out.flush();
            out.close();
        }), ConcurrentSessionFilter.class);*/
    }
}
