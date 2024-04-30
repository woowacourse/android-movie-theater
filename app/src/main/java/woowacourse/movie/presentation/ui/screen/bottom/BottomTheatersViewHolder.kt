package woowacourse.movie.presentation.ui.screen.bottom

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.TheaterCount

class BottomTheatersViewHolder(
    val view: View,
    private val actionHandler: BottomTheaterActionHandler,
) : RecyclerView.ViewHolder(view) {
    private val theaterName: TextView = view.findViewById(R.id.tv_bottom_theater_name)
    private val count: TextView = view.findViewById(R.id.tv_bottom_count)
    private val clTheater: ConstraintLayout = view.findViewById(R.id.cl_theater)

    fun bind(theaterCount: TheaterCount) {
        initView(theaterCount)
        initClickListener(theaterCount)
    }

    private fun initView(theaterCount: TheaterCount) {
        with(theaterCount) {
            this@BottomTheatersViewHolder.theaterName.text = name
            this@BottomTheatersViewHolder.count.text =
                view.context.getString(R.string.theater_count, size)
        }
    }

    private fun initClickListener(theaterCount: TheaterCount) {
        clTheater.setOnClickListener {
            actionHandler.onTheaterClick(theaterCount.id)
        }
    }
}
