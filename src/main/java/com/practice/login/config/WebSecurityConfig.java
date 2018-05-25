package com.practice.login.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.practice.login.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		// 로그인이 필요하지 않은 페이지들
		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
		
		// userInfo 페지이는 ROLE_USER와 ROLE_ADMIN 등 모든 권한 접근 가능
		// 로그인이 안되어있을 경우 로그인 페이지 이동
		http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		
		// ROLE_ADMIN만 접근 가능
		http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
		
		// 액세스 거부시 리다이렉팅 페이지 설정
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		http
			.authorizeRequests()
				.and()
			// 로그인 설정
			.formLogin()
				.loginProcessingUrl("/j_spring_security_check")
				.loginPage("/login")
				.defaultSuccessUrl("/userAccountInfo")
				.failureUrl("/login?error=true")
				.usernameParameter("username")
				.passwordParameter("password")
				.and()
			// 로그아웃 페이지 설정
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccessful");
		
		http
			.authorizeRequests()
				.and()
			.rememberMe()
			.tokenRepository(this.persistentTokenRepository())
			// 24시간 유지
			.tokenValiditySeconds(1 * 24 * 60 * 60);
		
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}
	
}
