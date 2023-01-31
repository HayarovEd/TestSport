package com.edurda77.testsport.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.edurda77.testsport.domain.model.Note

class NoteAdapter :
    RecyclerView.Adapter<NoteHolder>() {

    private val diffUtil = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(newList: List<Note>) {
        diffUtil.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {


        val note: Note = diffUtil.currentList[position]
        holder.bind(note)

    }

    override fun getItemCount(): Int = diffUtil.currentList.size

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {

                override fun areItemsTheSame(
                    oldItem: Note,
                    newItem: Note
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Note,
                    newItem: Note
                ): Boolean =
                    oldItem.id == newItem.id
            }
    }
}