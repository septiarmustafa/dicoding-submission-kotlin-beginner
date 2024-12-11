package com.septiar.news_on

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListNewsAdapter(private val listNews: ArrayList<News>) :
    RecyclerView.Adapter<ListNewsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, overview, description, description2, image, date, link) = listNews[position]
        holder.tvTitle.text = name
        holder.tvOverview.text = overview
        holder.tvDate.text = date
        Glide.with(holder.itemView.context)
            .load(image)
            .into(holder.imgNews)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listNews[holder.adapterPosition]) }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgNews: ImageView = itemView.findViewById(R.id.img_item)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvOverview: TextView = itemView.findViewById(R.id.tv_item_overview)
        val tvDate: TextView = itemView.findViewById(R.id.tv_item_date)
    }

}