package woowacourse.movie.main.view.adapter.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.Movie

class MovieViewHolder(binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var thumbnail: Int = 0
    var title: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var runningTime: String = ""
    var movieId: Long = 0L
    lateinit var onReservationButtonClick: (Long) -> Unit

    init {
        binding.movieViewHolder = this
    }

    fun bind(
        movie: Movie,
        onReservationButtonClick: (Long) -> Unit,
    ) {
        thumbnail = movie.thumbnail
        title = movie.title
        startDate = movie.date.startLocalDate.toString()
        endDate = movie.date.endLocalDate.toString()
        runningTime = movie.runningTime.toString()
        movieId = movie.id
        this.onReservationButtonClick = onReservationButtonClick
    }

    fun reservationButtonClick() {
        onReservationButtonClick(movieId)
    }
}
