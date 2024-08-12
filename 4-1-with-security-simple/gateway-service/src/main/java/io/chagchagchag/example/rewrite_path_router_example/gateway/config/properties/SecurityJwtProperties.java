package io.chagchagchag.example.rewrite_path_router_example.gateway.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtProperties {
  private String key;
}
