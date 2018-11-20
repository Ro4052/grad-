package com.scottlogic.librarygradproject;

import com.scottlogic.librarygradproject.Entities.LibraryUser;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.io.Serializable;
import java.security.Principal;
import java.util.*;

public class OAuthClientTestHelper {

    private String userId;
    private String name;
    private String avatarUrl;

    public OAuthClientTestHelper(String userId, String name, String avatarUrl) {
        this.userId = userId;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    private class OAuthUser implements Principal {
        private LibraryUser user;

        OAuthUser(LibraryUser user) {
            this.user = user;
        }

        @Override
        public String getName() {
            return this.user.getUserId();
        }
    }

    private OAuth2Request getOauth2Request () {
        String clientId = "oauth-client-id";
        Map<String, String> requestParameters = Collections.emptyMap();
        boolean approved = true;
        String redirectUrl = "";
        Set<String> responseTypes = Collections.emptySet();
        Set<String> scopes = Collections.emptySet();
        Set<String> resourceIds = Collections.emptySet();
        Map<String, Serializable> extensionProperties = Collections.emptyMap();
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("Everything");

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities,
                approved, scopes, resourceIds, redirectUrl, responseTypes, extensionProperties);

        return oAuth2Request;
    }

    private Authentication getAuthentication() {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("Everything");

        LibraryUser userPrincipal = LibraryUser.builder()
                .userId(userId)
                .name(name)
                .avatarUrl(avatarUrl)
                .build();

        HashMap<String, String> details = new HashMap<String, String>();
        details.put("name", name);
        details.put("login", userId);
        details.put("avatar_url", avatarUrl);

        OAuthUser oAuthUser = new OAuthUser(userPrincipal);

        TestingAuthenticationToken token = new TestingAuthenticationToken(oAuthUser,null, authorities);
        token.setAuthenticated(true);
        token.setDetails(details);

        return token;
    }

    public OAuth2Authentication getOauthTestAuthentication() {
        return new OAuth2Authentication(getOauth2Request(), getAuthentication());
    }
}
