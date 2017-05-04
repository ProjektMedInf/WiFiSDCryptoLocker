package io.github.projektmedinf.wifisdcryptolocker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class User implements Parcelable {

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
    private long id;
    private String username;
    private String password;
    private byte[] salt;
    private byte[] cryptoKey;
    private byte[] cryptoKey_iv;
    private Date createdAt;
    private String passwordNotHashed;

    public User() {}

    public User(long id, String username, String password, byte[] salt, byte[] cryptoKey, byte[] cryptoKey_iv,
                Date createdAt, String passwordNotHashed) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.cryptoKey = cryptoKey;
        this.cryptoKey_iv = cryptoKey_iv;
        this.createdAt = createdAt;
        this.passwordNotHashed = passwordNotHashed;
    }

    private User(Parcel parcel) {
        this.id = parcel.readLong();
        this.username = parcel.readString();
        this.password = parcel.readString();
        this.salt = new byte[parcel.readInt()];
        parcel.readByteArray(this.salt);
        this.cryptoKey = new byte[parcel.readInt()];
        parcel.readByteArray(this.cryptoKey);
        this.cryptoKey_iv = new byte[parcel.readInt()];
        parcel.readByteArray(this.cryptoKey_iv);
        this.createdAt = new Date(parcel.readLong());
        this.passwordNotHashed = parcel.readString();
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

    public byte[] getCryptoKey() {
        return cryptoKey;
    }

    public void setCryptoKey(byte[] cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getCryptoKeyIV() {
        return cryptoKey_iv;
    }

    public void setCryptoKeyIV(byte[] cryptoKey_iv) {
        this.cryptoKey_iv = cryptoKey_iv;
    }

    public String getPasswordNotHashed() {
        return passwordNotHashed;
    }

    public void setPasswordNotHashed(String passwordNotHashed) {
        this.passwordNotHashed = passwordNotHashed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(this.id);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeInt(this.salt.length);
        parcel.writeByteArray(this.salt);
        parcel.writeInt(this.cryptoKey.length);
        parcel.writeByteArray(this.cryptoKey);
        parcel.writeInt(this.cryptoKey_iv.length);
        parcel.writeByteArray(this.cryptoKey_iv);
        parcel.writeLong(this.createdAt.getTime());
        parcel.writeString(this.passwordNotHashed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!Arrays.equals(salt, user.salt)) return false;
        if (!Arrays.equals(cryptoKey, user.cryptoKey)) return false;
        if (!Arrays.equals(cryptoKey_iv, user.cryptoKey_iv)) return false;
        if (!passwordNotHashed.equals(user.passwordNotHashed)) return false;
        return createdAt.equals(user.createdAt);
    }
}
