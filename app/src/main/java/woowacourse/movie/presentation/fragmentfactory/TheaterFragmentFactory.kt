package woowacourse.movie.presentation.fragmentfactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import woowacourse.movie.model.data.local.preference.SettingPreference
import woowacourse.movie.presentation.settings.SettingsFragment

class TheaterFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SettingsFragment::class.java.name -> SettingsFragment(getSettingStorage = {
                SettingPreference(it)
            })
            else -> super.instantiate(classLoader, className)
        }
    }
}
