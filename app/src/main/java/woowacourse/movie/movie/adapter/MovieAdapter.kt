package woowacourse.movie.movie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.ui.model.MovieUiModel

class MovieAdapter(
    private val movieList: List<MovieUiModel>,
    private val onReserveClick: ReserveClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        val adCount = movieList.size / AD_FREQUENCY
        return movieList.size + adCount
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % (AD_FREQUENCY + 1) == 0) TYPE_AD else TYPE_MOVIE
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_AD -> AdViewHolder(parent)
            else -> MovieViewHolder.from(parent, onReserveClick)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is MovieViewHolder -> {
                val realPosition = position - (position / (AD_FREQUENCY + 1))
                holder.bind(movieList[realPosition])
            }
            is AdViewHolder -> {
                holder.bind()
            }
        }
    }

    companion object {
        private const val TYPE_MOVIE = 0
        private const val TYPE_AD = 1
        private const val AD_FREQUENCY = 3
    }
}
