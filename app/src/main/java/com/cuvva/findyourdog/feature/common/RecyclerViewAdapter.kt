package com.cuvva.findyourdog.feature.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuvva.findyourdog.BR

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    abstract class ItemViewModel(@LayoutRes val layoutRes: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ItemViewModel) return false

            if (layoutRes != other.layoutRes) return false

            return true
        }

        override fun hashCode(): Int {
            return layoutRes
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(
            this,
            DiffUtilCallback
    )

    val data: List<ItemViewModel>
        get() = listDiffer.currentList

    init {
        hasStableIds()
    }

    fun update(newData: List<ItemViewModel>) {
        listDiffer.submitList(newData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val parentLifecycleOwner =
                DataBindingUtil.findBinding<ViewDataBinding>(parent)?.lifecycleOwner
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
                DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(
                binding,
                parentLifecycleOwner
        )
    }

    override fun getItemViewType(position: Int) = data[position].layoutRes

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position].let(holder::bind)
    }

    class ViewHolder(
            val binding: ViewDataBinding,
            val lifecycleOwner: LifecycleOwner?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ItemViewModel) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.lifecycleOwner = lifecycleOwner
        }
    }
}