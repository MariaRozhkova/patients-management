package org.hospital.management.patients.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private static final String OAUTH_SCHEME = "auth";
    public static final String OPENID_CONNECT_URL_PART = "/protocol/openid-connect";
    public static final String AUTH_URL_PART = OPENID_CONNECT_URL_PART + "/auth";
    public static final String TOKEN_URL_PART = OPENID_CONNECT_URL_PART + "/token";

    @Value("${openApi.server.url}")
    private String serverUrl;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String authURL;

    @Bean
    public OpenAPI myOpenAPI() {
        var localServer = new Server();
        localServer.setUrl(serverUrl);
        localServer.setDescription("Server URL in local environment");

        var info = new Info()
            .title("Patients Management API")
            .version("1.0")
            .description("This API exposes endpoints to manage patients.");

        return new OpenAPI()
            .info(info)
            .servers(Collections.singletonList(localServer))
            .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME))
            .components(new Components().addSecuritySchemes(OAUTH_SCHEME, createOAuthScheme()));
    }

    private SecurityScheme createOAuthScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(createOAuthFlows());
    }

    private OAuthFlows createOAuthFlows() {
        final var oauthFlow = new OAuthFlow()
            .authorizationUrl(authURL + AUTH_URL_PART)
            .refreshUrl(authURL + TOKEN_URL_PART)
            .tokenUrl(authURL + TOKEN_URL_PART)
            .scopes(new Scopes());

        return new OAuthFlows().implicit(oauthFlow);
    }
}
