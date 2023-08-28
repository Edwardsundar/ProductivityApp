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
    fun getTodayProgressData(data:String = Resorce.getTodayDate()): Flow<ProgressData>
    @Query("SELECT * FROM progressData WHERE  `key` = :date ")
    fun getYesterdayProgressData(date : String = Resorce.getYesterdayDate()) : Flow<ProgressData>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgressData(progressData: ProgressData)
    @Query("SELECT EXISTS (SELECT 1 FROM progressData WHERE `key` = :date)")
    fun getById(date : String) : Boolean

    @Query("SELECT * FROM progressData")
    fun getAllData():Flow<List<ProgressData>>

}