package woowacourse.movie.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieCatalogBinding
import woowacourse.movie.feature.home.adapter.viewholder.AdvertisementViewHolder
import woowacourse.movie.feature.home.adapter.viewholder.MovieViewHolder
import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie

typealias OnMovieSelected = (movieId: Int) -> Unit

class MovieCatalogAdapter(
    private val movies: List<Movie>,
    private val advertisements: List<Advertisement>,
    private val onMovieSelected: OnMovieSelected,
) : RecyclerView.Adapter<ViewHolder>() {
    private val movieViewType = CatalogViewType.MOVIE.viewType
    private val advertisementViewType = CatalogViewType.ADVERTISEMENT.viewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == movieViewType) {
            val view = ItemMovieCatalogBinding.inflate(inflater, parent, false)
            MovieViewHolder(view)
        } else {
            val view = ItemAdvertisementBinding.inflate(inflater, parent, false)
            AdvertisementViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (CatalogViewType.from(getItemViewType(position))) {
            CatalogViewType.MOVIE -> {
                val moviePosition = position - (position / (CatalogViewType.ADVERTISEMENT.interval))
                val item = movies[moviePosition]
                (holder as MovieViewHolder).bind(item, onMovieSelected)
            }
            CatalogViewType.ADVERTISEMENT -> {
                val item = advertisements[position - CatalogViewType.ADVERTISEMENT.position]
                (holder as AdvertisementViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        val advertisementInterval = CatalogViewType.ADVERTISEMENT.interval
        if (movies.size >= advertisementInterval) {
            val advertisementCount = movies.size / advertisementInterval
            return movies.size + advertisementCount
        }
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % CatalogViewType.ADVERTISEMENT.interval == CatalogViewType.ADVERTISEMENT.position) {
            advertisementViewType
        } else {
            movieViewType
        }
    }
}
