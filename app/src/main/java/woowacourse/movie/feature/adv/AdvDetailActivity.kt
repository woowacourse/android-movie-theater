package woowacourse.movie.feature.adv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityAdvDetailBinding
import woowacourse.movie.feature.common.BackKeyActionBarActivity
import woowacourse.movie.model.AdvState
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class AdvDetailActivity : BackKeyActionBarActivity() {

    private lateinit var binding: ActivityAdvDetailBinding
    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_adv_detail)
        val advState: AdvState =
            intent.getParcelableExtraCompat(KEY_ADV) ?: return keyError(KEY_ADV)
        binding.adv = advState
    }

    companion object {
        fun getIntent(context: Context, adv: AdvState): Intent {
            val intent = Intent(context, AdvDetailActivity::class.java)
            intent.putExtra(KEY_ADV, adv)
            return intent
        }

        private const val KEY_ADV = "key_adv"
    }
}
