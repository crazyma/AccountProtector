package com.beibeilab.accountprotector.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by david on 2017/6/9.
 */

@Database(entities = {AccountEntity.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase{

    private volatile static AccountDatabase instance;

    public static AccountDatabase getInstance(Context context){
        if(instance == null) {
            synchronized (AccountDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class, "db-account").build();
                }
            }
        }
        return instance;
    }

    public abstract AccountDao getAccountDao();
}
