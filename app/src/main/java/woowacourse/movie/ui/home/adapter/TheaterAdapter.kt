package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.home.TheaterAdapterActionHandler

class TheaterAdapter(
    private val screen: Screen,
    private val onMove: (Int, Int) -> Unit,
) : ListAdapter<Theater, TheaterViewHolder>(TheaterDiffUtil()), TheaterAdapterActionHandler {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding = HolderTheaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding, this)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position), screen)
    }

    override fun moveToDetailActivity(
        screenId: Int,
        theaterId: Int,
    ) {
        onMove(screenId, theaterId)
    }
}
