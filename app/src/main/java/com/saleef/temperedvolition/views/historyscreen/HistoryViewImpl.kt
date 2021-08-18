package com.saleef.temperedvolition.views.historyscreen


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.saleef.temperedvolition.HistoryNote
import com.saleef.temperedvolition.MainActivity
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.views.common.viewmvc.BaseViewMvc
//2:01
class HistoryViewImpl(private val layoutInflater: LayoutInflater,private val viewGroup: ViewGroup?)
    : BaseViewMvc<HistoryViewImpl.Listener>(layoutInflater,viewGroup, R.layout.history_screen) {

    interface Listener{
        fun onClearHistoryClicked()
        fun onNoteClicked(historyNote: HistoryNote)
    }


    private val toolbar:Toolbar
    private val backGround:RelativeLayout
    private val recyclerView: RecyclerView
    private val  adapter: HistoryRecyclerAdapter
    init {
        backGround = findViewById(R.id.historyBackGround)
        toolbar = findViewById(R.id.toolBar)
        toolbar.title = "History"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(context.resources.getColor(R.color.white, context.theme))
        } else{
            toolbar.setTitleTextColor(ContextCompat.getColor(context,R.color.white))
        }
        toolbar.inflateMenu(R.menu.settings)
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

      fun bindDayNight(status:Int){
          if (status==MainActivity.OnDayNightStateChanged.NIGHT){
              toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDarkNight))
              backGround.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDarkNight))
          } else{
              toolbar.setBackgroundColor(ContextCompat.getColor(context,R.color.blue))
              backGround.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
          }
      }

       fun bindNotes(historyNotes: List<HistoryNote>,status: Int){
           adapter.bindData(historyNotes,status)
       }



  class HistoryRecyclerAdapter(private val clickListener: (HistoryNote) -> Unit ) : RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder>() {



         private var notes:List<HistoryNote> = ArrayList(0)
         private var dayNight:Int = 1
       inner class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
             val startDate: TextView = view.findViewById(R.id.startDateTxt)
           val endDate: TextView = view.findViewById(R.id.endDateTxt)
           val streak: TextView = view.findViewById(R.id.streakTxt)
           val note: TextView = view.findViewById(R.id.noteTxt)
           val background:ConstraintLayout = view.findViewById(R.id.cardBackGround)
           val cardBackGround:MaterialCardView = view.findViewById(R.id.cardViewBackGround)
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
          if (dayNight == MainActivity.OnDayNightStateChanged.NIGHT){
              holder.startDate.setTextColor(ContextCompat.getColor(holder.startDate.context,R.color.white))
              holder.endDate.setTextColor(ContextCompat.getColor(holder.endDate.context,R.color.white))
              holder.streak.setTextColor(ContextCompat.getColor(holder.streak.context,R.color.white))
              holder.note.setTextColor(ContextCompat.getColor(holder.note.context,R.color.white))
              holder.background.setBackgroundColor(ContextCompat.getColor(holder.background.context,R.color.light_purple))
              holder.cardBackGround.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#9575CD"))
          } else{
              holder.startDate.setTextColor(ContextCompat.getColor(holder.startDate.context,R.color.black))
              holder.endDate.setTextColor(ContextCompat.getColor(holder.endDate.context,R.color.black))
              holder.streak.setTextColor(ContextCompat.getColor(holder.streak.context,R.color.black))
              holder.note.setTextColor(ContextCompat.getColor(holder.note.context,R.color.black))
              holder.background.setBackgroundColor(ContextCompat.getColor(holder.background.context,R.color.error_red))
              holder.cardBackGround.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#B00020"))
          }
          // Invoke the unit which is history when the item view is clicked
          // Then the corresponding object has its lambda function that we passed in invoked
          holder.itemView.setOnClickListener { clickListener.invoke(notes[position]) }

      }

      override fun getItemCount(): Int {
        return notes.size
      }


      fun bindData(historyNotes:List<HistoryNote>,visualStatus:Int){
          notes = historyNotes
          dayNight = visualStatus
          notifyDataSetChanged()
      }
  }


}