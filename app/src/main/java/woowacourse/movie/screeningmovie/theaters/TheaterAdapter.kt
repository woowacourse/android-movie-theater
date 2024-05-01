package woowacourse.movie.screeningmovie.theaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.screeningmovie.AdapterClickListener

class TheaterAdapter(
    private val items: List<TheaterUiModel>,
    private val clickListener: AdapterClickListener,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    class TheaterViewHolder(itemView: View, private val clickListener: AdapterClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val navigateButton =
            itemView.findViewById<ImageButton>(R.id.btn_theater_navigate_next)
        private val name = itemView.findViewById<TextView>(R.id.tv_theater_name)
        private val timeCount = itemView.findViewById<TextView>(R.id.tv_theater_time_count)

        fun onBind(item: TheaterUiModel) {
            name.text = item.name
            timeCount.text = item.timeCount
            navigateButton.setOnClickListener {
                clickListener.onClick(item.id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_theater, parent, false)

        return TheaterViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.onBind(items[position])
    }
}
