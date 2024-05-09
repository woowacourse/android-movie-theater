package woowacourse.movie.presentation.main.home.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.home.Theater

class TheaterAdapter(
    private val movieId: Long,
    private val theaters: List<Theater>,
    private val onTheaterItemClickListener: OnTheaterItemClickListener
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val binding = TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        val theater = theaters[position]
        holder.bind(movieId, theater, onTheaterItemClickListener)
    }

    override fun getItemCount(): Int = theaters.size

    class TheaterViewHolder(private val binding: TheaterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movieId: Long,
            theater: Theater,
            onTheaterItemClickListener: OnTheaterItemClickListener,
        ) {
            binding.movieId = movieId
            binding.theater = theater
            binding.onTheaterItemClickListener = onTheaterItemClickListener
            binding.executePendingBindings()
        }
    }
}
