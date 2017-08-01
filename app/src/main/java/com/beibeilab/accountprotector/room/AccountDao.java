package com.beibeilab.accountprotector.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by david on 2017/6/9.
 */

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
public interface AccountDao {

    @Query("SELECT account FROM accountEntity "
            + "WHERE account LIKE :account")
    List<String> getAccountEntityByAccount(String account);

    @Query("SELECT DISTINCT account FROM accountEntity "
            + "WHERE account IS NOT NULL")
    List<String> getDistinctAccountEntity();

    @Query("SELECT * FROM accountEntity")
    List<AccountEntity> getAll();

    @Query("SELECT * FROM accountEntity")
    Flowable<List<AccountEntity>> getAllFlowable();

    @Query("SELECT * FROM accountEntity ORDER BY service_name ASC")
    LiveData<List<AccountEntity>> getAllFromLiveData();

    @Query("SELECT * FROM accountEntity WHERE uid == :uid")
    Flowable<AccountEntity> getAccountEntityByUidRx(long uid);

    @Query("SELECT * FROM accountEntity WHERE uid == :uid")
    LiveData<AccountEntity> getAccountEntityByUid(long uid);

    @Insert
    void insert(AccountEntity accountEntity);

    @Update
    void update(AccountEntity accountEntity);

    @Delete
    void delete(AccountEntity accountEntity);

}
