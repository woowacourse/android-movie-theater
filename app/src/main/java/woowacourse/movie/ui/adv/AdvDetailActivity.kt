package woowacourse.movie.ui.adv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.main.MainActivity.Companion.KEY_ADV
import woowacourse.movie.model.AdvState
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class AdvDetailActivity : BackKeyActionBarActivity() {

    private val advImage: ImageView by lazy { findViewById(R.id.adv_detail_img) }
    private val advDescription: TextView by lazy { findViewById(R.id.adv_description) }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_adv_detail)

        val advState: AdvState =
            intent.getParcelableExtraCompat(KEY_ADV) ?: return keyError(KEY_ADV)
        advImage.setImageResource(advState.imgId)
        advDescription.text = advState.advDescription
    }

    companion object {

        fun startActivity(context: Context, adbState: AdvState) {
            val intent = Intent(context, AdvDetailActivity::class.java).apply {
                putExtra(KEY_ADV, adbState)
            }
            context.startActivity(intent)
        }
    }
}
