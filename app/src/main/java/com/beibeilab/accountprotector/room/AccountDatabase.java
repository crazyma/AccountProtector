package com.beibeilab.accountprotector.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by david on 2017/6/9.
 */

@Database(entities = {AccountEntity.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase{
    public abstract AccountDao getAccountDao();
}
