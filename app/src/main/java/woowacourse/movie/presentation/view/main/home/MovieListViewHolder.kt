package woowacourse.movie.presentation.view.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.view.main.home.moviedetail.MovieDetailActivity

class MovieListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_advertise, parent, false)
) {
    private val context = parent.context
    private val ivMoviePoster = itemView.findViewById<ImageView>(R.id.iv_movie_poster)
    private val tvMovieTitle = itemView.findViewById<TextView>(R.id.tv_movie_title)
    private val tvMovieReleaseDate = itemView.findViewById<TextView>(R.id.tv_movie_release_date)
    private val tvMovieRunningTime = itemView.findViewById<TextView>(R.id.tv_movie_running_time)
    private val btBookNow = itemView.findViewById<Button>(R.id.bt_book_now)

    fun bind(movie: Movie) {
        ivMoviePoster.setImageResource(movie.poster)
        tvMovieTitle.text = movie.title
        tvMovieReleaseDate.text = movie.releaseDate
        tvMovieRunningTime.text = movie.runningTime
        btBookNow.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.MOVIE_DATA_INTENT_KEY, movie)
            context.startActivity(intent)
        }
    }
}
