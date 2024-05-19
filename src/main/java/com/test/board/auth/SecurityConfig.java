package com.test.board.auth;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable) //csrf 비활성화
			.authorizeHttpRequests((authorize) -> {
				authorize.requestMatchers("/", "/login", "/register", "/member/**", "/board/**").permitAll()
					.anyRequest().authenticated();
			})
			.formLogin((form) -> {
				form.loginPage("/login")
					.loginProcessingUrl("/member/login")
					.defaultSuccessUrl("/main", true) //.defaultSuccessUrl("/main", true)를 하면 alwaysUse 가 true가 되기 때문에 setAlwaysUseDefaultTargetUrl 항상 메인 url을 타켓으로 하게 된다.
					.failureUrl("/login?error=true")
					.permitAll();
			})
			.logout((logout) -> {
				logout.logoutUrl("/logout");
			})
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// //resources를 접근할 수 있도록
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web -> web.ignoring()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
	}
}
