package com.iudc.security;


import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Joe Grandja
 */
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
	private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;

	public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
		this.defaultAuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(
				clientRegistrationRepository, "/oauth2/authorization");
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
		OAuth2AuthorizationRequest authorizationRequest =
				this.defaultAuthorizationRequestResolver.resolve(request);
		if (authorizationRequest != null) {
			return customAuthorizationRequest(authorizationRequest);
		}
		return authorizationRequest;
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
		OAuth2AuthorizationRequest authorizationRequest =
				this.defaultAuthorizationRequestResolver.resolve(request, clientRegistrationId);
		if (authorizationRequest != null) {
			return customAuthorizationRequest(authorizationRequest);
		}
		return authorizationRequest;
	}

	private OAuth2AuthorizationRequest customAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest) {
		Set<String> scopes = new LinkedHashSet<>(authorizationRequest.getScopes());
		scopes.add("contacts");
		return OAuth2AuthorizationRequest.from(authorizationRequest)
				.scopes(scopes)
				.build();
	}
}