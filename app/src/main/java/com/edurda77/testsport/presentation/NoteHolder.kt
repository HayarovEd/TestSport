package com.edurda77.testsport.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edurda77.testsport.R
import com.edurda77.testsport.domain.model.Note

class NoteHolder (inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_note, parent, false)) {

    private var titleNote: TextView? = null
    private var contentNote: TextView? = null
    private var dateNote: TextView? = null

    init {
        titleNote = itemView.findViewById(R.id.titleTv)
        contentNote = itemView.findViewById(R.id.contentTv)
        dateNote = itemView.findViewById(R.id.dateTv)

    }
    @SuppressLint("SetTextI18n")
    fun bind(note: Note) {
        titleNote?.text =  note.title
        contentNote?.text = note.content
        dateNote?.text = note.timestamp
    }

}