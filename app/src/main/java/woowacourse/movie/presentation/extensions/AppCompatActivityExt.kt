package woowacourse.movie.presentation.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit

fun AppCompatActivity.showBackButton(isShow: Boolean = true) {
    supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
}

inline fun <reified T : Fragment> AppCompatActivity.showFragmentByTag(
    @IdRes containerViewId: Int,
    tag: String,
) {
    supportFragmentManager.commit {
        setReorderingAllowed(true)
        supportFragmentManager.fragments.forEach(::hide)

        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            add<T>(containerViewId, tag)
        } else {
            show(fragment)
        }
    }
}
