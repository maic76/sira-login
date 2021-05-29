package mx.lania.siralogin.security.config;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.appuser.UsuarioService;
import mx.lania.siralogin.security.filter.JWTAuthenticationFilter;
import mx.lania.siralogin.security.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))        //configuramos el filtro de logueo
                .addFilterAfter(new JWTAuthorizationFilter(authenticationManager()),JWTAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*","/images/*","/fonts/*","/scss/*").permitAll()
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .anyRequest()
                .authenticated();
               // .formLogin()
               // .loginPage("/login").permitAll()
               // .defaultSuccessUrl("/convocatorias",true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);

        return provider;
    }
}
