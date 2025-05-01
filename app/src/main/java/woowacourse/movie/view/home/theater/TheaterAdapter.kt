package woowacourse.movie.view.home.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.TheaterMovieSchedule

class TheaterAdapter(
    theaterMovieScheduleItems: List<TheaterMovieSchedule>,
    private val onTheaterClick: (TheaterMovieSchedule) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    private var theaterMovieSchedules: List<TheaterMovieSchedule> = theaterMovieScheduleItems

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val theaterBinding =
            DataBindingUtil.inflate<ItemTheaterBinding>(
                inflater,
                R.layout.item_theater,
                parent,
                false,
            )
        return TheaterViewHolder(theaterBinding, onTheaterClick)
    }

    override fun getItemCount(): Int = theaterMovieSchedules.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaterMovieSchedules[position])
    }

    fun updateTheaterMovieSchedules(newItems: List<TheaterMovieSchedule>) {
        theaterMovieSchedules = newItems
        notifyDataSetChanged()
    }
}
