package com.SMA.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.SMA.Queries;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@MapperScan("com.SMA.mapper")
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(Queries.SELECT_USER)
				.authoritiesByUsernameQuery(Queries.SELECT_ROLE);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("Config Accessed");
		http.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/index.html", "/views/login.html", "/scripts/*",
						"/styles/*", "/images/*","/").permitAll().anyRequest()
				.authenticated().and()
				.addFilterAfter(new CSRFFilter(), CsrfFilter.class).csrf()
				.csrfTokenRepository(csrfTokenRepository());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		return sessionFactory.getObject();
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

}
