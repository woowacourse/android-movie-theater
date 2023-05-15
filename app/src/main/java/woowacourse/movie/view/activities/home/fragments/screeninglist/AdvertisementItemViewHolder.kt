package woowacourse.movie.view.activities.home.fragments.screeninglist

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.AdvertisementUIState

class AdvertisementItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val advertisementImageView: ImageView = view.findViewById(R.id.advertisement_iv)

    fun bind(advertisementUIState: AdvertisementUIState) {
        advertisementImageView.setImageResource(advertisementUIState.advertisementImage)
    }
}
