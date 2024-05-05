package woowacourse.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.model.MovieListItem
import woowacourse.movie.list.model.MovieListItemType

class MovieListAdapter(
    private val movieHomeClickListener: OnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies: List<Movie> = emptyList()
    private var advertisements: List<Advertisement> = emptyList()

    fun initMovieListInfo(movies: List<Movie>, advertisements: List<Advertisement>) {
        this.movies = movies
        this.advertisements = advertisements
    }

    inner class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: Movie,
            clickListener: OnItemClickListener,
        ) {
            binding.movie = movie
            binding.moviePoster.setImageResource(movie.posterResourceId)
            binding.onItemClickListener = clickListener
        }
    }

    inner class AdvertisementViewHolder(val binding: AdvertisementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(advertisement: Advertisement) {
            binding.advertisement = advertisement
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = MovieListItem(position)
        return item.type.separator
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (viewType == MovieListItemType.MOVIE.separator) {
            val binding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MovieViewHolder(binding)
        } else {
            val binding =
                AdvertisementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdvertisementViewHolder(binding)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (getItemViewType(position) == MovieListItemType.MOVIE.separator) {
            val movieHolder = holder as MovieViewHolder
            movieHolder.bind(movies[position], movieHomeClickListener)
        } else {
            val advertisementHolder = holder as AdvertisementViewHolder
            advertisementHolder.bind(advertisements[0])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
