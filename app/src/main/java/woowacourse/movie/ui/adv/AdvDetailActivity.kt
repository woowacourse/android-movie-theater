package woowacourse.movie.ui.adv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import woowacourse.movie.R
import woowacourse.movie.model.AdvState
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class AdvDetailActivity : BackKeyActionBarActivity() {
    private lateinit var advDetailView: AdvDetailView
    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_adv_detail)

        val advState: AdvState = intent.getParcelableExtraCompat(KEY_ADV)
            ?: return keyError(KEY_ADV)

        advDetailView = AdvDetailView(window.decorView.rootView, advState)
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
