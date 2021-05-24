package com.example.mobileclient.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.R
import com.example.mobileclient.model.HistoryItem

class HistoryAdapter (private val items: List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val DateTextView = itemView.findViewById<TextView>(R.id.date_textview)
        val ContentTextView = itemView.findViewById<TextView>(R.id.content_textview)
        val IconTextView = itemView.findViewById<TextView>(R.id.icon_textview)
        val FormatTextView = itemView.findViewById<TextView>(R.id.format_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.history_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: HistoryItem = items[position]
        val dateTextView = viewHolder.DateTextView
        dateTextView.text = item.datetime
        val contentTextView = viewHolder.ContentTextView
        contentTextView.text = item.content
        val formatTextView = viewHolder.FormatTextView
        formatTextView.text = item.type_format
        val iconTextView = viewHolder.IconTextView
        if (item.status == "recognized") {
            iconTextView.text = "\u2713"
            iconTextView.setTextColor(Color.parseColor("#00FF00"))
        }
        if (item.status == "decoded") {
            iconTextView.text = "\u26A0"
            iconTextView.setTextColor(Color.parseColor("#FF8C00"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}