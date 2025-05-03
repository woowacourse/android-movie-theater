package woowacourse.movie.view.home.movies.viewholder

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.home.movies.model.TheaterRvItem

class TheaterViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutRes: Int,
    private val handler: Handler,
) : BaseViewHolder<TheaterRvItem.TheaterItem>(parent, layoutRes) {
    override fun bind(item: TheaterRvItem.TheaterItem) {
        TheaterItemBinding.bind(itemView).apply {
            model = item
            eventListener = handler
        }
    }

    interface Handler {
        fun onSelectTheater(
            theaterName: String,
            movieId: Int,
        )
    }
}
