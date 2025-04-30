package woowacourse.movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.presentation.movies.MoviesItem
import woowacourse.movie.ui.util.PosterMapper

class MovieAdapter(private val onClick: (Movie) -> Unit) :
    ListAdapter<MoviesItem, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_movie -> {
                val binding = DataBindingUtil.inflate<ItemMovieBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_movie,
                    parent,
                    false
                )
                MovieViewHolder(binding, parent.context, onClick)
            }

            R.layout.item_advertisement -> {
                val binding = DataBindingUtil.inflate<ItemAdvertisementBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_advertisement,
                    parent,
                    false
                )
                AdvertisementViewHolder(binding)
            }

            else -> throw IllegalArgumentException(TYPE_ERROR)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> holder.bind((getItem(position) as MoviesItem.MovieItem).movie)
            is AdvertisementViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MoviesItem.MovieItem -> R.layout.item_movie
            is MoviesItem.AdvertisementItem -> R.layout.item_advertisement
        }
    }

    private class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val context: Context,
        private val onClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textviewTitle.text = movie.title
            binding.textviewScreeningdate.text =
                context.getString(
                    R.string.date_text,
                    movie.startScreeningDate,
                    movie.endScreeningDate,
                )
            binding.textviewRunningtime.text =
                context.getString(
                    R.string.runningTime_text,
                    movie.runningTime.toString(),
                )
            binding.imageviewPoster.setImageResource(PosterMapper.convertTitleToResId(movie.title))

            binding.buttonBook.setOnClickListener {
                onClick(movie)
            }
        }
    }

    private class AdvertisementViewHolder(private val binding: ItemAdvertisementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.imageviewAd.setImageResource(R.drawable.advertisement)
        }
    }

    companion object {
        private const val TYPE_ERROR = "[ERROR] 알 수 없는 타입입니다."

        private val diffCallback =
            object : DiffUtil.ItemCallback<MoviesItem>() {
                override fun areContentsTheSame(
                    oldItem: MoviesItem,
                    newItem: MoviesItem,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areItemsTheSame(
                    oldItem: MoviesItem,
                    newItem: MoviesItem,
                ): Boolean {
                    return when {
                        oldItem is MoviesItem.MovieItem && newItem is MoviesItem.MovieItem -> oldItem.movie == newItem.movie
                        oldItem is MoviesItem.AdvertisementItem && newItem is MoviesItem.AdvertisementItem -> oldItem.id == newItem.id
                        else -> false
                    }
                }
            }
    }
}
