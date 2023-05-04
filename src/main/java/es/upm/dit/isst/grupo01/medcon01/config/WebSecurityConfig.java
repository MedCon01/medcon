package es.upm.dit.isst.grupo01.medcon01.config; 
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

  /*   // Configuración de autenticación para que se use la bbdd
    @Autowired
    DataSource ds;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(ds)
        // Para usar data.sql y schema.sql
        //    .usersByUsernameQuery("select username, password, enabled from users where username=?")
        //    .authoritiesByUsernameQuery("select user, authority from authorities where username=?");
        // Para usar citas_api
            .usersByUsernameQuery("select dni, password, enabled from medico where dni=?")
            .authoritiesByUsernameQuery("select dni, role from medico where dni=?");

    }
    // Permiso de acceso a la consola h2
    @Override
    public void configure(WebSecurity web) throws Exception{
        web
            .ignoring()
            .antMatchers("h2_console/**"); // Comprobar h2 a través del interfaz web
    }
    */ 
    // Configuración de control de accesos
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http 
            .authorizeRequests() // Define quien puede acceder a los recursos
                .antMatchers("/css/**", "/images/**","/index","/","/layouts","/error","/kiosko/**","/sala_espera/**").permitAll()
                .antMatchers("/medico/**","/aplicaciones_externas/**").hasAnyRole("MEDICO")
                .antMatchers("/admin").hasAnyRole("ADMIN")
                //.anyRequest().authenticated()
        .and() 
            .formLogin()
                .loginPage("/login_medico.html").permitAll()
                .loginProcessingUrl("/login_medico")
                .defaultSuccessUrl("/iniciomedico")
                .failureUrl("/login_medico.html?error=true")
        .and()
            .logout().permitAll()
        .and()
            .httpBasic();
    }   
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
            .withUser("medico").password("{noop}medico").roles("MEDICO");
    }
    /*
     * private JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> usersByUsernameQuery(String string) {
        return null;
    }
     */
    
}
