package spring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import spring.security.jwt.JwtProperties;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class TokenData implements Serializable {

    private String access_token;
    private String refresh_token;
    private String expires_in;

    public TokenData() {
        JwtProperties properties = new JwtProperties();
        expires_in = Long.toString(properties.getValidityInMs());
    }
}
