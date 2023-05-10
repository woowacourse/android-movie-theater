package woowacourse.movie.view.main.movieslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.TheatersUiModel
import woowacourse.movie.view.main.movieslist.viewholder.TheaterViewHolder

class TheaterAdapter(
    val context: Context,
    private val theaters: TheatersUiModel,
    private val onClickEvent: (TheaterUiModel) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val binding = DataBindingUtil.inflate<ItemTheaterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_theater,
            parent,
            false,
        )
        return TheaterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return theaters.list.size
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(theaters.list[position], onClickEvent)
    }
}
