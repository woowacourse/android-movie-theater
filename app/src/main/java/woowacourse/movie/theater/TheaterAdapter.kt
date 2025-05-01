package woowacourse.movie.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterAdapter(
    private val theaters: List<TheaterUiModel>,
    private val onSelectClick: SelectClickListener,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TheaterItemBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(binding, onSelectClick)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val theater = theaters[position]

        holder.bind(theater)
    }
}
