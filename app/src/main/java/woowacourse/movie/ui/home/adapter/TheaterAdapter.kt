package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theater

class TheaterAdapter(
    private val screen: Screen,
    private val itemClick: (screenId: Int, theaterId: Int) -> Unit,
) : ListAdapter<Theater, TheaterViewHolder>(TheaterDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding = HolderTheaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), screen)
    }
}
