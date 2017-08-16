package com.beibeilab.accountprotector.room2;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by david on 2017/8/16.
 */

@Database(entities = {AccountEntity.class}, version = 2)
public abstract class AccountDatabase extends RoomDatabase {

    private volatile static AccountDatabase instance;

    public static AccountDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AccountDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class, "db-account")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE AccountEntity "
                    + " ADD COLUMN encrypted_pwd TEXT");
        }
    };

    public abstract AccountDao getAccountDao();

}
