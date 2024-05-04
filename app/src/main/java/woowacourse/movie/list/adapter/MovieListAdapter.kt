package woowacourse.movie.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie
import woowacourse.movie.list.model.MovieListItem
import woowacourse.movie.list.model.MovieListItemType
import java.time.format.DateTimeFormatter

class MovieListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies: List<Movie> = emptyList()
    private var advertisements: List<Advertisement> = emptyList()

    fun initMovieListInfo(movies: List<Movie>, advertisements: List<Advertisement>) {
        this.movies = movies
        this.advertisements = advertisements
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val poster: ImageView = itemView.findViewById(R.id.movie_poster)
        val screeningDate: TextView = itemView.findViewById(R.id.movie_screening_date)
        val runningTime: TextView = itemView.findViewById(R.id.movie_running_time)
    }

    inner class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.advertisement_image)
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
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            MovieViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.advertisement, parent, false)
            AdvertisementViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (getItemViewType(position) == MovieListItemType.MOVIE.separator) {
            bindMovies(holder, position)
        } else {
            bindAdvertisements(holder)
        }
    }

    private fun bindAdvertisements(holder: RecyclerView.ViewHolder) {
        val advertisementHolder = holder as AdvertisementViewHolder
        advertisementHolder.image.setImageResource(advertisements[0].image)
    }

    private fun bindMovies(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val formattedScreeningDate =
            movies[position].firstScreeningDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

        val movieHolder = holder as MovieViewHolder
        movieHolder.title.text = movies[position].title
        movieHolder.poster.setImageResource(movies[position].posterResourceId)
        movieHolder.screeningDate.text = formattedScreeningDate
        movieHolder.runningTime.text = movies[position].runningTime.toString()

        movieHolder.itemView.setOnClickListener {
            itemClickListener.onClick(movies[position].id)
        }
    }

    interface OnItemClickListener {
        fun onClick(movieId: Long)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
