package com.e.roomdatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

//add database entities
@Database(entities ={MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //create database instance
    private static RoomDB database;
    //Define database name
    private static String DATABASE_NAME="database";

    public synchronized static RoomDB getInstance(Context context){
        //check condition
        if(database==null)
        {
            //when databaseis null
            //intialize database
            database = Room.databaseBuilder(context.getApplicationContext()
            ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        //return database
        return database;

    }

    //create Dao
    public abstract MainDao mainDao();

}
