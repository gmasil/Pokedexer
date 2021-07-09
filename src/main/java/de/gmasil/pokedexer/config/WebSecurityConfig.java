/**
 * Pokédexer
 * Copyright © 2021 Gmasil
 *
 * This file is part of Pokédexer.
 *
 * Pokédexer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pokédexer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pokédexer. If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.pokedexer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.gmasil.pokedexer.jpa.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/h2-console", "/h2-console/**");
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests() //
                .antMatchers("/admin", "/admin/**").hasAuthority("ADMIN") //
                .antMatchers("/h2-console", "/h2-console/**").permitAll() //
                .antMatchers("/setup").permitAll() //
                .antMatchers("/login", "/logout").permitAll() //
                .antMatchers("/error").permitAll() //
                .antMatchers("/public/**").permitAll() //
                .antMatchers("/webjars/**").permitAll() //
                .antMatchers("/api/progress/**").permitAll() //
                .anyRequest().authenticated();
        http.formLogin().loginPage("/login").failureUrl("/login?error") //
                .loginProcessingUrl("/login") //
                .usernameParameter("username") //
                .passwordParameter("password"); //
        http.logout() //
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
                .logoutSuccessUrl("/login?logout") //
                .deleteCookies("JSESSIONID", "remember-me");
        http.rememberMe() //
                .key("uniqueAndSecret") //
                .userDetailsService(userService) //
                .rememberMeParameter("remember-me");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
