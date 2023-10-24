package org.techtales.notes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.techtales.notes.MainActivity
import org.techtales.notes.Models.Notes
import org.techtales.notes.R
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: MainActivity):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NoteList = ArrayList<Notes>()
    private val fullList = ArrayList<Notes>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(

            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    fun updateList(newList: List<Notes>){

        fullList.clear()
        fullList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String){

        NoteList.clear()

        for (item in fullList){

            if(item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true ){
                NoteList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun randomColor(): Int{

        val list = ArrayList<Int>()
        list.add(R.color.Aqua)
        list.add(R.color.DarkCyan)
        list.add(R.color.DarkSlateGray)
        list.add(R.color.ForestGreen)
        list.add(R.color.SeaGreen)
        list.add(R.color.Ivory)
        list.add(R.color.SteelBlue)
        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NoteList[position]
        holder.title.text =currentNote.title
        holder.title.isSelected = true
        holder.note.text =currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))

        holder.notes_layout.setOnClickListener{

            listener.onitemClicked(NoteList[holder.adapterPosition])
        }

        holder.notes_layout.setOnClickListener{

            listener.onlongitemClicked(NoteList[holder.adapterPosition],holder.notes_layout)
            true
        }
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)

    }

    interface NotesClickListener{

        fun onitemClicked(note:Notes)
        fun onlongitemClicked(note:Notes,cardView: CardView)
    }
}