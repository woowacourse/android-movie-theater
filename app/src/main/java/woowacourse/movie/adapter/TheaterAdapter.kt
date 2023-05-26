package woowacourse.movie.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.TheaterItemModel
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import woowacourse.movie.viewholder.TheaterViewHolder

class TheaterAdapter : RecyclerView.Adapter<TheaterViewHolder>() {
    private var _theaters: List<TheaterItemModel> = listOf()
    val theaters
        get() = _theaters.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {

        return TheaterViewHolder(parent)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(theaters[position])
    }

    fun updateTheaterItems(
        theaters: List<TheaterUIModel>,
        onClick: (TheaterUIModel) -> Unit
    ) {
        _theaters = theaters.map { theater ->
            theater.toItemModel { onClick(theater) }
        }
    }
}
