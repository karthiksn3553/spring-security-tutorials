package com.telusko.springSecApp.Config;
import com.telusko.springSecApp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/register","/login").permitAll()    //skip auth for register and login
                .anyRequest().authenticated());
        //http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  //a new session ID each time you send a request
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    // Authentication provider exists in the login form and whenever you enter the credentials
    // It authenticates it into an authenticated OBJECT
    // we can connect a database directly to the authentication provider itself
    @Bean
    //BOUNCER
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService); //I don't store user passwords in my head. Let me ask the File Clerk (UserDetailsService) to look up
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

//    YOU CAN SET MULTIPLE USERNAMES AND PASSWORDS INSTEAD GIVING IT IN APPLICATION PROPERTIES
//    @Bean
//    This specific bean overrides the auto-generated security password or app prop passwords from Spring Boot
//    public UserDetailsService UserDetailsService(){
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder() // It allows the application to read the plain text password securely in memory
//                .username("karthik")
//                .password("k@123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("keerti")
//                .password("ke@123")
//                .roles("ADMIN")
//                .build();                     // Compiles the configurations into a UserDetails object
//
//        InMemoryUserDetailsManager is a built-in class that implements UserDetailsService.

//        return new InMemoryUserDetailsManager(user1,user2);
//    }



}
