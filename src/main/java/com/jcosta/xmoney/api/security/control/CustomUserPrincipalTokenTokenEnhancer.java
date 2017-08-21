package com.jcosta.xmoney.api.security.control;

import com.jcosta.xmoney.api.security.entity.CustomUserPrincipal;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomUserPrincipalTokenTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        Map<String, Object> userInfos = new HashMap<>();
        userInfos.put("name", customUserPrincipal.getUser().getName());

        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
        defaultOAuth2AccessToken.setAdditionalInformation(userInfos);

        return accessToken;
    }
}
