package com.saleef.temperedvolition.views


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saleef.temperedvolition.HistoryNote
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.views.common.viewmvc.BaseViewMvc
//2:01
class HistoryViewImpl(private val layoutInflater: LayoutInflater,private val viewGroup: ViewGroup?)
    : BaseViewMvc<HistoryViewImpl.Listener>(layoutInflater,viewGroup, R.layout.history_screen) {

    interface Listener{
        fun onClearHistoryClicked()
        fun onNoteClicked(historyNote: HistoryNote)
    }


    private lateinit var toolbar:Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:HistoryRecyclerAdapter
    init {
        toolbar = findViewById(R.id.toolBar)
        toolbar.title = "History"
        toolbar.menu.findItem(R.id.settings).isVisible = false
        toolbar.setOnMenuItemClickListener{
            when (it.itemId){
                R.id.history -> {
                    for (listener in listeners)
                        listener.onClearHistoryClicked()
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

        // Init RecyclerView
        recyclerView = findViewById(R.id.historyRecycler)
         adapter = HistoryRecyclerAdapter{ // It just works :}
            historyNote -> // passes a history note with a method????
            for (listener in listeners) listener.onNoteClicked(historyNote)

        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

       fun bindNotes(historyNotes: List<HistoryNote>){
           adapter.bindData(historyNotes)
       }



  class HistoryRecyclerAdapter(private val clickListener: (HistoryNote) -> Unit ) : RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder>() {



         private var notes:List<HistoryNote> = ArrayList(0)

       inner class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
             val startDate: TextView = view.findViewById(R.id.startDateTxt)
           val endDate: TextView = view.findViewById(R.id.endDateTxt)
           val streak: TextView = view.findViewById(R.id.streakTxt)
           val note: TextView = view.findViewById(R.id.noteTxt)
      }

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
          val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.history_viewholder,parent,false)
          return HistoryViewHolder(itemView)
      }

      override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
          holder.startDate.text = notes[position].streakStarted
          holder.endDate.text = notes[position].streakEnded
          holder.streak.text = notes[position].streakDuration.toString()
          holder.note.text = notes[position].failureNote
          // Invoke the unit which is history when the item view is clicked
          // Then the corresponding object has its lambda function that we passed in invoked
          holder.itemView.setOnClickListener { clickListener.invoke(notes[position]) }


      }

      override fun getItemCount(): Int {
        return notes.size
      }


      fun bindData(historyNotes:List<HistoryNote>){
          notes = historyNotes
          notifyDataSetChanged()
      }
  }


}