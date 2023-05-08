package woowacourse.movie.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerView {
    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun <T> bind(item: T)
    }

    abstract class Adapter<T>(
        internal var onItemClick: (T) -> Unit = {},
    ) : RecyclerView.Adapter<BaseViewHolder>() {
        protected val items: ArrayList<T> = arrayListOf()
        protected val onItemViewClick: (Int) -> Unit = { position -> onItemClick(items[position]) }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        protected fun ViewGroup.inflate(resId: Int, attach: Boolean = false): View =
            layoutInflater().inflate(resId, this, attach)

        protected fun ViewGroup.layoutInflater(): LayoutInflater =
            LayoutInflater.from(context)
    }
}
