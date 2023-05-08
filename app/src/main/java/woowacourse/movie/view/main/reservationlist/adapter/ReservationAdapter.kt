package woowacourse.movie.view.main.reservationlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.view.main.reservationlist.viewholder.ReservationViewHolder

class ReservationAdapter(
    private val reservations: List<ReservationUiModel>,
    private val onClickEvent: (ReservationUiModel) -> Unit
) :
    RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = DataBindingUtil.inflate<ItemReservationBinding>(
            LayoutInflater.from(parent.context), R.layout.item_reservation, parent, false
        )
        return ReservationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(reservations[position], onClickEvent)
    }
}
