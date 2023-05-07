package woowacourse.movie.dialog

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.TheaterMovie

class TheaterViewHolder(private val view: View, onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val theaterNameText: TextView = view.findViewById(R.id.dialog_theater_name_text)
    private val time: TextView = view.findViewById(R.id.dialog_time_text)
    private val clickArrow: ImageView = view.findViewById(R.id.theater_click_image)

    init {
        clickArrow.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(theaterMovie: TheaterMovie) {
        theaterNameText.text =
            view.context.getString(R.string.theater_name, theaterMovie.theaterName)
        time.text = view.context.getString(
            R.string.theater_times_count, theaterMovie.screenTimes.size
        )
    }
}
