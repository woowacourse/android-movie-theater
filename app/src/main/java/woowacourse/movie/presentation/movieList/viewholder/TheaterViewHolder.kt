package woowacourse.movie.presentation.movieList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.movieList.listener.TheaterClickListener

class TheaterViewHolder(private val binding: ItemTheaterBinding) : RecyclerView.ViewHolder(binding.root) {
//    private val name: TextView = view.findViewById(R.id.item_theater_name_tv)
//    private val time: TextView = view.findViewById(R.id.item_theater_time_tv)
//    private val arrowButton: ImageButton = view.findViewById(R.id.item_theater_arrow_btn)

    fun bind(
        theater: Theater,
        movieId: Long,
        listener: TheaterClickListener,
    ) {
        binding.theater = theater
        binding.movieId = movieId
        binding.listener = listener
//        time.text =
//            theater.screens
//                .filter { screen -> screen.movieId == movieId }
//                .flatMap { screen -> screen.screenSchedule }
//                .size.toString()
//        arrowButton.setOnClickListener {
//            listener(theater.id, movieId)
//        }
    }
}
