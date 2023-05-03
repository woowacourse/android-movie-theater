package woowacourse.movie.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.main.itemModel.ItemModel
import woowacourse.movie.ui.main.viewHolder.BottomSheetViewHolder
import woowacourse.movie.ui.main.viewHolder.ItemViewHolder

class CinemaListAdapter(
    cinemas: List<ItemModel>,
    val onArrowButtonClick: (itemModel: ItemModel) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var cinemas = cinemas.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cinema_bottom_sheet, parent, false)
        return BottomSheetViewHolder(itemView) { position ->
            onArrowButtonClick(cinemas[position])
        }
    }

    override fun getItemCount(): Int = cinemas.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(cinemas[position])
    }
}
