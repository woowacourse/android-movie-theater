package woowacourse.movie.presentation.main

import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.main.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), MainContract.View {
    private lateinit var presenter: MainPresenter

    override fun onCreateSetup() {
        binding.home = this
        presenter = MainPresenter(this)
        setContentView(binding.root)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNav.selectedItemId = R.id.home_fragment_item
        showFragment(HomeFragment())
        binding.bottomNav.setOnItemSelectedListener { item ->
            presenter.onBottomNavItemSelected(item.itemId)
            true
        }
    }

    override fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
