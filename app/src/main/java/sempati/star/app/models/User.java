package sempati.star.app.models;

public class User {
    private int id;
    private String username;
    private int agenID;
    private int statusUser;
    private String accessToken;

    public User() {
    }

    public User(int id, String username, int agenID, int statusUser, String accessToken) {
        this.id = id;
        this.username = username;
        this.agenID = agenID;
        this.statusUser = statusUser;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAgenID() {
        return agenID;
    }

    public void setAgenID(int agenID) {
        this.agenID = agenID;
    }

    public int getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(int statusUser) {
        this.statusUser = statusUser;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
