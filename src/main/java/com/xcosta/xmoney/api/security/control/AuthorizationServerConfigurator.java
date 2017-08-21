package com.xcosta.xmoney.api.security.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurator extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Configurar das aplicações clientes

        clients.inMemory()
                .withClient("angular") // clientes da applicação Angular- nome da aplicação
                .secret("@ngul@r0") // secrete da aplicação
                .scopes("read", "write") // permissoes da aplicação no resources
                //.authorizedGrantTypes("password") // fluxo password flow, recebemos e username e passoword d o client
                .authorizedGrantTypes("password", "refresh_token") // fluxo password flow, recebemos e username e passoword d o client
                //.accessTokenValiditySeconds(1800); // validade do token
                .accessTokenValiditySeconds(20) // validade do token
                .refreshTokenValiditySeconds(3600 * 24)
                .and()
                .withClient("mobile") // clientes da applicação Angular- nome da aplicação
                .secret("mobile_1234") // secrete da aplicação
                .scopes("read") // permissoes da aplicação no resources
                //.authorizedGrantTypes("password") // fluxo password flow, recebemos e username e passoword d o client
                .authorizedGrantTypes("password", "refresh_token") // fluxo password flow, recebemos e username e passoword d o client
                //.accessTokenValiditySeconds(1800); // validade do token
                .accessTokenValiditySeconds(20) // validade do token
                .refreshTokenValiditySeconds(3600 * 24)

        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .reuseRefreshTokens(false) // por default o refresh token tem 24 horas de validade. se passar mos para false cada vez que o cliente requer um novo acceass token atravez de refresh um novo refresh token será gerado
                .authenticationManager(authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("algaworks");
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
