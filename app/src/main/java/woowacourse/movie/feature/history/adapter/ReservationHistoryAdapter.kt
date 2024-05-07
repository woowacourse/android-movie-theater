package woowacourse.movie.feature.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.databinding.ItemReservationHistoryBinding

typealias ReservationHistoryItemClickListener = (Ticket) -> Unit

class ReservationHistoryAdapter(
    private val onReservationHistoryItemClickListener: ReservationHistoryItemClickListener,
) :
    ListAdapter<Ticket, ReservationHistoryViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReservationHistoryBinding.inflate(inflater, parent, false)
        return ReservationHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReservationHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), onReservationHistoryItemClickListener)
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<Ticket>() {
                override fun areItemsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Ticket,
                    newItem: Ticket,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}

class ReservationHistoryViewHolder(
    private val binding: ItemReservationHistoryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        ticket: Ticket,
        onReservationHistoryItemClickListener: ReservationHistoryItemClickListener,
    ) {
        binding.tvMovieTitle.text = MovieRepository.getMovieById(ticket.movieId).title
        binding.tvScreeningDate.text = ticket.screeningDate.toString()
        binding.tvScreeningTime.text = ticket.screeningTime.toString()
        binding.tvTheaterName.text = "${ticket.theaterName} 극장"
        binding.root.setOnClickListener {
            onReservationHistoryItemClickListener(ticket)
        }
    }
}
