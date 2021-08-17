package com.saleef.temperedvolition

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relapse_notes")
data class HistoryNote
    (@PrimaryKey(autoGenerate = true) val id:Int, val streakStarted:String, val streakEnded:String, val streakDuration:Int, val failureNote:String)


