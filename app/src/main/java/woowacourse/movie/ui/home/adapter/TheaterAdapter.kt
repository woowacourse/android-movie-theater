package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.domain.Theater

class TheaterAdapter(
    private val theaters: List<Theater>,
    private val movieContentId: Long,
    private val theaterClickListener: TheaterClickListener,
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

        return TheaterViewHolder(binding, movieContentId, theaterClickListener)
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
        movieContentId: Long,
        theaterClickListener: TheaterClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.movieContentId = movieContentId
            binding.onClickListener = theaterClickListener
        }

        fun bind(theater: Theater) {
            binding.theater = theater
        }
    }

    interface TheaterClickListener {
        fun onTheaterClick(
            movieContentId: Long,
            theaterId: Long,
        )
    }
}

@BindingAdapter("theaterClickListener", "movieContentId", "theaterId")
fun onClickTheater(
    constraintLayout: ConstraintLayout,
    theaterClickListener: TheaterAdapter.TheaterClickListener,
    movieContentId: Long,
    theaterId: Long,
) {
    constraintLayout.setOnClickListener {
        theaterClickListener.onTheaterClick(movieContentId, theaterId)
    }
}
