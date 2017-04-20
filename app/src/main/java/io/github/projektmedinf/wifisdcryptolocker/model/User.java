package io.github.projektmedinf.wifisdcryptolocker.model;

import java.util.Date;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class User {

    private long id;
    private String username;
    private String password;
    private Date createdAt;

    public User(long id, String username, String password, Date createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
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
}
