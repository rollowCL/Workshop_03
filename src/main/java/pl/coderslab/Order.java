package pl.coderslab;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;

public class Order {
    private int id;
    private String orderNumber;
    private Date orderDate;
    private Date plannedRepairDateStart;
    private Date actualRepairDateStart;
    private Date actualRepairDateEnd;
    private String problemDesc;
    private String repairDesc;
    private BigDecimal customerCost;
    private BigDecimal partsCost;
    private BigDecimal employeeManHourCost;
    private BigDecimal manHoursSpent;
    private Status status;
    private Vehicle vehicle;
    private Employee employee;
    private OrderCancelReason orderCancelReason;


    public Order() {
    }

    public Order(int id, BigDecimal customerCost, BigDecimal partsCost, Employee employee) {
        this.id = id;
        this.customerCost = customerCost;
        this.partsCost = partsCost;
        this.employee = employee;
    }

    public Order(String orderNumber, Date orderDate, Date plannedRepairDateStart, Date actualRepairDateStart, String problemDesc, String repairDesc, BigDecimal customerCost, BigDecimal partsCost, BigDecimal employeeManHourCost, BigDecimal manHoursSpent, Status status, Vehicle vehicle) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.plannedRepairDateStart = plannedRepairDateStart;
        this.actualRepairDateStart = actualRepairDateStart;
        this.problemDesc = problemDesc;
        this.repairDesc = repairDesc;
        this.customerCost = customerCost;
        this.partsCost = partsCost;
        this.employeeManHourCost = employeeManHourCost;
        this.manHoursSpent = manHoursSpent;
        this.status = status;
        this.vehicle = vehicle;
    }

    public Order(int id, String orderNumber, Date orderDate, Date plannedRepairDateStart, Date actualRepairDateStart, String problemDesc, String repairDesc, BigDecimal customerCost, BigDecimal partsCost, BigDecimal employeeManHourCost, BigDecimal manHoursSpent, Status status, Vehicle vehicle, Employee employee, OrderCancelReason orderCancelReason) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.plannedRepairDateStart = plannedRepairDateStart;
        this.actualRepairDateStart = actualRepairDateStart;
        this.problemDesc = problemDesc;
        this.repairDesc = repairDesc;
        this.customerCost = customerCost;
        this.partsCost = partsCost;
        this.employeeManHourCost = employeeManHourCost;
        this.manHoursSpent = manHoursSpent;
        this.employee = employee;
        this.status = status;
        this.vehicle = vehicle;
        this.orderCancelReason = orderCancelReason;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getOrderNumber() {

        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {

        this.orderNumber = orderNumber;
    }


    public Date getOrderDate() {

        return orderDate;
    }

    public void setOrderDate(Date orderDate) {

        this.orderDate = orderDate;
    }

    public Date getPlannedRepairDateStart() {

        return plannedRepairDateStart;
    }

    public void setPlannedRepairDateStart(Date plannedRepairDateStart) {

        this.plannedRepairDateStart = plannedRepairDateStart;
    }

    public Date getActualRepairDateStart() {

        return actualRepairDateStart;
    }

    public Date getActualRepairDateEnd() {

        return actualRepairDateEnd;
    }

    public void setActualRepairDateEnd(Date actualRepairDateEnd) {

        this.actualRepairDateEnd = actualRepairDateEnd;
    }

    public void setActualRepairDateStart(Date actualRepairDateStart) {

        this.actualRepairDateStart = actualRepairDateStart;
    }

    public String getProblemDesc() {

        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {

        this.problemDesc = problemDesc;
    }

    public String getRepairDesc() {

        return repairDesc;
    }

    public void setRepairDesc(String repairDesc) {

        this.repairDesc = repairDesc;
    }

    @DecimalMin(value = "0.0", message="Koszt nie może być < 0")
    @Digits(integer = 10, fraction=2, message = "Błędny koszt dla klienta - nieprawidłowa dokładność")
    public BigDecimal getCustomerCost() {

        return customerCost;
    }

    public void setCustomerCost(BigDecimal customerCost) {

        this.customerCost = customerCost;
    }

    @DecimalMin(value = "0.0", message="Koszt nie może być < 0")
    @Digits(integer = 10, fraction=2, message = "Błędny koszt częsci - nieprawidłowa dokładność")
    public BigDecimal getPartsCost() {

        return partsCost;
    }

    public void setPartsCost(BigDecimal partsCost) {

        this.partsCost = partsCost;
    }

    public BigDecimal getEmployeeManHourCost() {

        return employeeManHourCost;
    }

    public void setEmployeeManHourCost(BigDecimal employeeManHourCost) {

        this.employeeManHourCost = employeeManHourCost;
    }

    public BigDecimal getManHoursSpent() {

        return manHoursSpent;
    }

    public void setManHoursSpent(BigDecimal manHoursSpent) {

        this.manHoursSpent = manHoursSpent;
    }

    public Employee getEmployee() {

        return employee;
    }

    public void setEmployee(Employee employee) {

        this.employee = employee;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    public Vehicle getVehicle() {

        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {

        this.vehicle = vehicle;
    }

    public OrderCancelReason getOrderCancelReason() {

        return orderCancelReason;
    }

    public void setOrderCancelReason(OrderCancelReason orderCancelReason) {

        this.orderCancelReason = orderCancelReason;
    }

    public static boolean isValidNumber(String number) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[0-9]+(,[0-9]{1,2})?$");
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static String validateOrderCost(Order order) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Order>> constraintViolationsCustomerCost = validator.validateProperty(order, "customerCost");
        Set<ConstraintViolation<Order>> constraintViolationsPartsCost = validator.validateProperty(order, "partsCost");

        String errors = "";
        if (!constraintViolationsCustomerCost.isEmpty()) {
            for (ConstraintViolation<Order> constraintViolation : constraintViolationsCustomerCost) {
                errors += "<li>" + " " + constraintViolation.getMessage()
                        + "</li>";
            }

        }

        if (!constraintViolationsPartsCost.isEmpty()) {
            for (ConstraintViolation<Order> constraintViolation : constraintViolationsPartsCost) {
                errors += "<li>" + " " + constraintViolation.getMessage()
                        + "</li>";
            }

        }

        return errors;
    }

    public static double calculateManHours(LocalDateTime fromDate, LocalDateTime toDate, int workHourStart, int workMinuteStart,
                                    int workHourEnd, int workMinuteEnd) {

        LocalDateTime fromDateMinManHour = fromDate.withHour(workHourStart).withMinute(workMinuteStart).withMinute(0).withSecond(0);
        LocalDateTime toDateMaxManHour = toDate.withHour(workHourEnd).withMinute(workMinuteEnd).withMinute(0).withSecond(0);

        long days = ChronoUnit.DAYS.between(fromDate, toDate);
        long minutesStartDay = ChronoUnit.MINUTES.between(fromDateMinManHour, fromDate);
        long minutesEndDay = ChronoUnit.MINUTES.between(toDate, toDateMaxManHour);

        if (minutesStartDay < 0) {
            minutesStartDay = 0;

        }
        if (minutesEndDay < 0) {
            minutesEndDay = 0;

        }

        long totalMinutes = (days * 8 * 60) + minutesStartDay + + minutesEndDay;
        double totalHours = (double)Math.round((totalMinutes/60.0) * 100d) / 100d;

        return totalHours;

    }

}
