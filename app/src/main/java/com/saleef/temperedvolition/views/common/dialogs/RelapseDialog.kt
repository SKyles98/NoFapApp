package com.saleef.temperedvolition.views.common.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.saleef.temperedvolition.R
import com.saleef.temperedvolition.common.constants.Constants
import com.saleef.temperedvolition.views.common.base.BaseDialog

class RelapseDialog : BaseDialog() {

    companion object{
        fun newInstance():RelapseDialog{
            return RelapseDialog()
        }

    }

    private lateinit var noteEdit:EditText
    private lateinit var btnYes:MaterialButton
    private lateinit var btnNo:MaterialButton

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.relapse_dialog)

        noteEdit = dialog.findViewById(R.id.reasonEdt)
        btnYes = dialog.findViewById(R.id.yesBtn)
        btnNo = dialog.findViewById(R.id.noBtn)
        btnNo.setOnClickListener{dismiss()}
        btnYes.setOnClickListener{
            addNote()
            dismiss()
        }
        return dialog
    }

    fun addNote(){
        val bundle = Bundle()
        bundle.putString(Constants.NOTEKEY,noteEdit.text.toString())
        parentFragmentManager.setFragmentResult(Constants.REQUESTKEY,bundle)

    }
}