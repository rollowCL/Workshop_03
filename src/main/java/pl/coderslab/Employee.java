package pl.coderslab;

import org.hibernate.validator.constraints.Length;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Set;
import java.util.regex.Matcher;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String city;
    private String street;
    private String building;
    private String apartment;
    private String phone;
    private String notes;
    private BigDecimal manHourCost;
    private String avatarFileName;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, String zipCode, String city, String street, String building, String apartment, String phone, String notes, BigDecimal manHourCost) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.phone = phone;
        this.notes = notes;
        this.manHourCost = manHourCost;
    }

    public Employee(String firstName, String lastName, String zipCode, String city, String street, String building, String apartment, String phone, String notes, BigDecimal manHourCost) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.phone = phone;
        this.notes = notes;
        this.manHourCost = manHourCost;
    }

    public Employee(String firstName, String lastName, String zipCode, String city, String street, String building, String apartment, String phone, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.phone = phone;
        this.notes = notes;
    }

    public Employee(String firstName, String lastName, String zipCode, String city, String street, String building, String apartment, String phone, String notes, String avatarFileName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.phone = phone;
        this.notes = notes;
        this.avatarFileName = avatarFileName;
    }

    public Employee(String firstName, String lastName, String zipCode, String city, String street, String building, String apartment, String phone, String notes, BigDecimal manHourCost, String avatarFileName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.phone = phone;
        this.notes = notes;
        this.manHourCost = manHourCost;
        this.avatarFileName = avatarFileName;
    }

    public Employee(int employeeId, String firstName, String lastName, String zipCode, String city, String street, String building, String apartment, String phone, String notes) {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    @Length(max = 100, message = "Imię może mieć max 100 znaków")
    @NotEmpty(message = "Imię nie może być puste")
    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    @Length(max = 100, message = "Nazwisko może mieć max 100 znaków")
    @NotEmpty(message = "Nazwisko nie może być puste")
    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    @NotEmpty(message = "Kod pocztowy nie może być pusty")
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Nieprawidłowy format kodu pocztowego")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {

        this.zipCode = zipCode;
    }

    @Length(max = 100, message = "Miasto może mieć max 100 znaków")
    @NotEmpty(message = "Miasto nie może być puste")
    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    @Length(max = 100, message = "Ulica może mieć max 100 znaków")
    public String getStreet() {

        return street;
    }

    public void setStreet(String street) {

        this.street = street;
    }

    @Length(max = 5, message = "Budynek może mieć max 5 znaków")
    @NotEmpty(message = "Budynek nie może być pusty")
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {

        this.building = building;
    }

    @Length(max = 5, message = "Nr mieszkania może mieć max 5 znaków")
    public String getApartment() {

        return apartment;
    }

    public void setApartment(String apartment) {

        this.apartment = apartment;
    }

    @NotEmpty(message = "Numer telefonu nie może być pusty")
    @Pattern(regexp = "[0-9]{9}", message = "Nieprawidłowy format numeru telefonu")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getNotes() {

        return notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;
    }

    @DecimalMin(value = "0.0", inclusive = false, message = "Koszt nie może być <= 0")
    @Digits(integer = 10, fraction = 2, message = "Błędny koszt roboczogodziny - nieprawidłowa dokładność")
    public BigDecimal getManHourCost() {

        return manHourCost;
    }

    public void setManHourCost(BigDecimal manHourCost) {

        this.manHourCost = manHourCost;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public static boolean isValidNumber(String number) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[0-9]+(,[0-9]{1,2})?$");
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static String validateEmployee(Employee employee) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);
        String errors = "";
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<Employee> constraintViolation : constraintViolations) {
                errors += "<li>" + " " + constraintViolation.getMessage()
                        + "</li>";
            }

        }

        return errors;
    }
}
