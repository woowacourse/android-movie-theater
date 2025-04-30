package woowacourse.movie.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.movies.model.UiModel
import woowacourse.movie.view.movies.viewholder.AdvertiseViewHolder
import woowacourse.movie.view.movies.viewholder.MovieViewHolder

class MovieAdapter(
    private val onClickBooking: (Int) -> Unit,
    private val itemsList: List<UiModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = itemsList.size

    override fun getItemViewType(position: Int): Int =
        when (itemsList[position]) {
            is UiModel.MovieUiModel -> VIEW_TYPE_MOVIE
            is UiModel.AdvertiseUiModel -> VIEW_TYPE_ADVERTISEMENT
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)

        val movieItemBinding = MovieItemBinding.inflate(inflater, parent, false)
        val advertisementItemBinding = AdvertisementItemBinding.inflate(inflater, parent, false)
        return when (viewType) {
            VIEW_TYPE_ADVERTISEMENT -> AdvertiseViewHolder(advertisementItemBinding)
            VIEW_TYPE_MOVIE -> MovieViewHolder(movieItemBinding, onClickBooking)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = itemsList[position]

        when (holder) {
            is AdvertiseViewHolder -> holder.bind(item as UiModel.AdvertiseUiModel)
            is MovieViewHolder -> holder.bind(item as UiModel.MovieUiModel)
        }
    }

    companion object {
        private val VIEW_TYPE_MOVIE = R.layout.movie_item
        private val VIEW_TYPE_ADVERTISEMENT = R.layout.advertisement_item
    }
}
