package com.surajpal.a1_dictionary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.surajpal.a1_dictionary.model.WordResult

@Database(entities = [WordResult::class], version = 2)
@TypeConverters(MeaningListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordResultDao(): WordResultDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase?=null
                fun getDatabase (context: Context) : AppDatabase {
                    if(INSTANCE == null) {
                        synchronized(this){
                            INSTANCE= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,
                                "word_results").build()
                        }

                    }
                    return INSTANCE!!
                }
    }
}