package woowacourse.movie.feature.common.viewHolder

import android.view.View
import android.widget.ImageView
import woowacourse.movie.R
import woowacourse.movie.feature.common.itemModel.AdvItemModel
import woowacourse.movie.feature.common.itemModel.ItemModel

class AdvViewHolder(
    itemView: View
) : ItemViewHolder(itemView) {
    private val image: ImageView

    init {
        image = itemView.findViewById(R.id.adv_img)
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as AdvItemModel
        image.setImageResource(item.advState.imgId)
        image.setOnClickListener { item.onClick(bindingAdapterPosition) }
    }
}
