package woowacourse.movie.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.model.MovieUiModel

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
    startDate: String,
    endDate: String,
) {
    val text =
        view.context.getString(
            R.string.movie_screening_date,
            startDate,
            endDate,
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
        fun onMovieClicked(item: MovieUiModel)
    }
}
