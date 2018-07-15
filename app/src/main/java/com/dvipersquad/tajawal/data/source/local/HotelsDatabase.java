package com.dvipersquad.tajawal.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dvipersquad.tajawal.data.Hotel;

/**
 * The Room Database that contains the Hotel table.
 */
@Database(entities = {Hotel.class}, version = 1)
public abstract class HotelsDatabase extends RoomDatabase {
    public abstract HotelsDao hotelsDao();
}
