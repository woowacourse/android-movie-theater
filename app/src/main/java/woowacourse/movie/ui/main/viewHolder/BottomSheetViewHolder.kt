package woowacourse.movie.ui.main.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.ui.main.itemModel.BottomSheetItemModel
import woowacourse.movie.ui.main.itemModel.ItemModel

class BottomSheetViewHolder(
    view: View,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(view) {
    private val cinemaNameView = view.findViewById<TextView>(R.id.bottom_sheet_cinema_name)
    private val movieCountView = view.findViewById<TextView>(R.id.bottom_sheet_movie_count)
    private val image: ImageView = view.findViewById(R.id.bottom_sheet_arrow)

    init {
        image.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as? BottomSheetItemModel ?: return

        cinemaNameView.text = "%s 극장".format(item.cinema.name)
        movieCountView.text = "%d개의 상영 시간".format(item.cinema.numberOfMovie)
    }
}
