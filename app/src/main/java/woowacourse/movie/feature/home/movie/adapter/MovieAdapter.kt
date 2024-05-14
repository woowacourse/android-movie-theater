package woowacourse.movie.feature.home.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.data.movie.dto.Movie
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding

typealias ReservationButtonClickListener = (movieId: Long) -> Unit

class MovieAdapter(
    private val movies: List<Movie>,
    private val onReservationButtonClick: ReservationButtonClickListener,
) : RecyclerView.Adapter<ViewHolder>() {
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
            val binding = ItemAdvertisementBinding.inflate(inflater, parent, false)
            return AdvertisementViewHolder(binding)
        }
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val movie = movies[position]
        when (holder) {
            is MovieViewHolder -> holder.bind(movie, onReservationButtonClick)
            is AdvertisementViewHolder -> holder.bind(ADVERTISEMENT_LINK)
        }
    }

    override fun getItemId(position: Int): Long = movies[position].id

    override fun getItemCount(): Int {
        return movies.size + movies.size / ADVERTISEMENT_OFFSET
    }

    companion object {
        private const val MOVIE_VIEW_TYPE = 0
        private const val ADVERTISEMENT_VIEW_TYPE = 1
        private const val ADVERTISEMENT_OFFSET = 3
        private const val ADVERTISEMENT_PER_INDEX = 4
        private const val ADVERTISEMENT_LINK = "https://www.woowacourse.io/"
    }
}
