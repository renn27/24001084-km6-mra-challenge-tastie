package com.rendi.foodorderapp.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.refood.tastie.core.ViewHolderBinder
import com.refood.tastie.data.model.Menu
import com.refood.tastie.databinding.ItemMenuBinding
import com.refood.tastie.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val binding: ItemMenuBinding,
    private val listener: OnItemClickedListener<Menu>,
) : ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivCatalogImage.load(it.imagePictUrl) {
                crossfade(true)
            }
            binding.tvCatalogName.text = it.name
            binding.tvCatalogPrice.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}
