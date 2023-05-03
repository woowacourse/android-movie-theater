package woowacourse.movie.ui.main.viewHolder

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemCinemaBottomSheetBinding
import woowacourse.movie.ui.main.itemModel.BottomSheetItemModel
import woowacourse.movie.ui.main.itemModel.ItemModel

class CinemaListViewHolder(
    private val binding: ItemCinemaBottomSheetBinding,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(binding.root) {
    init {
        binding.bottomSheetArrow.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as? BottomSheetItemModel ?: return
        binding.cinema = item.cinema
        setVisibility(item.cinema.numberOfMovie > 0)
    }

    private fun setVisibility(isVisible: Boolean) {
        val param = itemView.layoutParams as RecyclerView.LayoutParams
        if (isVisible) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT
            param.width = LinearLayout.LayoutParams.MATCH_PARENT
            itemView.visibility = View.VISIBLE
        } else {
            param.height = 0
            param.width = 0
            itemView.visibility = View.GONE
        }
        itemView.layoutParams = param
    }
}
