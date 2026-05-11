package com.thelastimperial.oauth2_server.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final RememberMeServices rememberMeServices;

	private String rpId;
	private List<String> webauthAllowedHosts;
	
	public SecurityConfig(
		RememberMeServices rememberMeServices,
		@Value("${com.thelastimperial.oauth2_server.webauthn.rpid}")
		String rpId,
		@Value("${com.thelastimperial.oauth2_server.webauthn.allowedorigins}")
		List<String> webauthAllowedHosts
	) {
		this.rememberMeServices = rememberMeServices;
		this.rpId = rpId;
		this.webauthAllowedHosts = webauthAllowedHosts;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {

		http
			.oauth2AuthorizationServer((authorizationServer) -> {
				http.securityMatcher(authorizationServer.getEndpointsMatcher());
				authorizationServer
					.oidc(Customizer.withDefaults()) // Enable OpenID Connect 1.0
					.clientRegistrationEndpoint(Customizer.withDefaults());
			})
			.authorizeHttpRequests((authorize) ->
				authorize
					.anyRequest().authenticated()
			)
			// Redirect to the login page when not authenticated from the
			// authorization endpoint
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
			);

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/").permitAll()
				.requestMatchers("/user").hasRole("USER")
				.anyRequest().permitAll()
			)
			// Form login handles the redirect to the login page from the
			// authorization server filter chain
			.formLogin(Customizer.withDefaults())
			.rememberMe(rememberMe -> rememberMe
				.rememberMeServices(rememberMeServices)
				.rememberMeParameter("remember-me")
			)
			.webAuthn(webauthn -> webauthn
				.rpId(rpId)
				.allowedOrigins(webauthAllowedHosts.toArray(String[]::new))
			);

		return http.build();
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings
			.builder()
			.build();
	}

}
