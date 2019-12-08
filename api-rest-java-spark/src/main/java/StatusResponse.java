
public enum StatusResponse {

    SUCCESS ("Success"),
    ERROR ("Error");

    private String status;

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    StatusResponse(String status) {
        this.status = status;
    }
}
