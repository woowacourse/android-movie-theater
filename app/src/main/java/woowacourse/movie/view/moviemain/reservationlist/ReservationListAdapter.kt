package woowacourse.movie.view.moviemain.reservationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.view.model.ReservationUiModel

class ReservationListAdapter(
    private val reservations: List<ReservationUiModel>,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<ReservationItemViewHolder>() {

    fun interface OnItemClick {
        fun onClick(reservation: ReservationUiModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_item, parent, false)
        return ReservationItemViewHolder(ReservationItemBinding.bind(view), onItemClick)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    override fun onBindViewHolder(holder: ReservationItemViewHolder, position: Int) {
        holder.bind(reservations[position])
    }
}
