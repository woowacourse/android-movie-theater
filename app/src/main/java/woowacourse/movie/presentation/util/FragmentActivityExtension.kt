package woowacourse.movie.presentation.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace

inline fun <reified T : Fragment> FragmentActivity.replace(@IdRes frameId: Int) {
    this.supportFragmentManager.commit {
        setReorderingAllowed(true)
        replace<T>(frameId)
    }
}
