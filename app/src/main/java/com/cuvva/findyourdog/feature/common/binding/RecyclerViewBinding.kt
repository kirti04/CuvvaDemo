package com.cuvva.findyourdog.feature.common.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.cuvva.findyourdog.feature.common.RecyclerViewAdapter

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("items")
    fun setItems(
        view: RecyclerView,
        oldItems: List<RecyclerViewAdapter.ItemViewModel>?,
        newItems: List<RecyclerViewAdapter.ItemViewModel>?
    ) {
        if (newItems == null || newItems == oldItems)
            return

        view.apply {
            if (adapter == null)
                adapter = RecyclerViewAdapter()

            layoutManager = LinearLayoutManager(this.context)
            (adapter as? RecyclerViewAdapter)?.update(newItems)
            (view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }
}

