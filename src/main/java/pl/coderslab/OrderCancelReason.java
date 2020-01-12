package pl.coderslab;

public class OrderCancelReason {
    private int id;
    private String cancelReasonName;

    public OrderCancelReason() {
    }

    public OrderCancelReason(int id, String cancelReasonName) {
        this.id = id;
        this.cancelReasonName = cancelReasonName;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getCancelReasonName() {

        return cancelReasonName;
    }

    public void setCancelReasonName(String cancelReasonName) {

        this.cancelReasonName = cancelReasonName;
    }

}

