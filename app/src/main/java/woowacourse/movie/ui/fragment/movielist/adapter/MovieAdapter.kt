package woowacourse.movie.ui.fragment.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.ui.model.AdModel
import woowacourse.movie.ui.model.MovieModel

class MovieAdapter(
    private val movies: List<MovieModel>,
    private val ads: List<AdModel>,
    private val onMovieItemClick: (MovieModel) -> Unit,
    private val onAdItemClick: (AdModel) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    private val onMovieItemViewClick: (Int) -> Unit = { onMovieItemClick(getMovie(it)) }
    private val onAdItemViewClick: (Int) -> Unit = { onAdItemClick(getAd(it)) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val types = MovieListViewType.values()
        return when (types[viewType]) {
            MovieListViewType.MOVIE_VIEW -> {
                val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MovieViewHolder(binding, onMovieItemViewClick)
            }
            MovieListViewType.AD_VIEW -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.advertise_item, parent, false)

                AdViewHolder(view, onAdItemViewClick)
            }
        }
    }

    override fun getItemCount(): Int = movies.size + (movies.size / (AD_INTERVAL - 1))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.bind(getMovie(position))
            is AdViewHolder -> holder.bind(getAd(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isAdTime(position)) return MovieListViewType.AD_VIEW.ordinal
        return MovieListViewType.MOVIE_VIEW.ordinal
    }

    private fun isAdTime(position: Int) = (position + 1) % AD_INTERVAL == 0

    private fun getMovie(position: Int): MovieModel = movies[position - (position / AD_INTERVAL)]

    private fun getAd(position: Int): AdModel {
        if ((position / AD_INTERVAL) >= ads.size) {
            return createDefaultAd()
        }
        return ads[position / AD_INTERVAL]
    }

    private fun createDefaultAd() =
        AdModel(R.drawable.default_ad, "https://developer.android.com/?hl=ko")

    companion object {
        private const val AD_INTERVAL = 4
    }
}
