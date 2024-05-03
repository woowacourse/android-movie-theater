package woowacourse.movie.common.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import woowacourse.movie.ErrorActivity

fun Activity.redirectToErrorActivity() {
    val intent = Intent(this, ErrorActivity::class.java).apply {
        putExtra("ERROR_MESSAGE", "필요한 데이터를 불러오는 데 실패했습니다.")
    }
    startActivity(intent)
    finish()
}
