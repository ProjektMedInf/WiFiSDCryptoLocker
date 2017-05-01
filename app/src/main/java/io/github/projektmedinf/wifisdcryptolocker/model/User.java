package io.github.projektmedinf.wifisdcryptolocker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by stiefel40k on 13.04.17.
 */
public class User implements Parcelable{

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

    private User(Parcel parcel){
        this.id = parcel.readLong();
        this.username = parcel.readString();
        this.password = parcel.readString();
        this.createdAt = new Date(parcel.readLong());
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(this.id);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeLong(this.createdAt.getTime());
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return createdAt != null ? createdAt.equals(user.createdAt) : user.createdAt == null;
    }
}
