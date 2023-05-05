package woowacourse.movie.ui.fragment.movielist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.ui.model.MovieModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieViewHolder(view: View, private val onItemViewClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding: MovieItemBinding

    init {
        binding = MovieItemBinding.bind(view)
        view.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
        binding.itemBookingButton.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
    }

    fun bind(movie: MovieModel) {
        binding.itemPoster.setImageResource(movie.poster)
        binding.itemTitle.text = movie.title
        binding.itemDate.text = movie.getScreenDate()
        binding.itemRunningTime.text = movie.getRunningTime()
    }

    private fun MovieModel.getScreenDate(): String =
        binding.itemDate.context.getString(R.string.screen_date, startDate.format(), endDate.format())

    private fun LocalDate.format(): String =
        format(DateTimeFormatter.ofPattern(binding.itemDate.context.getString(R.string.date_format)))

    private fun MovieModel.getRunningTime(): String =
        binding.itemRunningTime.context.getString(R.string.running_time, runningTime)
}
