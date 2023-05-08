package woowacourse.movie.ui.reservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.uimodel.MovieTicketModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationAdapter(
    private val reservationInfo: List<MovieTicketModel>,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_reservation,
            parent,
            false,
        )

        return ReservationViewHolder(ItemReservationBinding.bind(view), onClick)
    }

    override fun getItemCount(): Int = reservationInfo.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.onBind(reservationInfo[position])
    }

    class ReservationViewHolder(
        private val binding: ItemReservationBinding,
        onClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
        }

        fun onBind(movieTicketModel: MovieTicketModel) {
            binding.reservationDate.text = movieTicketModel.time.dateFormat()
            binding.reservationTime.text = movieTicketModel.time.timeFormat()
            binding.reservationTitle.text = movieTicketModel.title
        }

        private fun LocalDateTime.dateFormat(): String = format(
            DateTimeFormatter.ofPattern(itemView.context.getString(R.string.date_format)),
        )

        private fun LocalDateTime.timeFormat(): String = format(
            DateTimeFormatter.ofPattern(itemView.context.getString(R.string.time_format)),
        )
    }
}
