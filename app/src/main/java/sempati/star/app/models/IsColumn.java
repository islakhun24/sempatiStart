package sempati.star.app.models;

public class IsColumn {
    String status;
    boolean isMe;

    public IsColumn() {
    }

    public IsColumn(String status, boolean isMe) {
        this.status = status;
        this.isMe = isMe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
