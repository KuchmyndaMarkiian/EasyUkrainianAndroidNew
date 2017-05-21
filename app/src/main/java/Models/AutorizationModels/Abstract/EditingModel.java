package Models.AutorizationModels.Abstract;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by mark0 on 26.04.2017.
 */
public class EditingModel extends UserModel {
    String Name;
    String Surname;
    String ConfirmPassword;
    String Email;
    String Date;

    public void setDateOfBirth(int day, int month, int year) {
        setDateOfBirth(new GregorianCalendar(year, month, day));
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDateOfBirth() {
        return Date;
    }

    private void setDateOfBirth(GregorianCalendar dateOfBirth) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date = sdfDate.format(dateOfBirth.getTime());
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }
}
