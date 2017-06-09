package com.beibeilab.accountprotector.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by david on 2017/6/9.
 */

@Entity
public class AccountEntity {

    @PrimaryKey
    private int uid;

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
}
