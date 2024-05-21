package com.makeus.daycarat.presentation.recyclerview.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeus.daycarat.data.data.GalleryImage
import com.makeus.daycarat.databinding.ItemGalleryBinding
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick

class GalleryAdapter( var onclick :( (GalleryImage) -> Unit ))  :
    PagingDataAdapter<GalleryImage, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<GalleryImage>() {
        override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean {
            //같은 객체인지 체크합니다
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean {
            //같은 내용물인지 check 합니다
            return oldItem.uri == newItem.uri
        }
    }) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { (holder as ViewHoler).bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHoler(
            ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHoler(val binding:ItemGalleryBinding) :  RecyclerView.ViewHolder(binding.root) {

        fun bind(data : GalleryImage){
            binding.itemImage.onThrottleClick {
                onclick.invoke(data)
            }
            Glide.with(binding.itemImage.context)
                .load(data.uri)
                .into(binding.itemImage)
        }

    }

}