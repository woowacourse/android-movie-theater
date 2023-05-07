package woowacourse.movie.view.reservationList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.MovieTicketModel

class ReservationAdapter(
    private val items: List<MovieTicketModel>,
    private val onReservationItemClick: (MovieTicketModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val onReservationItemViewClick: (Int) -> Unit = { onReservationItemClick(items[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reservation_item, parent, false)
        return ReservationViewHolder(view, onReservationItemViewClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReservationViewHolder -> holder.bind(items[position])
        }
    }
}
