package woowacourse.movie.presentation.view.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ReservationItemBinding
import woowacourse.movie.domain.model.reservation.ReservationMovieInfo

class ReservationListAdapter(
    private var ticketList: List<ReservationMovieInfo>,
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val reservationViewHolder =
            ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(reservationViewHolder)
    }

    override fun getItemCount(): Int = ticketList.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(ticketList[position])
    }
}
