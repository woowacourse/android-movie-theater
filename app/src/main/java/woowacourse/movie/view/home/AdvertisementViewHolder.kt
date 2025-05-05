package woowacourse.movie.view.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class AdvertisementViewHolder(
    view: View,
    advertisementClickListener: () -> Unit,
) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            advertisementClickListener.invoke()
        }
    }
}
