package pl.coderslab;

public class OrderNumber {
    private int id;
    private int year;
    private int number;

    public OrderNumber(int year, int number) {
        this.year = year;
        this.number = number;
    }

    public OrderNumber() {
    }

    public OrderNumber(int id, int year, int number) {
        this.id = id;
        this.year = year;
        this.number = number;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getYear() {

        return year;
    }

    public void setYear(int year) {

        this.year = year;
    }

    public int getNumber() {

        return number;
    }

    public void setNumber(int number) {

        this.number = number;
    }
}
