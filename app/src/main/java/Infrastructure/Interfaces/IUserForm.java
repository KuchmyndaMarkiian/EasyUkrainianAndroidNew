package Infrastructure.Interfaces;

/**
 * Created by mark0 on 26.04.2017.
 */
public interface IUserForm {

    void setName(String text);

    void setSurname(String text);

    void setEmail(String text);

    void setDateOfBirth(int d, int m, int y);
}
