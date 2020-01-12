package pl.coderslab;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;


public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;


    public Customer() {
    }

    public Customer(int id, String firstName, String lastName, String birthDate, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String birthDate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Length(min = 5, max=50, message="Email musi mieć między 5 a 50 znaków")
    @NotEmpty(message = "Email nie może być pusty")
    @Pattern(regexp = "[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}",
            message = "Niepoprawny format emaila")
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    @NotEmpty(message = "Imię nie może być puste")
    @Length(min = 3, max = 100, message = "Imię musi mieć co najmniej 3 znaki")
    @Pattern(regexp = "[AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]+", message = "Imię może zawierać tylko litery")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @NotEmpty(message = "Nazwisko nie może być puste")
    @Length(min = 3, max = 10, message = "Nazwisko może mieć od 3 do 100 znaków")
    @Pattern(regexp = "[AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż\\-']+", message = "Nazwisko może zawierać tylko litery, myślik i apostrof")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "Błędny format daty")
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
