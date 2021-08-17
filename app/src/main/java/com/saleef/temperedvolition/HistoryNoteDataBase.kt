package com.saleef.temperedvolition

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryNote::class],version = 1,exportSchema = false)
abstract class HistoryNoteDataBase: RoomDatabase() {


  abstract fun getHistoryNoteDao():HistoryNoteDao


      companion object{ // Singleton initialization of database
          @Volatile
          private var INSTANCE:HistoryNoteDataBase? = null

          fun getDataBase(context: Context):HistoryNoteDataBase{
              val tempInstance = INSTANCE
              if (tempInstance!=null){
                  return tempInstance
              }
              synchronized(this){
                  val instance = Room.databaseBuilder(context.applicationContext
                      ,HistoryNoteDataBase::class.java,
                      "relapse_notes").build()
                  INSTANCE = instance
                  return instance
              }

          }
      }
}