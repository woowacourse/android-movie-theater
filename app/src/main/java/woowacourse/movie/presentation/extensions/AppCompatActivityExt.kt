package woowacourse.movie.presentation.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

fun AppCompatActivity.showBackButton(isShow: Boolean = true) {
    supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
}

fun AppCompatActivity.replaceFragment(@IdRes containerViewId: Int, fragment: Fragment) {
    supportFragmentManager.commit { replace(containerViewId, fragment) }
}
