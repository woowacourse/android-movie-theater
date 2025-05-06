package woowacourse.movie.ui.view.cinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.reservation.Advertisement
import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ScreeningContent
import woowacourse.movie.ui.view.util.ErrorMessage

class ScreeningAdapter(
    private val onClickReserveButton: (Screening) -> Unit,
) : ListAdapter<ScreeningContent, BaseViewHolder<ScreeningContent>>(
        object : DiffUtil.ItemCallback<ScreeningContent>() {
            override fun areItemsTheSame(
                oldItem: ScreeningContent,
                newItem: ScreeningContent,
            ): Boolean = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: ScreeningContent,
                newItem: ScreeningContent,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun getItemViewType(position: Int): Int {
        val screeningContent = getItem(position)
        return when (screeningContent) {
            is Screening -> VIEW_TYPE_SCREENING
            is Advertisement -> VIEW_TYPE_ADVERTISEMENT
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ScreeningContent> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SCREENING -> {
                val view = layoutInflater.inflate(R.layout.item_screening, parent, false)
                ScreeningViewHolder(view, onClickReserveButton)
            }

            VIEW_TYPE_ADVERTISEMENT -> {
                val view = layoutInflater.inflate(R.layout.item_advertisement, parent, false)
                AdvertisementViewHolder(view)
            }

            else -> error(ErrorMessage("viewType").noSuch())
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ScreeningContent>,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        const val VIEW_TYPE_SCREENING = 0
        const val VIEW_TYPE_ADVERTISEMENT = 1
    }
}
