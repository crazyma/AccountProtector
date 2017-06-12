package com.beibeilab.accountprotector.room;

/**
 * Created by david on 2017/6/12.
 */

public class AccountEntityBuilder {

    private AccountEntity entity;

    public AccountEntityBuilder() {
        this.entity = new AccountEntity();
    }

    public void setOauth(String oauth){
        entity.setOauth(oauth);
    }

    public void setAccount(String account){
        entity.setAccount(account);
    }

    public void setPassword(String password){
        entity.setPassword(password);
    }

    public void setUserName(String userName){
        entity.setUserName(userName);
    }

    public void setEmail(String email){
        entity.setEmail(email);
    }

    public void setRemark(String remark){
        entity.setRemark(remark);
    }

    public AccountEntity build(){
        if(entity.isValid())
            return entity;
        else
            throw new RuntimeException(
                    "AccountEntityBuilder an not create AccountEntity with no parameter");
    }

}
