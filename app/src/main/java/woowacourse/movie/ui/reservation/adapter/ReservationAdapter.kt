package woowacourse.movie.ui.reservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.uimodel.MovieTicketModel

class ReservationAdapter(
    private val reservationInfo: List<MovieTicketModel>,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        return ReservationViewHolder(parent, onClick)
    }

    override fun getItemCount(): Int = reservationInfo.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.onBind(reservationInfo[position])
    }

    class ReservationViewHolder(
        parent: ViewGroup,
        onClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false),
    ) {
        private val binding = ItemReservationBinding.bind(itemView)

        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
        }

        fun onBind(movieTicketModel: MovieTicketModel) {
            binding.reservation = movieTicketModel
        }
    }
}
