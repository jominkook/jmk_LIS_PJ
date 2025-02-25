package com.yk.logistic.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yk.logistic.service.member.UserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final UserDetailService userService;
	
	@Bean
	public WebSecurityCustomizer configure() {
		return (web)->web.ignoring()
				.requestMatchers(new AntPathRequestMatcher("/static/**"));
	}
	
	
	//특정 HTTP 요청에 대한 웹 기반 보안 구성
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
				//.authorizeRequests -> Spring Security 6.1부터 더이상 사용되지 않는다.
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers(
	                        new AntPathRequestMatcher("/login"),
	                        new AntPathRequestMatcher("/signup"),
	                        new AntPathRequestMatcher("/user")
	                    ).permitAll()
	                    .anyRequest().authenticated()
	                )
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.defaultSuccessUrl("/members")
				)
				.logout(logout -> logout
						.logoutUrl("/login")
						.invalidateHttpSession(true)
				)
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}
	
	//인증 관리자 관련 설정
	@Bean
	public AuthenticationManager authenticationMager(HttpSecurity http,
			BCryptPasswordEncoder bCryptPasswordEncoder,UserDetailService userDetailService)
	throws Exception{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return new ProviderManager(authProvider);
	}
	
	//패스워드 인코더로 사용할 빈 등록
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
