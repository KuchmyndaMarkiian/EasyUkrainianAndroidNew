package Infrastructure.AccountSessions;

import java.io.Serializable;

/**
 * Created by mark0 on 26.04.2017.
 */
public class Token implements Serializable {

    public static String authorizationHeader = "authorization";
    private String access_token;
    private String token_type;
    private int expires_in;

    public Token() {

    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }


    public int getExpiresIn() {
        return expires_in;
    }

    private String getAccessToken() {
        return access_token;
    }

    private String getTokenType() {
        return token_type;
    }

    public String getToken() {
        return getTokenType() + " " + getAccessToken();
    }
}

