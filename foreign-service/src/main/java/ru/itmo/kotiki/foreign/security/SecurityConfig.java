package ru.itmo.kotiki.foreign.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itmo.kotiki.foreign.filter.KotikiAuthenticationFilter;
import ru.itmo.kotiki.foreign.filter.KotikiAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        KotikiAuthenticationFilter kotikiAuthenticationFilter = new KotikiAuthenticationFilter(authenticationManagerBean());
        kotikiAuthenticationFilter.setFilterProcessesUrl("/kotiki/login");

        KotikiAuthorizationFilter kotikiAuthorizationFilter = new KotikiAuthorizationFilter();
//
//        String[] user_matchers = new String[]{"/cats/*", "/owners/*", "/users/getbyusername/**"};
//        String[] admin_matchers = new String[]{"/users/**", "/owners/**", "/cats/**"};

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/kotiki/login/**", "/users/token/refresh/**").permitAll();

//        http.authorizeRequests().antMatchers(GET, user_matchers)
//                .hasAnyAuthority(AppUserRole.USER.toString(), AppUserRole.ADMIN.toString());
//        http.authorizeRequests().antMatchers(GET, admin_matchers)
//                .hasAnyAuthority(AppUserRole.ADMIN.toString());
//
//        http.authorizeRequests().antMatchers(POST, admin_matchers)
//                .hasAnyAuthority(AppUserRole.ADMIN.toString());
//
//        http.authorizeRequests().antMatchers(PUT, admin_matchers)
//                .hasAnyAuthority(AppUserRole.ADMIN.toString());
//
//        http.authorizeRequests().antMatchers(DELETE, admin_matchers)
//                .hasAnyAuthority(AppUserRole.ADMIN.toString());

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(kotikiAuthenticationFilter);
        http.addFilterBefore(kotikiAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
