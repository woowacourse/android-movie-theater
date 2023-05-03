package woowacourse.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemCinemaBottomSheetBinding
import woowacourse.movie.ui.itemModel.CinemaItemModel
import woowacourse.movie.ui.itemModel.ItemModel
import woowacourse.movie.ui.viewHolder.CinemaListViewHolder
import woowacourse.movie.ui.viewHolder.ItemViewHolder

class CinemaListAdapter(
    cinemas: List<ItemModel>,
    val onArrowButtonClick: (cinema: CinemaItemModel) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var cinemas = cinemas.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ItemCinemaBottomSheetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CinemaListViewHolder(itemBinding) { position ->
            val cinemaItemModel = cinemas[position] as CinemaItemModel
            onArrowButtonClick(cinemaItemModel)
        }
    }

    override fun getItemCount(): Int = cinemas.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(cinemas[position])
    }
}
