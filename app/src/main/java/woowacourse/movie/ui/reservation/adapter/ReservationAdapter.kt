package woowacourse.movie.ui.reservation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemReservationBinding
import woowacourse.movie.model.MovieTicketModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationAdapter(
    private val reservationInfo: List<MovieTicketModel>,
    private val onClick: (MovieTicketModel) -> Unit,
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ItemReservationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )

        return ReservationViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = reservationInfo.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.onBind(reservationInfo[position])
    }

    class ReservationViewHolder(
        private val binding: ItemReservationBinding,
        private val onClick: (MovieTicketModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(movieTicketModel: MovieTicketModel) {
            with(binding) {
                root.setOnClickListener { onClick(movieTicketModel) }
                reservationTitle.text = movieTicketModel.title
                reservationTime.text = movieTicketModel.time.toTimeFormat()
                reservationTime.text = movieTicketModel.time.toDateFormat()
            }
        }

        private fun LocalDateTime.toDateFormat(): String = format(
            DateTimeFormatter.ofPattern(itemView.context.getString(R.string.date_format)),
        )

        private fun LocalDateTime.toTimeFormat(): String = format(
            DateTimeFormatter.ofPattern(itemView.context.getString(R.string.time_format)),
        )
    }
}
