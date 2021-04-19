package com.ditu.coordinatordemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ditu.coordinatordemo.databinding.AdapterNewsItemBinding

/**
 *=======================================
 *
 * Created by Ditu on 4/7/21
 *=======================================
 */
class NewsAdapter(val list: List<String>) : RecyclerView.Adapter<NewsAdapter.VH>(){



    class VH(val binding: AdapterNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(AdapterNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.binding.tvItemText.text = list[position]
    }

    override fun getItemCount(): Int = list.size

}