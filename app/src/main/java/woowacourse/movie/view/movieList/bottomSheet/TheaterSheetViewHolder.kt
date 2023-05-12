package woowacourse.movie.view.movieList.bottomSheet

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.model.TheaterModel

class TheaterSheetViewHolder(view: View, onTheaterClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding = TheaterItemBinding.bind(view)

    init {
        view.setOnClickListener { onTheaterClick(absoluteAdapterPosition) }
    }

    fun bind(theater: TheaterModel) {
        binding.tvTheaterName.text = theater.name
        binding.tvTheaterScreeningTime.text = binding.root.context.getString(
            R.string.theater_bottom_sheet_screening_time,
            theater.screeningTimes.size
        )
    }
}
