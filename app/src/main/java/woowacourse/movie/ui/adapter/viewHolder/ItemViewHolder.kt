package woowacourse.movie.ui.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.ui.adapter.itemModel.ItemModel

abstract class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(itemModel: ItemModel)
}
