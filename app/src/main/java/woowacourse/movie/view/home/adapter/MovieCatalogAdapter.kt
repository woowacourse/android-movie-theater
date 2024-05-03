package woowacourse.movie.view.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieCatalogBinding
import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.home.adapter.viewholder.AdvertisementViewHolder
import woowacourse.movie.view.home.adapter.viewholder.MovieViewHolder

typealias OnClickReservationButton = (Movie) -> Unit

class MovieCatalogAdapter(
    private val onClickReservationButton: OnClickReservationButton,
) : RecyclerView.Adapter<ViewHolder>() {
    private var movies: List<Movie> = emptyList()
    private var advertisements: List<Advertisement> = emptyList()

    private val movieViewType = CatalogViewType.MOVIE.viewType
    private val advertisementViewType = CatalogViewType.ADVERTISEMENT.viewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == movieViewType) {
            val view = ItemMovieCatalogBinding.inflate(inflater, parent, false)
            MovieViewHolder(view, onClickReservationButton)
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
                (holder as MovieViewHolder).bind(item)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateMovies(movies: List<Movie>)  {
        this.movies = movies
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAds(advertisements: List<Advertisement>)  {
        this.advertisements = advertisements
        notifyDataSetChanged()
    }
}
