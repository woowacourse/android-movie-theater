package woowacourse.movie.presentation.view.main.home

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.view.main.home.moviedetail.MovieDetailActivity

class MovieListViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    private val context = rootView.context
    private val ivMoviePoster = rootView.findViewById<ImageView>(R.id.iv_movie_poster)
    private val tvMovieTitle = rootView.findViewById<TextView>(R.id.tv_movie_title)
    private val tvMovieReleaseDate = rootView.findViewById<TextView>(R.id.tv_movie_release_date)
    private val tvMovieRunningTime = rootView.findViewById<TextView>(R.id.tv_movie_running_time)
    private val btBookNow = rootView.findViewById<Button>(R.id.bt_book_now)
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
