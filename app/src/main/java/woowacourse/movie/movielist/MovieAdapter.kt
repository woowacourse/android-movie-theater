package woowacourse.movie.movielist

import MovieViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.movielist.dto.AdDto
import woowacourse.movie.movielist.dto.MovieDto

class MovieAdapter(
    private val movies: List<MovieDto>,
    private val ad: AdDto,
) :
    RecyclerView.Adapter<ViewHolder>() {

    lateinit var itemMovieClick: OnClickListener<MovieDto>
    lateinit var itemAdClick: OnClickListener<AdDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.MOVIE_VIEW -> {
                val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(binding, itemMovieClick)
            }
            ViewType.AD_VIEW -> {
                val binding = AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(binding, itemAdClick)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        when (holder.itemViewType) {
            ViewType.MOVIE_VIEW.ordinal -> (holder as MovieViewHolder).bind(item)
            ViewType.AD_VIEW.ordinal -> (holder as AdViewHolder).bind(ad)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % DIVIDE_MOVIE_VIEW == 0) ViewType.AD_VIEW.ordinal
        else ViewType.MOVIE_VIEW.ordinal
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    companion object {
        private const val DIVIDE_MOVIE_VIEW = 4
    }
}
