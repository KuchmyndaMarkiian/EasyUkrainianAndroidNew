package Models.AutorizationModels.Abstract;

import Infrastructure.Interfaces.IAuthorization;

/**
 * Created by mark0 on 26.04.2017.
 */
public class UserModel implements IAuthorization {
    String Nick;
    String Password;

    @Override
    public void setUsername(String name) {
        Nick = name;
    }

    @Override
    public void setPassword(String name) {
        Password = name;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Nick;
    }
}
