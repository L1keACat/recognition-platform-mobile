package com.example.mobileclient

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.model.ItemObject

class CustomAdapter (private val items: List<ItemObject>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val TextView = itemView.findViewById<TextView>(R.id.textViewSmall)
        val TextView2 = itemView.findViewById<TextView>(R.id.textViewLarge)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: CustomAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val item: ItemObject = items[position]
        // Set item views based on your views and data model
        val textView = viewHolder.TextView
        textView.text = item.key
        val textView2 = viewHolder.TextView2
        textView2.text = item.value
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return items.size
    }
}