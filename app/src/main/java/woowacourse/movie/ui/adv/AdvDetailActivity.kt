package woowacourse.movie.ui.adv

import android.content.Context
import android.content.Intent
import woowacourse.movie.databinding.ActivityAdvDetailBinding
import woowacourse.movie.model.AdvState
import woowacourse.movie.ui.base.BaseBackKeyActionBarActivity
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class AdvDetailActivity : BaseBackKeyActionBarActivity(), AdvDetailContract.View {
    override lateinit var presenter: AdvDetailContract.Presenter
    override lateinit var binding: ActivityAdvDetailBinding

    override fun initBinding() {
        binding = ActivityAdvDetailBinding.inflate(layoutInflater)
    }

    override fun initPresenter() {
        presenter = AdvDetailPresenter(
            this,
            intent.getParcelableExtraCompat(KEY_ADV) ?: return keyError(KEY_ADV)
        )
    }

    override fun showAdv(advState: AdvState) {
        binding.adv = advState
    }

    companion object {
        private const val KEY_ADV = "key_adb"

        fun startActivity(context: Context, advState: AdvState) {
            val intent = Intent(context, AdvDetailActivity::class.java).apply {
                putExtra(KEY_ADV, advState)
            }
            context.startActivity(intent)
        }
    }
}
