package com.museo.siw.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import static com.museo.siw.spring.model.Credentials.ADMIN_ROLE;
//import static it.uniroma3.siw.spring.model.Credentials.DEFAULT_ROLE;

/**
 * The AuthConfiguration is a Spring Security Configuration.
 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
 */
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * The datasource is automatically injected into the AuthConfiguration (using its getters and setters)
     * and it is used to access the DB to get the Credentials to perform authentiation and authorization
     */
    @Autowired
    DataSource datasource;

    /**
     * This method provides the whole authentication and authorization configuration to use.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // authorization paragraph: qui definiamo chi può accedere a cosa
                .authorizeRequests()
                // tutti gli utenti (autenticati o non) possono accere alle pagine rimanenti 
                .anyRequest().permitAll()

        		// login paragraph: qui definiamo come è gestita l'autenticazione
        		// usiamo il protocollo formlogin 
        		.and().formLogin()
        		// la pagina di login si trova a /login
        		// NOTA: Spring gestisce il post di login automaticamente
        		.loginPage("/login")
        		// se il login ha successo, si viene rediretti al path /default
        		.defaultSuccessUrl("/default", true)
        		// se il login ha fallito, si viene rediretti al path /loginFail
        		.failureUrl("/loginFail")

        		// logout paragraph: qui definiamo il logout
        		.and().logout()
        		// il logout è attivato con una richiesta GET a "/logout"
        		.logoutUrl("/logout")
        		// in caso di successo, si viene reindirizzati alla /index page
        		.logoutSuccessUrl("/home")        
        		.invalidateHttpSession(true)
        		.clearAuthentication(true).permitAll();
    }

    /**
     * This method provides the SQL queries to get username and password.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                //use the autowired datasource to access the saved credentials
                .dataSource(this.datasource)
                //retrieve username and role
                .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    /**
     * This method defines a "passwordEncoder" Bean.
     * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}