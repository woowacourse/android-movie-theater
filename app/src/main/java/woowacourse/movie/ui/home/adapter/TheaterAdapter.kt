package woowacourse.movie.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater
import woowacourse.movie.ui.home.MovieHomeKey
import woowacourse.movie.ui.reservation.MovieReservationActivity

class TheaterAdapter(
    private val theaters: List<Theater>,
    private val movieContentId: Long,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding: ItemTheaterBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_theater,
                parent,
                false,
            )

        return TheaterViewHolder(binding, movieContentId)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position])
    }

    override fun getItemCount(): Int = theaters.size

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
}
