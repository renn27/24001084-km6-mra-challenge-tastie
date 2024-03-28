package com.refood.tastie.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.refood.tastie.data.model.Category
import com.refood.tastie.databinding.ItemCategoryMenuBinding
import com.refood.tastie.base.ViewHolderBinder

class CategoryListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    fun submitData(data: List<Category>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            ItemCategoryMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Category>).bind(asyncDataDiffer.currentList[position])
    }

    class CategoryViewHolder(private val binding: ItemCategoryMenuBinding) :
        RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
        override fun bind(item: Category) {
            item.let {
                binding.tvCategoryName.text = it.name
                binding.ivCategoryImage.load(it.imageUrl) {
                    crossfade(true)
                }
            }
        }
    }
}