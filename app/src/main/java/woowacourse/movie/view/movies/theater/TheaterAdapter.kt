package woowacourse.movie.view.movies.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.MovieScreeningInfoByTheater

class TheaterAdapter(
    private val movieScreeningInfoByTheaters: List<MovieScreeningInfoByTheater>,
    private val onTheaterClick: (MovieScreeningInfoByTheater) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val theaterBinding = ItemTheaterBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(theaterBinding, onTheaterClick)
    }

    override fun getItemCount(): Int = movieScreeningInfoByTheaters.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(movieScreeningInfoByTheaters[position])
    }
}
