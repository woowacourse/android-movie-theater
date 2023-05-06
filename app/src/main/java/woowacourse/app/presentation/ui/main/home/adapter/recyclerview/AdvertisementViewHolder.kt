package woowacourse.app.presentation.ui.main.home.adapter.recyclerview

import android.content.Intent
import android.view.View
import android.widget.ImageView
import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.model.advertisement.AdvertisementUiModel
import woowacourse.app.presentation.ui.main.home.adapter.HomeViewType
import woowacourse.movie.R

class AdvertisementViewHolder(view: View) : HomeViewHolder(view) {
    private val poster: ImageView = view.findViewById(R.id.imageAdvertisement)

    override val homeViewType: HomeViewType = HomeViewType.ADVERTISEMENT
    private lateinit var advertisement: AdvertisementUiModel

    override fun onBind(data: HomeData) {
        advertisement = data as AdvertisementUiModel
        poster.setImageResource(advertisement.image)
    }

    fun setAdvertisementClick(clickAd: (Intent) -> Unit) {
        view.setOnClickListener {
            clickAd(advertisement.getIntent())
        }
    }
}
