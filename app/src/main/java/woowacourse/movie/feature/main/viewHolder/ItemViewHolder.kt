package woowacourse.movie.feature.main.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.main.itemModel.ItemModel

abstract class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(itemModel: ItemModel)
}
