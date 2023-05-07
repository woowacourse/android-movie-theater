package woowacourse.movie.fragment.movielist.adapter

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.domain.TheaterMovie
import woowacourse.movie.databinding.ItemTheaterListBinding

class TheaterViewHolder(
    private val binding: ItemTheaterListBinding,
    private val theaterOnClickListener: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val name: TextView = binding.tvTheaterName
    private val numberOfScreeningTime: TextView = binding.tvTheaterTimeNum

    init {
        binding.root.setOnClickListener {
            theaterOnClickListener(bindingAdapterPosition)
        }
    }

    fun bind(theaterMovie: TheaterMovie) {
        name.text = theaterMovie.name
        numberOfScreeningTime.text =
            FORMATTER_NUMBER_OF_SCREENING_TIME.format(theaterMovie.movieInfo.screeningTime.size)
    }

    companion object {
        private const val FORMATTER_NUMBER_OF_SCREENING_TIME = "%d개의 상영 시간"
    }
}
