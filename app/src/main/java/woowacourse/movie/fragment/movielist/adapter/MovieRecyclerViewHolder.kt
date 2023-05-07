package woowacourse.movie.fragment.movielist.adapter

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.DateFormatter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieListBinding
import woowacourse.movie.model.MovieUIModel

class MovieRecyclerViewHolder(
    private val binding: ItemMovieListBinding,
    private val listener: (Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private val poster: ImageView = binding.ivMoviePoster
    private val title: TextView = binding.tvMovieTitle
    private val screeningPeriod: TextView = binding.tvMovieScreeningPeriod
    private val runningTime: TextView = binding.tvMovieRunningTime
    private val bookButton: Button = binding.btBookNow

    init {
        bookButton.setOnClickListener {
            listener(bindingAdapterPosition)
        }
    }

    fun bind(movieData: MovieUIModel) {
        poster.setImageResource(movieData.poster)
        title.text = movieData.title
        screeningPeriod.text = binding.root.context.getString(
            R.string.movie_screening_period,
            DateFormatter.format(movieData.startDate),
            DateFormatter.format(movieData.endDate)
        )
        runningTime.text =
            binding.root.context.getString(R.string.movie_running_time, movieData.runningTime)
    }
}
