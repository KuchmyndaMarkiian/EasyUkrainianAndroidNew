package Infrastructure.AccountSessions;

import java.io.Serializable;

/**
 * Created by mark0 on 26.04.2017.
 */
public class Token implements Serializable {

    private String access_token;
    private String token_type;
    private int expires_in;

    public Token() {

    }

    public int getExpiresIn() {
        return expires_in;
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getToken() {
        return getTokenType() + " " + getAccessToken();
    }
}

