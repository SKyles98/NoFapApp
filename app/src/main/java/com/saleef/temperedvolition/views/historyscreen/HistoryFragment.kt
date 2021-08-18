package com.saleef.temperedvolition.views.historyscreen

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.saleef.temperedvolition.views.common.base.BaseFragment
import com.saleef.temperedvolition.HistoryNote
import com.saleef.temperedvolition.HistoryNoteDao
import com.saleef.temperedvolition.SharedPrefs
import kotlinx.coroutines.*

/*
  TODO DATE Time DIsplay is a bit jank need to adjust UI for smaller devices
  TODO Add Night mode (Fix NightModeBug not changing color of popUp menu)
  TODO implement highest Days for the ui
  TODO Start on motivation screen (Probably will just display some motivational quotes)

 */
class HistoryFragment : BaseFragment(),HistoryViewImpl.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var historyNoteDao: HistoryNoteDao
    private lateinit var historyViewImpl: HistoryViewImpl
    private lateinit var sharedPrefs: SharedPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyNoteDao = compositionRoot.historyNoteDao
        sharedPrefs = compositionRoot.sharedPrefs
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyViewImpl = compositionRoot.viewFactory.newHistoryViewImpl(container)
        return historyViewImpl.rootView
    }

    override fun onStart() {
        super.onStart()
        historyViewImpl.registerListener(this)
        bindNotes()
    }


       private fun bindNotes(){
              coroutineScope.launch {
                  try{
                    val result = historyNoteDao.getAllNotes()
                      historyViewImpl.bindDayNight(sharedPrefs.getVisualPref())
                      historyViewImpl.bindNotes(result,sharedPrefs.getVisualPref())
              } finally {
                  Toast.makeText(requireContext(),"Hidden",Toast.LENGTH_LONG).show()
              }
           }

       }
    override fun onStop() {
        super.onStop()
        historyViewImpl.unRegisterListener(this)
    }

    override fun onClearHistoryClicked() {
        coroutineScope.launch {
            try{
                 historyNoteDao.deleteRelapses()

            } finally {
                bindNotes()
                Toast.makeText(requireContext(),"Cleared",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onNoteClicked(historyNote: HistoryNote) {
        Toast.makeText(requireContext(),historyNote.id.toString(), Toast.LENGTH_LONG).show()
    }

}