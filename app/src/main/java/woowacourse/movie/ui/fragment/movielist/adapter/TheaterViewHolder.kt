package woowacourse.movie.ui.fragment.movielist.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.model.TheaterModel

class TheaterViewHolder(view: View, private val onItemViewClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val theaterDescription: TextView by lazy { view.findViewById(R.id.tv_theater_description) }
    private val theaterName: TextView by lazy { view.findViewById(R.id.tv_theater_name) }
    private val theaterNextButton: ImageView by lazy { view.findViewById(R.id.iv_theater_next) }

    init {
        view.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
        theaterNextButton.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
    }

    fun bind(theater: TheaterModel) {
        theaterName.text = theaterName.context.getString(R.string.theater_name, theater.name)
        theaterDescription.text =
            theaterDescription.context.getString(R.string.theater_description, theater.times.size)
    }
}
