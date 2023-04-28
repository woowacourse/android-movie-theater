package woowacourse.movie.presentation.view.main.home

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertiseBinding

class AdListViewHolder(
    rootView : View
) : RecyclerView.ViewHolder(rootView) {
    private val advertise = rootView.findViewById<ImageView>(R.id.iv_advertise)
    fun bind(ad: Drawable) {
        advertise.setImageDrawable(ad)
    }
}
