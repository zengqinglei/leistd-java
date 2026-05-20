package com.mysoft.leistd.security;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.security.mdc.SecurityLogMdc;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {
    @ConditionalOnMissingBean
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomAuthenticationEntryPoint authEntryPoint,
            MdcProvider mdcProvider,
            SecurityLogMdc securityLogMdc) throws Exception {
        return http.authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 允许匿名访问
                                .mvcMatchers("/api/health/**").permitAll()
                                .anyRequest().hasAuthority("defaultApi")
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .bearerTokenResolver(bearerTokenResolver())
                        .authenticationEntryPoint(authEntryPoint))
                .addFilterAfter(new CustomSetUserFilter(mdcProvider, securityLogMdc), BearerTokenAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    protected JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        // 设置jwt中用户名的key  默认就是sub  你可以自定义
        jwtAuthenticationConverter.setPrincipalClaimName("name");
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 如果不按照规范  解析权限集合Authorities 就需要自定义key
        // jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scopes");
        // OAuth2 默认前缀是 SCOPE_     Spring Security 是 ROLE_
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * 从request请求中那个地方获取到token
     */
    protected BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
        // 设置请求头的参数，即从这个请求头中获取到token
        bearerTokenResolver.setBearerTokenHeaderName(HttpHeaders.AUTHORIZATION);
        bearerTokenResolver.setAllowFormEncodedBodyParameter(true);
        // 是否可以从uri请求参数中获取token
        bearerTokenResolver.setAllowUriQueryParameter(true);
        return bearerTokenResolver;
    }
}