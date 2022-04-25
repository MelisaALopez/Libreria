/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egg.Libreria;

import egg.Libreria.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Usr
 */
@Configuration
@EnableWebSecurity
//Para habilitar o deshabilitar el acceso a las URLs de acuerdo a los roles
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LibreriaSecurity extends WebSecurityConfigurerAdapter {
    //Autenticación y Autorización
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    //Método que configura la autenticación
    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    //Configuracion de peticiones http
    //Para evitar poner la contraseña cuando se ingresa a la página
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/*","/img/*","/js/*").permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .usernameParameter("mail")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .loginProcessingUrl("/logincheck")
                    .failureUrl("/login?error-error")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .and().csrf().disable();
    }
    
    
    
}
