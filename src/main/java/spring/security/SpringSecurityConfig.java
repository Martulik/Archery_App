package spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import spring.security.jwt.JwtSecurityConfigurer;
import spring.security.jwt.JwtTokenProvider;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //не нужно создавать сессию тк храним пользователя по токену
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/archery/auth/signIn").permitAll()
//                .antMatchers(HttpMethod.GET,"/company/goods").permitAll()
//                .antMatchers(HttpMethod.GET,"/company/goods/byId={id}").permitAll()
//                .antMatchers(HttpMethod.GET,"/company/goods/byName={name}").permitAll()
//                .antMatchers(HttpMethod.POST,"/company/goods/createGood").hasAnyRole("ADMIN")

                .anyRequest().authenticated()
                .and()

                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
