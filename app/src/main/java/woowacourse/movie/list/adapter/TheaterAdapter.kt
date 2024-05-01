package woowacourse.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.list.model.Theater

class TheaterAdapter(private val movieId: Long, private val theaters: List<Theater>) :
    RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    inner class TheaterViewHolder(private val binding: TheaterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            name: String,
            position: Int,
        ) {
            binding.theaterName.text = name
            binding.screeningTimes.text = theaters[position].getCount(movieId).toString()
            binding.theaterItem.setOnClickListener {
                itemClickListener.onClick(movieId, theaters[position].id)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(
            movieId: Long,
            theaterId: Long,
        )
    }

    private lateinit var itemClickListener: OnItemClickListener

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
