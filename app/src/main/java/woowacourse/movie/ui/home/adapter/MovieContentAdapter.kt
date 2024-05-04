package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieContentBinding
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.ui.ReservationButtonClickListener

class MovieContentAdapter(
    private val movieContents: List<MovieContent>,
    private val reservationButtonClickListener: ReservationButtonClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return when (viewType) {
            TYPE_MOVIE -> {
                val binding: ItemMovieContentBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_movie_content,
                        parent,
                        false,
                    )
                MovieViewHolder(binding).apply {
                    binding.onClickListener = reservationButtonClickListener
                }
            }

            else -> {
                val binding: ItemAdvertisementBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_advertisement,
                        parent,
                        false,
                    )
                AdvertisementViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = movieContents.size / INTERVAL_ADVERTISEMENT + movieContents.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val movieContent = movieContents[position - position / INTERVAL_ADVERTISEMENT]
        if (getItemViewType(position) == TYPE_MOVIE) {
            (holder as MovieViewHolder).bind(movieContent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position % DIVIDER_ADVERTISEMENT == INTERVAL_ADVERTISEMENT -> TYPE_ADS
            else -> TYPE_MOVIE
        }
    }

    companion object {
        private const val TYPE_ADS = 0
        private const val TYPE_MOVIE = 1
        private const val INTERVAL_ADVERTISEMENT = 3
        private const val DIVIDER_ADVERTISEMENT = INTERVAL_ADVERTISEMENT + 1
    }
}

@BindingAdapter("clickListener", "movieContentId")
fun onClickReservationButton(
    button: Button,
    clickListener: ReservationButtonClickListener,
    movieContentId: Long,
) {
    button.setOnClickListener {
        clickListener.onReservationButtonClick(movieContentId)
    }
}
