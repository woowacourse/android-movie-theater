package woowacourse.movie.home.view.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.home.view.listener.MovieHomeClickListener
import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie

class MovieAdapter(
    private val onReservationButtonClick: MovieHomeClickListener,
) : RecyclerView.Adapter<ViewHolder>() {
    private var movies: List<Movie> = emptyList()
    private var advertisements: List<Advertisement> = emptyList()

    override fun getItemViewType(position: Int): Int {
        if ((position + 1) % ADVERTISEMENT_PER_INDEX == 0) return ADVERTISEMENT_VIEW_TYPE
        return MOVIE_VIEW_TYPE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == ADVERTISEMENT_VIEW_TYPE) {
            val advertisementItemBinding = ItemAdvertisementBinding.inflate(inflater, parent, false)
            return AdvertisementViewHolder(advertisementItemBinding)
        }
        val movieItemBinding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val movie = movies[position - getAdvertisementCount(position)]
        val advertisement = advertisements[getAdvertisementCount(position)]
        when (holder) {
            is MovieViewHolder -> holder.bind(movie, onReservationButtonClick)
            is AdvertisementViewHolder -> holder.bind(advertisement)
        }
    }

    override fun getItemCount(): Int {
        return movies.size + movies.size / 3
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun updateAdvertisements(advertisements: List<Advertisement>) {
        this.advertisements = advertisements
        notifyDataSetChanged()
    }

    private fun getAdvertisementCount(position: Int): Int {
        return (position + 1) / ADVERTISEMENT_PER_INDEX
    }

    companion object {
        private const val MOVIE_VIEW_TYPE = 0
        private const val ADVERTISEMENT_VIEW_TYPE = 1
        private const val ADVERTISEMENT_PER_INDEX = 4
    }
}
