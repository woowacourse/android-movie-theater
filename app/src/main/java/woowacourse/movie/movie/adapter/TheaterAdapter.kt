package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.movie.TheaterUiModel

class TheaterAdapter(
    private val theaters: List<TheaterUiModel>,
    private val onSelectClick: SelectClickListener,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TheaterItemBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(binding)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val theater = theaters[position]

        holder.bind(theater)
    }

    inner class TheaterViewHolder(val binding: TheaterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theater: TheaterUiModel) {
            binding.theater = theater
            binding.clickListener = onSelectClick
        }
    }
}
