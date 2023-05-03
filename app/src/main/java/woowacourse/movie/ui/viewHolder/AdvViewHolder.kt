package woowacourse.movie.ui.viewHolder

import android.view.View
import android.widget.ImageView
import woowacourse.movie.R
import woowacourse.movie.ui.itemModel.AdvItemModel
import woowacourse.movie.ui.itemModel.ItemModel

class AdvViewHolder(
    itemView: View,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(itemView) {
    private val image: ImageView

    init {
        image = itemView.findViewById(R.id.adv_img)
        image.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as AdvItemModel
        image.setImageResource(item.advState.imgId)
    }
}
