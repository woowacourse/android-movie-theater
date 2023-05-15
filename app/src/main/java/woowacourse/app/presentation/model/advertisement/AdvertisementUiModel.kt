package woowacourse.app.presentation.model.advertisement

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.ui.main.home.adapter.HomeViewType

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
