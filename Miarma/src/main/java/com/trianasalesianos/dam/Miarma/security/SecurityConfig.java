package com.trianasalesianos.dam.Miarma.security;
import com.trianasalesianos.dam.Miarma.security.jwt.JwtAccesDeniedHandler;
import com.trianasalesianos.dam.Miarma.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccesDeniedHandler jwtAccesDeniedHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(jwtAccesDeniedHandler)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET,"/auth/me").permitAll()
                .antMatchers(HttpMethod.POST,"/post/").permitAll()
                .antMatchers(HttpMethod.PUT,"/post/{id}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/post/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/post/public").permitAll()
                .antMatchers(HttpMethod.GET,"/post/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/post/{nick}").permitAll()
                .antMatchers(HttpMethod.GET,"/post/me").permitAll()
                .antMatchers(HttpMethod.GET, "/profile/{id}").permitAll()
                .antMatchers(HttpMethod.PUT, "/profile/me").permitAll();

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
