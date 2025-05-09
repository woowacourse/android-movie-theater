package woowacourse.movie.view.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.TheaterUIModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val clickListener: TheaterClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(theaterUIModel: TheaterUIModel) {
        binding.tvTheaterName.text =
            itemView.context.getString(
                R.string.bottom_sheet_dialog_theater_name,
                theaterUIModel.name,
            )
        binding.tvTimeSlot.text =
            itemView.context.getString(
                R.string.bottom_sheet_dialog_time_slot,
                theaterUIModel.timeSlotCount,
            )
        binding.clTheater.setOnClickListener {
            clickListener.onTheaterClick(theaterUIModel)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: TheaterClickListener,
        ): TheaterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTheaterBinding.inflate(layoutInflater, parent, false)
            return TheaterViewHolder(binding, clickListener)
        }
    }
}
