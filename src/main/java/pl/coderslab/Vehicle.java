package pl.coderslab;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.*;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String model;
    private String manufacturer;
    private int productionYear;
    private String registrationNo;
    private Date nextTechnicalCheckDate;
    private Customer customer;

    public Vehicle() {
    }

    public Vehicle(int id, String model, String manufacturer, int productionYear, String registrationNo, Date nextTechnicalCheckDate, Customer customer, String customerString, int noOfOrders) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.productionYear = productionYear;
        this.registrationNo = registrationNo;
        this.nextTechnicalCheckDate = nextTechnicalCheckDate;
        this.customer = customer;
    }

    public Vehicle(int id, String model, String manufacturer, int productionYear, String registrationNo, Date nextTechnicalCheckDate, Customer customer) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.productionYear = productionYear;
        this.registrationNo = registrationNo;
        this.nextTechnicalCheckDate = nextTechnicalCheckDate;
        this.customer = customer;
    }

    public Vehicle(String model, String manufacturer, int productionYear, String registrationNo, Date nextTechnicalCheckDate, Customer customer) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.productionYear = productionYear;
        this.registrationNo = registrationNo;
        this.nextTechnicalCheckDate = nextTechnicalCheckDate;
        this.customer = customer;
    }

    @Length(max = 100, message = "Model może mieć max 100 znaków")
    @NotEmpty(message = "Model nie może być pusty")
    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

    @Length(max = 100, message = "Producent może mieć max 100 znaków")
    @NotEmpty(message = "Producent nie może być pusty")
    public String getManufacturer() {

        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Min(value = 1950, message = "Rok produkcji nie może być mniejszy niż 1950")
    @Max(value = 2020, message = "Rok produkcji nie może być starszy niż 2020")
    public int getProductionYear() {

        return productionYear;
    }

    public void setProductionYear(int productionYear) {

        this.productionYear = productionYear;
    }

    @Size(min = 6, max = 10, message = "Nieprawidłowa długość nr rejestracyjnego")
    public String getRegistrationNo() {

        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {

        this.registrationNo = registrationNo;
    }

    @NotNull(message = "Data kolejnego badania nie może być pusta")
    @FutureOrPresent(message = "Data kolejnego badania nie może być z przeszłości")
    public Date getNextTechnicalCheckDate() {

        return nextTechnicalCheckDate;
    }

    public void setNextTechnicalCheckDate(Date nextTechnicalCheckDate) {

        this.nextTechnicalCheckDate = nextTechnicalCheckDate;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
