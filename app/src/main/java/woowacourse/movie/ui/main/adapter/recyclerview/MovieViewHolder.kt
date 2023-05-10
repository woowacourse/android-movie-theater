package woowacourse.movie.ui.main.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.model.main.MainData
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ui.main.adapter.MainViewType
import woowacourse.movie.util.formatScreenDate

class MovieViewHolder(
    binding: MovieListItemBinding
) : MainViewHolder<MovieListItemBinding>(binding) {

    override val mainViewType: MainViewType = MainViewType.CONTENT
    private lateinit var movie: MovieUiModel

    override fun onBind(data: MainData) {
        movie = data as MovieUiModel

        with(binding) {
            imageItemThumbnail.setImageResource(movie.thumbnail)
            textItemTitle.text = movie.title
            textBookingScreeningDate.text = root.context
                .getString(
                    R.string.screening_date,
                    movie.startDate.formatScreenDate(),
                    movie.endDate.formatScreenDate()
                )
            textBookingRunningTime.text = root.context
                .getString(
                    R.string.running_time,
                    movie.runningTime
                )
        }
    }

    fun setBookingClick(clickBook: (Long) -> Unit) {
        binding.buttonItemBook.setOnClickListener {
            clickBook(movie.id)
        }
    }

    companion object {

        fun from(parent: ViewGroup): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MovieListItemBinding.inflate(layoutInflater, parent, false)

            return MovieViewHolder(binding)
        }
    }
}
