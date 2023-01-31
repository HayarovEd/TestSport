package com.edurda77.testsport.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.edurda77.testsport.R
import com.edurda77.testsport.data.mapper.convertToString
import com.edurda77.testsport.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class DialogAddNote : DialogFragment() {
    private val viewModel by viewModels<DialogAddNoteViewModel>()
    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.dialog_view,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title_ev)
        content = view.findViewById(R.id.content_ev)
        okButton = view.findViewById(R.id.ok_bt)
        cancelButton = view.findViewById(R.id.cancel_bt)
        okButton.setOnClickListener {

            val hp = title.text
            val lp = content.text
            viewModel.addNote(
                Note(
                    id = 0,
                    title = hp.toString(),
                    content = lp.toString(),
                    timestamp = LocalDateTime.now().convertToString()
                )
            )

            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}