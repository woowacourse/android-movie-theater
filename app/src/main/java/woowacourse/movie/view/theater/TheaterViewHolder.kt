package woowacourse.movie.view.theater

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.view.model.TheaterUiModel

@BindingAdapter("theaterNameText")
fun setTheaterNameText(
    view: TextView,
    theater: TheaterUiModel?,
) {
    theater ?: return
    val theaterName = theater.name
    view.text = view.context.getString(R.string.bottom_sheet_dialog_theater_name, theaterName)
}

@BindingAdapter("timeSlotText")
fun setTimeSlotText(
    view: TextView,
    theater: TheaterUiModel?,
) {
    theater ?: return
    val count = theater.totalScreeningTimes
    view.text = view.context.getString(R.string.bottom_sheet_dialog_time_slot, count)
}

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    clickListener: TheaterClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.theaterClickListener = clickListener
    }

    fun bind(theaterUIModel: TheaterUiModel) {
        binding.theater = theaterUIModel
    }
}
