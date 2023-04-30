package woowacourse.movie.feature.common.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.feature.common.itemModel.ItemModel

abstract class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(itemModel: ItemModel)
}
