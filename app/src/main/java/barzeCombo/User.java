package barzeCombo;

public class User {
    private String username;
    private String password;
    private String status;
    private Integer points;
    private String bar;

    public User() {

    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public Integer getPoints() {
        return points;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getBar() {
        return bar;
    }
}
