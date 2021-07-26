package com.example.booksearch.settingsFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearch.R
import com.example.booksearch.network.services.BookData

class SettingsAdapter(
    private val items: List<SettingsData>,
    val clickListener: (SettingsData) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    private var selectedItem: SettingsData? = null

    init {
        items.forEach { item ->
            if (item.selected) {
                selectedItem = item
                return@forEach
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.settings_item_view, parent, false)
        return SettingsViewHolder(view)
    }

    fun updateSelectedItem(newSelectedItem: SettingsData) {
        selectedItem?.selected = false
        selectedItem = newSelectedItem
        selectedItem?.selected = true

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val item: SettingsData = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class SettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(data: SettingsData) {
            val textView = itemView.findViewById<TextView>(R.id.settingsTextView)
            val checkImage = itemView.findViewById<ImageView>(R.id.checkImage)

            checkImage.visibility = if (data.selected) View.VISIBLE else View.GONE
            textView.text = data.type.toString()

            itemView.setOnClickListener {
                clickListener.invoke(data)
            }
        }
    }
}