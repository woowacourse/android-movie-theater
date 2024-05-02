package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater

class TheaterAdapter(
    private val theaters: List<Theater>,
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

        binding.onClickListener = theaterClickListener

        return TheaterViewHolder(binding).apply {
            itemView.setOnClickListener {
                theaterClickListener.onClick(theaters[bindingAdapterPosition].id)
            }
        }
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
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theater: Theater) {
            binding.theater = theater
        }
    }

    fun interface TheaterClickListener {
        fun onClick(theaterId: Long)
    }
}

@BindingAdapter("theaterClickListener", "theaterId")
fun onClickTheater(
    constraintLayout: ConstraintLayout,
    theaterClickListener: TheaterAdapter.TheaterClickListener,
    theaterId: Long,
) {
    constraintLayout.setOnClickListener {
        theaterClickListener.onClick(theaterId)
    }
}
