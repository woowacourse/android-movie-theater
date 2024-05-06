package woowacourse.movie.ui.home.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.home.MovieHomeKey
import woowacourse.movie.ui.reservation.MovieReservationActivity

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val movieContentId: Long,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: Theater) {
        binding.theater = theater
        binding.root.setOnClickListener {
            val context = binding.root.context
            Intent(context, MovieReservationActivity::class.java).apply {
                putExtra(MovieHomeKey.MOVIE_CONTENT_ID, movieContentId)
                putExtra(MovieHomeKey.THEATER_ID, theater.id)
                context.startActivity(this)
            }
        }
    }
}
