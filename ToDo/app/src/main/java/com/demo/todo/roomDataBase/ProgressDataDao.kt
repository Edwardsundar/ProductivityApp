package com.demo.todo.roomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.todo.resorce.Resorce
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDataDao {
    @Query("SELECT * FROM progressData WHERE  `key` = :data ")
    fun getTodayProgressData(data:String = Resorce.date): Flow<ProgressData>
    @Query("SELECT * FROM progressData WHERE  `key` = :date ")
    fun getOldProgressData(date : String) : ProgressData
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressData(progressData: ProgressData)
}