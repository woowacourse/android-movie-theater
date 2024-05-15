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
import woowacourse.movie.domain.MovieContent
import woowacourse.movie.ui.home.ReservationButtonClickListener

class MovieContentAdapter(
    private val reservationButtonClickListener: ReservationButtonClickListener,
) : androidx.recyclerview.widget.ListAdapter<MovieContent, RecyclerView.ViewHolder>(MovieContentDiffUtil) {
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
                MovieViewHolder(binding, reservationButtonClickListener)
            }

            TYPE_ADS -> {
                val binding: ItemAdvertisementBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_advertisement,
                        parent,
                        false,
                    )
                AdvertisementViewHolder(binding)
            }

            else -> throw IllegalStateException(EXCEPTION_VIEW_TYPE)
        }
    }

    override fun getItemCount(): Int = currentList.size / INTERVAL_ADVERTISEMENT + currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val movieContent = currentList[position - position / INTERVAL_ADVERTISEMENT]
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
        private const val EXCEPTION_VIEW_TYPE = "잘못된 형태의 뷰 타입입니다."
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
