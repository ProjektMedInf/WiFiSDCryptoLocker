package io.github.projektmedinf.wifisdcryptolocker.model;

import java.util.Date;

/**
 * Created by stiefel40k on 20.04.17.
 */
public class Session {

    private long id;
    private byte[] encryptedDate;
    private Date date;
    private String location;
    private byte[] iv;

    public Session(long id, byte[] encryptedDate, Date date, String location, byte[] iv) {
        this.id = id;
        this.encryptedDate = encryptedDate;
        this.date = date;
        this.location = location;
        this.iv = iv;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getEncryptedDate() {
        return encryptedDate;
    }

    public void setEncryptedDate(byte[] encryptedDate) {
        this.encryptedDate = encryptedDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }
}
