package woowacourse.app.ui.main.home.adapter.listview

import android.content.Intent
import android.view.View
import android.widget.ImageView
import woowacourse.app.model.MainData
import woowacourse.app.model.advertisement.AdvertisementUiModel
import woowacourse.app.ui.main.home.adapter.MainViewType
import woowacourse.movie.R

class AdvertisementViewHolder(view: View) : MainViewHolder(view) {
    private val poster: ImageView = view.findViewById(R.id.imageAdvertisement)

    override val mainViewType: MainViewType = MainViewType.ADVERTISEMENT
    private lateinit var advertisement: AdvertisementUiModel

    override fun onBind(data: MainData) {
        advertisement = data as AdvertisementUiModel
        poster.setImageResource(advertisement.image)
    }

    fun clickAdvertisement(clickAd: (Intent) -> Unit) {
        view.setOnClickListener {
            clickAd(advertisement.getIntent())
        }
    }
}
