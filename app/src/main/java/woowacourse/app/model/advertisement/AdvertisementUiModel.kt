package woowacourse.app.model.advertisement

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import woowacourse.app.model.MainData
import woowacourse.app.ui.main.home.adapter.MainViewType

data class AdvertisementUiModel(
    override val id: Long,
    val link: String,
    @DrawableRes val image: Int,
) : MainData() {
    override val mainViewType: MainViewType = MainViewType.ADVERTISEMENT

    fun getIntent(): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(link))
    }
}
