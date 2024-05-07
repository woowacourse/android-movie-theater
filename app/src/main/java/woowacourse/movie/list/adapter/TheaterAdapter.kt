package woowacourse.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.list.model.Theater

class TheaterAdapter : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    private var movieId: Long = -1
    private var theaters: List<Theater> = emptyList()
    private lateinit var itemClickListener: OnItemClickListener

    fun initTheatersInfo(movieId: Long, theaters: List<Theater>) {
        this.movieId = movieId
        this.theaters = theaters
    }

    inner class TheaterViewHolder(private val binding: TheaterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            name: String,
            position: Int,
        ) {
            binding.theaterName.text = name
            val count = theaters[position].getCount(movieId)
            binding.screeningTimes.text = String.format(
                binding.root.context.resources.getString(R.string.screening_time_count_format),
                count,
            )
            binding.theaterItem.setOnClickListener {
                itemClickListener.onClick(movieId, theaters[position].id)
            }
            if (count == 0) binding.theaterItem.isClickable = false
        }
    }

    interface OnItemClickListener {
        fun onClick(
            movieId: Long,
            theaterId: Long,
        )
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding = TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position].name, position)
    }
}
