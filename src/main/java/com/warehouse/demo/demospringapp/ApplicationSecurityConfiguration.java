package com.warehouse.demo.demospringapp;

import com.warehouse.demo.demospringapp.auth.CustomerDetailsService;
import com.warehouse.demo.demospringapp.auth.DemoAppAuthenticationSuccessHandler;
import com.warehouse.demo.demospringapp.auth.SupplierDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SupplierDetailsService supplierDetailsService;

    @Autowired
    private CustomerDetailsService customerDetailsService;
    
    @Bean
    public static AuthenticationSuccessHandler authSuccessHandler() {
        return new DemoAppAuthenticationSuccessHandler();
    }

    @Bean
    public DaoAuthenticationProvider supplierAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(supplierDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(11));
        provider.setAuthoritiesMapper(supplierAuthoritiesMapper());
        return provider;
    }

    @Bean
    public GrantedAuthoritiesMapper supplierAuthoritiesMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setDefaultAuthority("SUPPLIER");
        return authorityMapper;
    }

    @Bean
    public DaoAuthenticationProvider customerAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(11));
        provider.setAuthoritiesMapper(customerAuthoritiesMapper());
        return provider;
    }

    @Bean
    public GrantedAuthoritiesMapper customerAuthoritiesMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setDefaultAuthority("CUSTOMER");
        return authorityMapper;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(supplierAuthenticationProvider());
        auth.authenticationProvider(customerAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/materials/**").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/supplier/**").hasRole("SUPPLIER")
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                .anyRequest().authenticated()

            .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/", true)
                // .successHandler(authSuccessHandler())
                
            .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout-success").permitAll();                         
    }
}