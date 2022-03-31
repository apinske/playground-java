package eu.pinske.playground;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.impl.plugin.AdministratorAuthorizationPlugin;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ProcessApplication("playground")
public class PlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaygroundApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    public FilterRegistrationBean<?> processEngineAuthenticationFilter() {
        FilterRegistrationBean<ProcessEngineAuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setName("camunda-auth");
        registration.setFilter(new ProcessEngineAuthenticationFilter());
        registration.addInitParameter("authentication-provider",
                "org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider");
        registration.addUrlPatterns("/engine-rest/*");
        return registration;
    }

    @Bean
    public WebSecurityConfigurerAdapter security() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http //
                        .authorizeRequests() //
                        .antMatchers("/playground-api/*", "/").fullyAuthenticated() //
                        .anyRequest().permitAll() //
                        .and().formLogin() //
                        .and().httpBasic() //
                        .and().csrf().disable();
            }

            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.ldapAuthentication() //
                        .userDnPatterns("cn={0},ou=users") //
                        .groupSearchBase("ou=groups") //
                        .contextSource().url("ldap://localhost:3389/dc=pinske,dc=eu");
            }
        };
    }

    @Bean
    public static AdministratorAuthorizationPlugin administratorAuthorizationPlugin() {
        AdministratorAuthorizationPlugin plugin = new AdministratorAuthorizationPlugin();
        plugin.setAdministratorGroupName("playground-camunda-admin");
        return plugin;
    }

    @Bean
    public static LdapIdentityProviderPlugin ldapIdentityProviderPlugin() {
        LdapIdentityProviderPlugin plugin = new LdapIdentityProviderPlugin();
        plugin.setServerUrl("ldap://localhost:3389");
        plugin.setAllowAnonymousLogin(true);
        plugin.setBaseDn("dc=pinske,dc=eu");
        plugin.setUserSearchBase("ou=users");
        plugin.setUserIdAttribute("cn");
        plugin.setUserFirstnameAttribute("givenName");
        plugin.setUserPasswordAttribute("userPassword");
        plugin.setGroupSearchBase("ou=groups");
        plugin.setGroupIdAttribute("cn");
        plugin.setGroupMemberAttribute("member");
        return plugin;
    }
}
