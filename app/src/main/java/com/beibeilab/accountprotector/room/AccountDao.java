package com.beibeilab.accountprotector.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by david on 2017/6/9.
 */

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
public interface AccountDao {

    @Query("SELECT * FROM accountEntity")
    List<AccountEntity> getAll();

    @Query("SELECT * FROM accountEntity")
    Flowable<List<AccountEntity>> getAllFlowable();

    @Query("SELECT * FROM accountEntity")
    LiveData<List<AccountEntity>> getAllFromLiveData();

    @Insert
    void insert(AccountEntity accountEntity);

    @Delete
    void delete(AccountEntity accountEntity);

}
