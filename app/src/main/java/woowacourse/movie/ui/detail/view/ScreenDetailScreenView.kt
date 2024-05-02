package woowacourse.movie.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.HolderScreenDetailScreenBinding
import woowacourse.movie.ui.ScreenDetailUI

class ScreenDetailScreenView(context: Context, attrs: AttributeSet? = null) : ScreenDetailView,
    ConstraintLayout(context, attrs) {
    private val binding: HolderScreenDetailScreenBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.holder_screen_detail_screen, this, true)

    override fun show(screen: ScreenDetailUI) {
        binding.screenDetailUi = screen
    }
}
