package woowacourse.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.TheaterItemModel
import woowacourse.movie.data.model.uimodel.TheaterUiModel
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.viewholder.TheaterViewHolder

class TheaterAdapter : RecyclerView.Adapter<TheaterViewHolder>() {
    private var _theaters: List<TheaterItemModel> = listOf()
    val theaters
        get() = _theaters.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemTheaterBinding.inflate(layoutInflater, parent, false)

        return TheaterViewHolder(view)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(theaters[position])
    }

    fun updateTheaterItems(
        theaters: List<TheaterUiModel>,
        onClick: (TheaterUiModel) -> Unit
    ) {
        _theaters = theaters.map { theater ->
            theater.toItemModel { onClick(theater) }
        }
    }
}
