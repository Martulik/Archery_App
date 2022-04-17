package spring.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    @Value("$(jwt.secret-key)")
    private String secretKey;
    //private String secretKey = "dfgyhujikol;rtyuij";
    private long validityInMs = 180000;
}
