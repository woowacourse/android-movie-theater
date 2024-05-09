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
import woowacourse.movie.list.model.TheaterContent

class MovieListAdapter(
    private var theaterContent: List<TheaterContent>,
    private val movieHomeClickListener: OnItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class MovieListViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.moviePoster.setImageResource(movie.posterResourceId)
            binding.onItemClickListener = movieHomeClickListener
        }
    }

    inner class AdvertisementViewHolder(val binding: AdvertisementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(advertisement: Advertisement) {
            binding.advertisement = advertisement
        }
    }

    fun updateItems(theaterContent: List<TheaterContent>) {
        this.theaterContent = theaterContent
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val item = MovieListItem(position)
        return item.type.separator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MovieListItemType.MOVIE.separator) {
            val binding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MovieListViewHolder(binding)
        } else {
            val binding =
                AdvertisementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdvertisementViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (theaterContent[position]) {
            is Movie -> {
                val movieHolder = holder as MovieListViewHolder
                val movie = theaterContent[position] as Movie
                movieHolder.bind(movie)
            }
            is Advertisement -> {
                val advertisementHolder = holder as AdvertisementViewHolder
                val advertisement = theaterContent[position] as Advertisement
                advertisementHolder.bind(advertisement)
            }
        }
    }

    override fun getItemCount(): Int {
        return theaterContent.size
    }
}
