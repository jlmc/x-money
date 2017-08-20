package com.xcosta.xmoney.api.security.token;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // apenas processar quando o nome do metodo for igual a: postAccessToken
        return returnType.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(
            OAuth2AccessToken body
            , MethodParameter returnType
            , MediaType selectedContentType
            , Class<? extends HttpMessageConverter<?>> selectedConverterType
            , ServerHttpRequest request
            , ServerHttpResponse response) {

        String refreshToken = body.getRefreshToken().getValue();

        addRefreshTokenToCookie(refreshToken, request, response);

        removeRefreshTokenFromTheBody(body);

        return body;
    }

    private void removeRefreshTokenFromTheBody(OAuth2AccessToken body) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
        if (token != null) {
            token.setRefreshToken(null);
        }
    }

    private void addRefreshTokenToCookie(String refreshToken, ServerHttpRequest request, ServerHttpResponse response) {

        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // TODO:  Mudar para true em producao HTTPS
        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge(2592000);

        resp.addCookie(refreshTokenCookie);

    }
}
