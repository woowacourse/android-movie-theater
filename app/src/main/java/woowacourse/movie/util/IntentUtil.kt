package woowacourse.movie.util

import android.content.Intent
import android.os.Build
import woowacourse.movie.detail.model.Count
import woowacourse.movie.detail.view.DetailActivity.Companion.EXTRA_COUNT_KEY

object IntentUtil {
    fun getSerializableCountData(intent: Intent): Count {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_COUNT_KEY, Count::class.java) ?: Count(1)
        } else {
            intent.getSerializableExtra(EXTRA_COUNT_KEY) as Count
        }
    }
}
