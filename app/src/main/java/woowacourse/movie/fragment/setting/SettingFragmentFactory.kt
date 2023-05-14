package woowacourse.movie.fragment.setting

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class SettingFragmentFactory(
    private val activityResultLauncher: ActivityResultLauncher<String>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SettingFragment::class.java.name -> SettingFragment(activityResultLauncher)
            else -> super.instantiate(classLoader, className)
        }
    }
}
