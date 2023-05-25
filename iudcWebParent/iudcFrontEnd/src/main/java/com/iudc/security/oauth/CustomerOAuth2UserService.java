package com.iudc.security.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerOAuth2UserService extends DefaultOAuth2UserService {

	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        
        if (clientName.equals("Linkedin")) {
            String accessToken = userRequest.getAccessToken().getTokenValue();
            String email = getEmailFromLinkedIn(accessToken);
            return new CustomerOAuth2User(user, clientName, email);
        }
        
        return new CustomerOAuth2User(user, clientName);
    }

    private String getEmailFromLinkedIn(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<JsonNode> response = restTemplate.exchange("https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))", HttpMethod.GET, entity, JsonNode.class);
        JsonNode node = response.getBody();
        return node.get("elements").get(0).get("handle~").get("emailAddress").asText();
    }

}
