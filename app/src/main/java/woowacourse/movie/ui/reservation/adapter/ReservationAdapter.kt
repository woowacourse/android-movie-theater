package woowacourse.movie.ui.reservation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.MovieTicketModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationAdapter(
    private val reservationInfo: List<MovieTicketModel>, private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_reservation, parent, false
        )

        return ReservationViewHolder(view, onClick)
    }

    override fun getItemCount(): Int = reservationInfo.size

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.onBind(reservationInfo[position])
    }

    class ReservationViewHolder(
        itemView: View,
        onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val dateView: TextView by lazy { itemView.findViewById(R.id.reservation_date) }
        private val timeView: TextView by lazy { itemView.findViewById(R.id.reservation_time) }
        private val titleView: TextView by lazy { itemView.findViewById(R.id.reservation_title) }

        init {
            itemView.setOnClickListener { onClick(adapterPosition) }
        }

        fun onBind(movieTicketModel: MovieTicketModel) {
            dateView.text = movieTicketModel.time.dateFormat()
            timeView.text = movieTicketModel.time.timeFormat()
            titleView.text = movieTicketModel.title
        }

        private fun LocalDateTime.dateFormat(): String = format(
            DateTimeFormatter.ofPattern(itemView.context.getString(R.string.date_format))
        )

        private fun LocalDateTime.timeFormat(): String = format(
            DateTimeFormatter.ofPattern(itemView.context.getString(R.string.time_format))
        )
    }
}