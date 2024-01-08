

package com.xpresspayment.takehometest.middleware.security.configs;

import com.xpresspayment.takehometest.middleware.security.services.RestAuthenticationEntryPoint;
import com.xpresspayment.takehometest.middleware.security.services.i.IUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class WebSecurityConfig  {

    private final IUserService userService;
    private final AuthorizationFilter authorizationFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;


    public WebSecurityConfig(IUserService userService,
                             AuthorizationFilter authorizationFilter,
                             RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.userService = userService;
        this.authorizationFilter = authorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authZ) -> authZ
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//    }


//    @Override
//    public void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .cors().disable()
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
//
//        httpSecurity.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        httpSecurity.authorizeRequests()
//                .antMatchers(
//                        HttpMethod.POST,
//                        "/v1/sessions/sign-up",
//                        "/v1/sessions/beta-notify",
//                        "/v1/sessions/login",
//                        "/v1/account/activate/*",
//                        "/v1/account/verify/phone/*",
//                        "/v1/account/verify/resend/phone",
//                        "/v1/account/verify/resend/email",
//                        "/v1/account/settings/password/reset-token",
//                        "/v1/account/settings/password/reset",
//                        "/v1/onboarding/investor/interest",
//                        "/authenticate",
//                        "/swagger-resources/**",
//                        "/swagger-ui/**",
//                        "/swagger-ui.html",
//                        "/v3/api-docs",
//                        "/webjars/**",
//                        "/v1/virtual-account/event/**",
//                        "/v1/onboarding/business/beta"
//                ).permitAll();
//        httpSecurity.authorizeRequests()
//                .antMatchers(  HttpMethod.GET,
//                        "/v1/accounts/invite/fetch/**",
//                        "/v1/onboarding/business/beta/**").permitAll();
//    }
//
////    @Override
////    public void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userService);
////    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Configuration
    @EnableWebMvc
    public static class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        }
    }


}
