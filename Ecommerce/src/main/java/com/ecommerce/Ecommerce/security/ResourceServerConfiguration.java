
package com.ecommerce.Ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.PutMapping;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/userSave").anonymous()
                .antMatchers("/custSave").anonymous()
                .antMatchers("/sellSave").anonymous()
                .antMatchers("/{user_id}/addressSave").hasAnyRole("SELLER","CUSTOMER","ADMIN")
                .antMatchers("/confirmSave").hasAnyRole("SELLER","CUSTOMER")
                .antMatchers("/forgot-password").permitAll()
                .antMatchers("/reset-password").permitAll()
                .antMatchers("/admin/category").hasAnyRole("ADMIN")
                .antMatchers("/category/{parentCategory}").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/category/list").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/admin/listCustomers").hasAnyRole("ADMIN")
                .antMatchers("/admin/listSellers").hasAnyRole("ADMIN")
                .antMatchers("/seller/productSave/{category_id}").hasAnyRole("SELLER")
                .antMatchers("/customer/productReviewSave/{product_id}").hasAnyRole("CUSTOMER")
                .antMatchers("/seller/home").hasAnyRole("SELLER")
                .antMatchers("/productSave/variation/{product_id}").hasAnyRole("SELLER")
                .antMatchers("customer/addToCart/{productVariation_id}").hasAnyRole("CUSTOMER")
                .antMatchers("customer/order/{cart_id}").hasAnyRole("CUSTOMER")
                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/customer/home").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/showAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/addCustomerAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/updateCustomerProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/updateCustomerAddress/{addressId}").hasAnyRole("CUSTOMER")
                .antMatchers("/deleteCustomerAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/updateCustomerPassword").hasAnyRole("CUSTOMER")
                .antMatchers("/seller/showData/").hasAnyRole("SELLER")
                .antMatchers("/updateSellerProfile/").hasAnyRole("SELLER")
                .antMatchers("/updateSellerAddress/{addressId}").hasAnyRole("SELLER")
                .antMatchers("/updateSellerPassword/").hasAnyRole("SELLER")
                .antMatchers("/customers").hasAnyRole("ADMIN")
                .antMatchers("/sellers").hasAnyRole("ADMIN")
                .antMatchers("/doLogout").hasAnyRole("ADMIN","SELLER","CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }
}

