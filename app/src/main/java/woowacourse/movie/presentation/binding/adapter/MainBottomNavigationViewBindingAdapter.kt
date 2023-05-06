package woowacourse.movie.presentation.binding.adapter

import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.views.main.contract.MainContract

@BindingAdapter("app:presenter")
fun BottomNavigationView.setPresenter(
    presenter: MainContract.Presenter,
) {
    setOnItemSelectedListener { menu ->
        when (menu.itemId) {
            R.id.history -> presenter.onShowHistoryScreen()
            R.id.home -> presenter.onShowHomeScreen()
            R.id.setting -> presenter.onShowSettingScreen()
            else -> {}
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
