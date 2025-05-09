package woowacourse.movie.view.movies.cinema

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.model.Screening

class CinemaSelectionAdapter(
    val items: List<Screening>,
    val eventListener: OnCinemaSelectionListener,
) : RecyclerView.Adapter<CinemaViewHolder>() {
    override fun onBindViewHolder(
        holder: CinemaViewHolder,
        position: Int,
    ) {
        holder.bind(items[position], eventListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CinemaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cinema, parent, false)
        val binding =
            ItemCinemaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CinemaViewHolder(view, binding)
    }

    override fun getItemCount(): Int = items.size
}

class CinemaViewHolder(
    val view: View,
    val binding: ItemCinemaBinding,
) : ViewHolder(binding.root) {
    fun bind(
        screening: Screening,
        eventListener: OnCinemaSelectionListener,
    ) {
        binding.screening = screening
        binding.root
            .setOnClickListener {
                eventListener.onReserveButtonClick(screening)
            }
        binding.executePendingBindings()
    }
}
