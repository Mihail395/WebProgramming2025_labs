package mk.ukim.finki.wp.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class WebSecurityConfig {
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/",
                                "/dishes",
                                "/login",
                                "/chefs",
                                "/chefs/**",
                                "/chefs/view-details",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        .requestMatchers("/dishes/add-to-chef").authenticated()

                        .requestMatchers(
                                "/dishes/add",
                                "/dishes/edit/**",
                                "/dishes/delete/**",
                                "/dishes/add-to-chef",
                                "/chefs/add",
                                "/chefs/edit/**",
                                "/chefs/delete/**"
                        ).hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .defaultSuccessUrl("/dishes", true)
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/dishes")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1 = User.builder()
                .username("mihail.dimitrovski")
                .password(passwordEncoder.encode("md"))
                .roles("USER")
                .build();


        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, user1);
    }
}
