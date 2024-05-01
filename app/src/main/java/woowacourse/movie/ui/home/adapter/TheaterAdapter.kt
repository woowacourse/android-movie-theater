package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater

class TheaterAdapter(
    private val theaters: List<Theater>,
    private val theaterItemClickListener: TheaterItemClickListener,
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

        return TheaterViewHolder(binding, theaterItemClickListener)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position])
    }

    override fun getItemCount(): Int = theaters.size

    class TheaterViewHolder(private val binding: ItemTheaterBinding, private val theaterItemClickListener: TheaterItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theater: Theater) {
            binding.theater = theater
            binding.root.setOnClickListener {
                theaterItemClickListener.onTheaterItemClick(theater.id)
            }
        }
    }

    interface TheaterItemClickListener {
        fun onTheaterItemClick(theaterId: Long)
    }
}
