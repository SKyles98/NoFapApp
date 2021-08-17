package com.saleef.temperedvolition

import androidx.room.*

@Dao // Responsible for performing CRUD operations on our database
interface HistoryNoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun addNote(note: HistoryNote)


    @Query("SELECT * FROM relapse_notes ORDER BY ID ASC")
      suspend  fun getAllNotes():List<HistoryNote>


     @Query("DELETE FROM relapse_notes")
      suspend fun deleteRelapses()


}