package woowacourse.movie.view.theater

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.TheaterUIModel

class TheaterViewHolder(
    view: View,
    private val clickListener: TheaterClickListener,
) : RecyclerView.ViewHolder(view) {
    private val layoutTheater: ConstraintLayout = view.findViewById(R.id.cl_theater)
    private val theaterNameTextView: TextView = view.findViewById(R.id.tv_theater_name)
    private val timeslotTextView: TextView = view.findViewById(R.id.tv_time_slot)

    fun bind(theaterUIModel: TheaterUIModel) {
        theaterNameTextView.text =
            itemView.context.getString(
                R.string.bottom_sheet_dialog_theater_name,
                theaterUIModel.name,
            )
        timeslotTextView.text =
            itemView.context.getString(
                R.string.bottom_sheet_dialog_time_slot,
                theaterUIModel.timeSlotCount,
            )
        layoutTheater.setOnClickListener {
            clickListener.onTheaterClick(theaterUIModel)
        }
    }
}
