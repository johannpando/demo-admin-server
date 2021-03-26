package correos.adminserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	private final AdminServerProperties adminServer;
//
//	public WebSecurityConfig(AdminServerProperties adminServer) {
//		this.adminServer = adminServer;
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//		successHandler.setTargetUrlParameter("redirectTo");
//		successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");
//
//		http.authorizeRequests().antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
//				.antMatchers(this.adminServer.getContextPath() + "/login").permitAll().anyRequest().authenticated()
//				.and().formLogin().loginPage(this.adminServer.getContextPath() + "/login")
//				.successHandler(successHandler).and().logout().logoutUrl(this.adminServer.getContextPath() + "/logout")
//				.and().httpBasic().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.ignoringRequestMatchers(
//						new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances",
//								HttpMethod.POST.toString()),
//						new AntPathRequestMatcher(this.adminServer.getContextPath() + "/instances/*",
//								HttpMethod.DELETE.toString()),
//						new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
//				.and().rememberMe().key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600);
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.ldapAuthentication().userDnPatterns("uid={0},ou=people").groupSearchBase("ou=groups").contextSource()
				.url("ldap://localhost:8399/dc=springframework,dc=org").and().passwordCompare()
				.passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("userPassword");

	}
}