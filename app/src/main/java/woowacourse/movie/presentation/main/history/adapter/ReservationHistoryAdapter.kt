package woowacourse.movie.presentation.main.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieTicketItemBinding
import woowacourse.movie.domain.model.movieticket.MovieTicket

class ReservationHistoryAdapter(
    private val movieTickets: List<MovieTicket>,
    private val onMovieTicketItemClickListener: OnMovieTicketItemClickListener,
) : RecyclerView.Adapter<MovieTicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTicketViewHolder {
        val binding = MovieTicketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieTicketViewHolder, position: Int) {
        val movieTicket = movieTickets[position]
        holder.bind(movieTicket, onMovieTicketItemClickListener)
    }

    override fun getItemCount(): Int = movieTickets.size
}
