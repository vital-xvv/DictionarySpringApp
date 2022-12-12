package com.spring.dictionary.security;

import com.spring.dictionary.filter.AuthenticationFilter;
import com.spring.dictionary.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().and().
                csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/token/refresh").permitAll()
                .antMatchers( "/swagger-ui/**",
                        "/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**" ,
                        "/swagger.json").permitAll()
                .antMatchers(HttpMethod.POST,"/api/user/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/api/user/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/api/dictionary/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/dictionary/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
         http.authorizeRequests().anyRequest().authenticated().and()
                .addFilter(new AuthenticationFilter(authenticationManagerBean()))
                .addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

       //http.headers().contentSecurityPolicy("script-src 'sef'");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
