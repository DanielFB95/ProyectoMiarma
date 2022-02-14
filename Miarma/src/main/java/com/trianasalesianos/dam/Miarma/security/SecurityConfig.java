package com.trianasalesianos.dam.Miarma.security;

import com.salesianostriana.dam.RealEstate.security.jwt.JwtAccesDeniedHandler;
import com.salesianostriana.dam.RealEstate.security.jwt.JwtAuthorizationFilter;
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
                    .antMatchers(HttpMethod.POST, "/auth/login").anonymous()
                    .antMatchers(HttpMethod.POST, "/auth/register/user").permitAll()
                    .antMatchers(HttpMethod.POST, "/auth/register/admin").anonymous()
                    .antMatchers(HttpMethod.POST, "/auth/register/gestor").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/propietario/").authenticated()
                    .antMatchers(HttpMethod.GET, "propietario/{id}").hasAnyRole("ADMIN","PROPIETARIO")
                    .antMatchers(HttpMethod.DELETE, "propietario/{id}").hasAnyRole("ADMIN","PROPIETARIO")
                    .antMatchers(HttpMethod.GET, "/vivienda/").hasRole("PROPIETARIO")
                    .antMatchers(HttpMethod.POST,"/vivienda/").hasRole("PROPIETARIO")
                    .antMatchers(HttpMethod.GET,"/vivienda/{id}").authenticated()
                    .antMatchers(HttpMethod.PUT, "/vivienda/{id}").hasAnyRole("ADMIN","PROPIETARIO")
                    .antMatchers(HttpMethod.DELETE, "/vivienda/{id}").hasAnyRole("ADMIN","PROPIETARIO")
                    .antMatchers(HttpMethod.POST,"/vivienda/{id}/meinteresa").hasRole("PROPIETARIO")
                    .antMatchers(HttpMethod.DELETE, "/vivienda/{id}/meinteresa/{id2}").hasAnyRole("ADMIN","PROPIETARIO")
                    .antMatchers(HttpMethod.POST, "/inmobiliaria/").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/inmobiliaria/").authenticated()
                    .antMatchers(HttpMethod.GET, "/inmobiliaria/{id}").authenticated()
                    .antMatchers(HttpMethod.DELETE,"/inmobiliaria/{id}").hasRole("ADMIN");

                //Añadir todas las peticiones dependiendo del rol que pueda acceder a la petición

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
