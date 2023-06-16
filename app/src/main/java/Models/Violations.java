package Models;

public class Violations {
    private String status;
    private String type;
    private int fees;
    private String id;


    public Violations(){

    }

    public Violations(String status, String type, int fees, String id) {
        this.status = status;
        this.type = type;
        this.fees = fees;
        this.id = id;
    }
    public Violations(int fees, String type, String status) {
        this.status = status;
        this.type = type;
        this.fees = fees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

