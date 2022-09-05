package com.example.probodia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.probodia.R
import com.example.probodia.data.remote.model.ApiItemName
import com.example.probodia.databinding.ItemSearchBinding

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v : View, position : Int)
    }

    var clickListener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        clickListener = listener
    }

    var dataSet : MutableList<ApiItemName> = mutableListOf()

    fun addDataSet(newDataSet : MutableList<ApiItemName>) {
        dataSet.addAll(newDataSet)
    }

    fun resetDataSet() {
        dataSet = mutableListOf()
    }

    fun getItem(position : Int): ApiItemName {
        return dataSet.get(position)
    }

    inner class ViewHolder(val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (clickListener != null) {
                        clickListener!!.onItemClick(it, position)
                    }
                }
            }
        }

        fun bind(item : ApiItemName) {
            binding.itemName.text = item.itemName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = dataSet.size
}