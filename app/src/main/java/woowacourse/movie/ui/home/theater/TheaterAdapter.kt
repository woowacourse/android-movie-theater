package woowacourse.movie.ui.home.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater

class TheaterAdapter(
    private val theaters: List<Theater>,
    private val movieContentId: Long,
) : RecyclerView.Adapter<TheaterViewHolder>() {
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
}
