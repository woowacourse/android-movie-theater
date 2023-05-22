package woowacourse.movie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.ReservationItemModel
import woowacourse.movie.data.model.uimodel.ReservationUIModel
import woowacourse.movie.viewholder.ReservationViewHolder

class ReservationAdapter :
    RecyclerView.Adapter<ReservationViewHolder>() {
    private var _reservations: List<ReservationItemModel> = listOf()
    val reservations
        get() = _reservations.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {

        return ReservationViewHolder(parent)
    }

    override fun getItemCount(): Int = reservations.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(reservations[position])
    }

    fun updateReservationItems(
        reservations: List<ReservationUIModel>,
        onClick: (ReservationUIModel) -> Unit
    ) {
        _reservations = reservations.map { reservation ->
            reservation.toItemModel { onClick(reservation) }
        }
    }
}
