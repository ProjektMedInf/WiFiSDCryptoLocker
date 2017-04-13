package io.github.projektmedinf.wifisdcryptolocker.model;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class Userdata {

    private String username;
    private String password;

    public Userdata(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Userdata userdata = (Userdata) o;

        if (!username.equals(userdata.username)) return false;
        return password.equals(userdata.password);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Userdata{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
