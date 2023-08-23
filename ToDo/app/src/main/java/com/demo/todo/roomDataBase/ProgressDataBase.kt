package com.demo.todo.roomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [ProgressData::class],
    version = 1
)
@TypeConverters(TypeConverter::class)
abstract class ProgressDataBase:RoomDatabase() {
    abstract fun progressDao() : ProgressDataDao

    companion object{
        @Volatile
        private var instance : ProgressDataBase? = null

        fun getDataBase(context:Context):ProgressDataBase{
            return instance ?: synchronized(this){
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ProgressDataBase::class.java,
                    "progressDataBase"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}