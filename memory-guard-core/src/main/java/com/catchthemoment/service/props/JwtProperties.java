package com.catchthemoment.service.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "security.jwt")
@Component
public class JwtProperties {

    private String secret;
    private long access;
    private long refresh;

}
