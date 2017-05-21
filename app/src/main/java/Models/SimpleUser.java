package Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mark0 on 28.04.2017.
 */
public class SimpleUser implements Serializable {
    @SerializedName("Name")
    public String name;
    @SerializedName("Surname")
    public String surname;
    @SerializedName("Nickname")
    public String nickname;
    @SerializedName("DateOfBirth")
    public String dateOfBirth;
    @SerializedName("Email")
    public String email;
    @SerializedName("Score")
    public int score;
    @SerializedName("MaxScore")
    public int maxScore;
    @SerializedName("Level")
    public String level;
    @SerializedName("IsTested")
    public boolean isTested;

    //todo: need code expert subsystem
    public void setTested(boolean tested) {
        isTested = tested;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public Date getDateOfBirth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS.sss");
        try {
            return dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getIsTested() {
        return isTested;
    }
}
