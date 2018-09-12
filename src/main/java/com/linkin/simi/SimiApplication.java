package com.linkin.simi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.linkin.simi.entity.User;
import com.linkin.simi.service.impl.AuditorAwareImpl;
import com.linkin.simi.utils.UserRoleEnum;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SimiApplication {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(SimiApplication.class, args);
	}

	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Configuration
	@Order(1)
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable().antMatcher("/api/**").authorizeRequests().antMatchers("/api/admin/**")
			.permitAll();
			// http.csrf().disable().antMatcher("/api/**").authorizeRequests().antMatchers("/api/admin/**")
			// .hasRole(UserRoleEnum.ADMIN.getRole()).antMatchers("/api/staff/**")
			// .hasAnyRole(UserRoleEnum.ADMIN.getRole(),
			// UserRoleEnum.STAFF.getRole()).antMatchers("/user/**")
			// .authenticated().anyRequest().permitAll().and().httpBasic().and().sessionManagement()
			// .sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().migrateSession()
			// .maximumSessions(-1).sessionRegistry(sessionRegistry());
		}
	}

	@Configuration
	public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().and().authorizeRequests().antMatchers("/admin/**").hasAnyRole(UserRoleEnum.ADMIN.getRole())
					.antMatchers("/staff/**").hasAnyRole(UserRoleEnum.ADMIN.getRole(), UserRoleEnum.STAFF.getRole())
					.antMatchers("/user/**").authenticated().anyRequest().permitAll().and().formLogin()
					.loginPage("/dang-nhap").loginProcessingUrl("/dang-nhap")
					.defaultSuccessUrl("/admin/sims", true).failureUrl("/dang-nhap?e").and().rememberMe()
					.rememberMeCookieName("app-remember-me").tokenValiditySeconds(24 * 60 * 60 * 30)
					.tokenRepository(persistentTokenRepository()).and().logout().logoutUrl("/dang-xuat")
					.logoutSuccessUrl("/dang-nhap").logoutRequestMatcher(new AntPathRequestMatcher("/dang-xuat"))
					.clearAuthentication(true).invalidateHttpSession(true)
					.deleteCookies("JSESSIONID", "app-remember-me").permitAll().and().exceptionHandling()
					.accessDeniedPage("/access-deny").and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().migrateSession()
					.maximumSessions(-1).sessionRegistry(sessionRegistry());
		}
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		return bCryptPasswordEncoder;
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		SpringSecurityDialect dialect = new SpringSecurityDialect();
		return dialect;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
}