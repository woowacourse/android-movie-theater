package woowacourse.movie.presentation.base

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import woowacourse.movie.R

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : AppCompatActivity(layoutResId) {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)

        enableEdgeToEdge()
        setWindowInsets()
    }

    private fun setWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentFragment = getCurrentVisibleFragment()

        if (item.itemId == android.R.id.home) {
            if (currentFragment is HomeButtonHandler) {
                currentFragment.onHomePressed()
                return true
            }

            supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentVisibleFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments.reversed()) {
            if (fragment.isVisible) return fragment
        }
        return null
    }
}
