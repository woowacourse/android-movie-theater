package woowacourse.movie.theater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterAdapter(
    private val theaters: List<TheaterUiModel>,
    private val onSelectClick: SelectClickListener,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        return TheaterViewHolder(parent, onSelectClick)
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
