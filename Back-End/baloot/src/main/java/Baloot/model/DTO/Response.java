package Baloot.model.DTO;

public class Response {
    public boolean status;
    public String statusMessage;
    public Object content;

    public Response(boolean status, String statusMessage, Object content) {
        this.status = status;
        this.statusMessage = statusMessage;
        this.content = content;
    }

    public boolean getStatusCode() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Object getContent() {
        return content;
    }

    public void setStatusCode(boolean status) {
        this.status = status;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
