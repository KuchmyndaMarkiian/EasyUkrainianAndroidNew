package Infrastructure.AccountSessions;

import Models.SimpleUser;

import java.io.Serializable;

/**
 * Created by mark0 on 26.04.2017.
 */
public class User extends SimpleUser implements Serializable {
    byte[] avatar;
    Token token;

    public User() {
    }

    public void cloneFrom(SimpleUser user) {
        setNickname(user.nickname);
        setDateOfBirth(user.dateOfBirth);
        setEmail(user.email);
        setLevel(user.level);
        setSurname(user.surname);
        setMaxScore(user.maxScore);
        score = user.score;
        setName(user.name);
    }

    public void cloneFromMemory(User user) {
        cloneFrom(user);
        setToken(user.getToken());
        setAvatar(user.avatar);
    }

    public SimpleUser parseToSimpleUser() {
        SimpleUser res = new SimpleUser();
        res.setName(getName());
        res.setSurname(getSurname());
        res.setScore(getScore());
        res.setDateOfBirth(this.dateOfBirth);
        res.setEmail(getEmail());
        res.setLevel(getLevel());
        res.setTested(getIsTested());
        res.setNickname(getNickname());
        res.setMaxScore(getMaxScore());
        return res;
    }

    //endregion
    //region Getters
    public Token getToken() {
        return token;
    }

    //region Setters
    public void setToken(Token token) {
        this.token = token;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    //endregion
}
