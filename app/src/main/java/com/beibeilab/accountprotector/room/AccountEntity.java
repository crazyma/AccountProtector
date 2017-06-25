package com.beibeilab.accountprotector.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.beibeilab.accountprotector.util.Util;

/**
 * Created by david on 2017/6/9.
 */

@Entity
public class AccountEntity {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "service_name")
    private String serviceName;

    @ColumnInfo(name = "oauth")
    private String oauth;

    @ColumnInfo(name = "account")
    private String account;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "remark")
    private String remark;

    @ColumnInfo(name = "color")
    private int color;

    public AccountEntity() {
    }

    @Ignore
    public AccountEntity(String account, String password, String userName, String email, String remark) {
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.remark = remark;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isValid(){
        return Util.validString(serviceName) &&
                (Util.validString(oauth) || Util.validString(account) || Util.validString(email) ||
                Util.validString(password) || Util.validString(userName) || Util.validString(remark));
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("uid: ");
        builder.append(uid);
        builder.append(", service name: ");
        builder.append(serviceName);

        if(oauth != null) {
            builder.append(", oauth: ");
            builder.append(oauth);
        }
        if(account != null) {
            builder.append(", account: ");
            builder.append(account);
        }
        if(email != null) {
            builder.append(", email: ");
            builder.append(email);
        }
        if(password != null) {
            builder.append(", password: ");
            builder.append(password);
        }
        if(userName != null) {
            builder.append(", userName: ");
            builder.append(userName);
        }
        if(remark != null) {
            builder.append(", remark: ");
            builder.append(remark);
        }
        builder.append(", color: ");
        builder.append(color);

        return builder.toString();
    }
}
