package nl.mahmoud.sarkout.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The class is used to implement basic spring security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    @Profile("dev")
    public WebSecurityConfigurerAdapter localDevelopmentSpringSecuritySetup() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/v3/api-docs", "/swagger-ui**/**").permitAll()
                        .antMatchers("/actuator/health").permitAll()
                        .antMatchers("/api/stocks", "/api/stocks/*/history", "/api/stocks/*").permitAll()
                        //do not allow any other calls
                        .anyRequest().denyAll().and()
                        //enable basic authentication
                        .httpBasic().and()
                        //disable csrf

                        //for h2 console
                        .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable();
            }
        };
    }

    @Bean
    @Profile("prod")
    public WebSecurityConfigurerAdapter productionSpringSecuritySetup() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                //Allow any calls to the Health endpoints.
                http.authorizeRequests()
                        .antMatchers("/actuator/health").permitAll()
                        .antMatchers("/api/stocks", "/api/stocks/*/history").permitAll()

                        //do not allow any other calls
                        .anyRequest().denyAll().and()
                        //enable basic authentication
                        .httpBasic().and()
                        //disable csrf
                        .csrf().disable();
            }
        };
    }
}