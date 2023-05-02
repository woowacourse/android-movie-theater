package woowacourse.app.ui.main.adapter.recyclerview

import android.content.Intent
import android.view.View
import android.widget.ImageView
import woowacourse.app.model.main.AdvertisementUiModel
import woowacourse.app.model.main.MainData
import woowacourse.app.ui.main.adapter.MainViewType
import woowacourse.movie.R

class AdvertisementViewHolder(view: View) : MainViewHolder(view) {
    private val poster: ImageView = view.findViewById(R.id.imageAdvertisement)

    override val mainViewType: MainViewType = MainViewType.ADVERTISEMENT
    private lateinit var advertisement: AdvertisementUiModel

    override fun onBind(data: MainData) {
        advertisement = data as AdvertisementUiModel
        poster.setImageResource(advertisement.image)
    }

    fun setAdvertisementClick(clickAd: (Intent) -> Unit) {
        view.setOnClickListener {
            clickAd(advertisement.getIntent())
        }
    }
}
