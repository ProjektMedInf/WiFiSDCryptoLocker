package io.github.projektmedinf.wifisdcryptolocker.model;

import java.util.Date;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class Userdata {

    private long id;
    private String username;
    private String password;
    private Date createdAt;

    public Userdata(){

    }

    public Userdata(long id, String username, String password, Date createdAt) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Userdata userdata = (Userdata) o;

        if (id != userdata.id) return false;
        if (!username.equals(userdata.username)) return false;
        if (!password.equals(userdata.password)) return false;
        return createdAt.equals(userdata.createdAt);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + createdAt.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Userdata{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
