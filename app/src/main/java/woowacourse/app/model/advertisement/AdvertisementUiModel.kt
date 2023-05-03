package woowacourse.app.model.advertisement

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import woowacourse.app.model.HomeData
import woowacourse.app.ui.main.home.adapter.HomeViewType

data class AdvertisementUiModel(
    override val id: Long,
    val link: String,
    @DrawableRes val image: Int,
) : HomeData() {
    override val homeViewType: HomeViewType = HomeViewType.ADVERTISEMENT

    fun getIntent(): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(link))
    }
}
