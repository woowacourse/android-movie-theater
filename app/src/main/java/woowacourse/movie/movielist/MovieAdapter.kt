package woowacourse.movie.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemScreeningAdvertiseBinding
import woowacourse.movie.databinding.ItemScreeningMovieBinding
import woowacourse.movie.movielist.uimodel.AdvertisementUiModel
import woowacourse.movie.movielist.uimodel.ListItemUiModel
import woowacourse.movie.movielist.uimodel.MovieUiModel

class MovieAdapter(
    private var movies: List<ListItemUiModel>,
    private val adapterClickListener: AdapterClickListener,
) : RecyclerView.Adapter<ListItemViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (movies[position]) {
            is MovieUiModel -> MOVIE
            is AdvertisementUiModel -> ADVERTISE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListItemViewHolder {
        return when (viewType) {
            MOVIE -> {
                val binding =
                    ItemScreeningMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                MovieViewHolder(binding, adapterClickListener)
            }

            ADVERTISE -> {
                val binding =
                    ItemScreeningAdvertiseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                AdvertiseViewHolder(binding)
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(
        holder: ListItemViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> holder.onBind(movies[position] as MovieUiModel)
            is AdvertiseViewHolder -> {}
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<ListItemUiModel>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(movies, newMovies))
        movies = newMovies
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(
        private val old: List<ListItemUiModel>,
        private val new: List<ListItemUiModel>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean = old[oldItemPosition] == new[newItemPosition]

        override fun areContentsTheSame(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Boolean = old[oldItemPosition] == new[newItemPosition]
    }

    companion object {
        private const val MOVIE = 0
        private const val ADVERTISE = 1
    }
}
