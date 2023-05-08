package woowacourse.movie.presentation.binding.adapter

import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R

@BindingAdapter("app:onChangedHistory", "app:onChangedHome", "app:onChangedSetting")
fun BottomNavigationView.setMainNavItemStateChange(
    onChangedHistory: () -> Unit,
    onChangedHome: () -> Unit,
    onChangedSetting: () -> Unit,
) {
    setOnItemSelectedListener { menu ->
        when (menu.itemId) {
            R.id.history -> onChangedHistory()
            R.id.home -> onChangedHome()
            R.id.setting -> onChangedSetting()
        }
        return@setOnItemSelectedListener true
    }
}

@BindingAdapter("app:selectedMenuId")
fun BottomNavigationView.setSelectedMenuId(
    @IdRes menuId: Int,
) {
    selectedItemId = menuId
}
