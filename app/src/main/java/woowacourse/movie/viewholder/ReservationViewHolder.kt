package woowacourse.movie.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.model.itemmodel.ReservationItemModel
import woowacourse.movie.databinding.ItemReservationBinding

class ReservationViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
) {
    private val binding = ItemReservationBinding.bind(itemView)

    fun bind(item: ReservationItemModel) {
        binding.reservations = item
    }
}
