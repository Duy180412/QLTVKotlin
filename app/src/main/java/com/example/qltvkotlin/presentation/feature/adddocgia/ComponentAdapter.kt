package com.example.qltvkotlin.presentation.feature.adddocgia

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.closable
import com.example.qltvkotlin.presentation.extension.cast
import java.util.concurrent.atomic.AtomicInteger

class ComponentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    HasCommandCallback,
    Signal.Closable by closable() {
    companion object {
        private val index = AtomicInteger()
        val TYPE_EDITABLE = index.getAndIncrement()
        val TYPE_PHOTO = index.getAndIncrement()
        val TYPE_DIVIDER = index.getAndIncrement()
        val TYPE_DATE = index.getAndIncrement()
    }

    private var mList: List<IComponent>? = null
    override var onCommand: (Command) -> Unit = {}

    @SuppressLint("NotifyDataSetChanged")
    fun submit(it: List<IComponent>?) {
        close()
        it.cast<Signal>()?.bind { notifyDataSetChanged() }
        mList = it
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        close()
    }

    override fun getItemViewType(position: Int): Int {
        return when (val it = mList!![position]) {
            is EditableField -> TYPE_EDITABLE
            is PhotoField -> TYPE_PHOTO
            is DividerComponent -> TYPE_DIVIDER
            is DateField -> TYPE_DATE
            else -> error("Not support ${it.javaClass.name}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_EDITABLE -> EditableViewHolder(parent)
            TYPE_PHOTO -> PhotoViewHolder(parent)
            TYPE_DIVIDER -> DividerViewHolder(parent)
            TYPE_DATE -> DateViewHolder(parent)
            else -> error("Not support type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HasCommandCallback) holder.onCommand = { this.onCommand(it) }
        holder.cast<AutoCloseable>()?.close()
        holder.cast<Bindable<IComponent>>()?.bind(mList!![position])
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        holder.cast<AutoCloseable>()?.close()
        if (holder is HasCommandCallback) holder.onCommand = { }
    }

}
