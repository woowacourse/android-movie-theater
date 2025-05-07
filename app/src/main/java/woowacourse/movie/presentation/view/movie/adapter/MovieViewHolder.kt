package woowacourse.movie.presentation.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.ReservationUiFormatter
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.MovieUiModel
import java.time.LocalDate

@BindingAdapter("imgResPath")
fun setImageViewResourcePath(
    imageView: ImageView,
    @DrawableRes resId: Int,
) {
    imageView.setImageResource(resId)
}

@BindingAdapter(value = ["startDate", "endDate"])
fun setScreeningDate(
    view: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val text =
        view.context.getString(
            R.string.movie_screening_date,
            ReservationUiFormatter.localDateToUI(startDate),
            ReservationUiFormatter.localDateToUI(endDate),
        )
    view.text = text
}

class MovieViewHolder(
    parent: ViewGroup,
    private val handler: Handler,
) : BaseViewHolder<MovieUiModel, ItemMovieBinding>(
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    init {
        binding.btnMovieReservation.setOnClickListener {
            handler.onMovieClicked(item)
        }
    }

    override fun bind(item: MovieUiModel) {
        super.bind(item)
        binding.movie = item
        binding.executePendingBindings()
    }

    interface Handler {
        fun onMovieClicked(movie: MovieUiModel)
    }
}
