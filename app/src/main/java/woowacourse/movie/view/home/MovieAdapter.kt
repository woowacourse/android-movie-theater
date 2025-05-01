package woowacourse.movie.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.movie.Movie

class MovieAdapter(
    private val movieClickListener: MovieClickListener,
    private val advertisementClickListener: () -> Unit,
) : ListAdapter<Movie, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun getItemViewType(position: Int): Int =
        when {
            (position + 1) % AD_POSITION_MULTIPLE == 0 -> AD_ITEM_TYPE
            else -> MOVIE_ITEM_TYPE
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            MOVIE_ITEM_TYPE -> {
                val inflater = LayoutInflater.from(parent.context)
                val movieBinding =
                    DataBindingUtil.inflate<ItemMovieBinding>(
                        inflater,
                        R.layout.item_movie,
                        parent,
                        false,
                    )
                val holder = MovieViewHolder(movieBinding)
                holder.button.setOnClickListener {
                    val position = holder.adapterPosition
                    val adjustedPosition = position - position / AD_POSITION_MULTIPLE
                    val item = getItem(adjustedPosition)
                    movieClickListener.onReservationClick(item.id)
                }
                holder
            }

            AD_ITEM_TYPE -> {
                val view =
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_advertisement, parent, false)
                AdvertisementViewHolder(view, advertisementClickListener)
            }

            else -> throw IllegalStateException("지원하지 않는 타입입니다.")
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> {
                val adjustedPosition = position - position / AD_POSITION_MULTIPLE
                val item = getItem(adjustedPosition)
                holder.bind(item)
            }
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    companion object {
        private const val AD_POSITION_MULTIPLE = 4
        private val MOVIE_ITEM_TYPE = R.layout.item_movie
        private val AD_ITEM_TYPE = R.layout.item_advertisement
    }
}
