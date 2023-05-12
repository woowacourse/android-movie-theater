package woowacourse.movie.presentation.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import woowacourse.movie.presentation.extensions.showBackButton

abstract class BaseActivity<V : ViewDataBinding>(
    private val isShowBackButton: Boolean = false,
) : AppCompatActivity(), BaseContract.View {

    abstract val layoutResId: Int
    open val onClickBackButton: () -> Unit = { finish() }

    private var _binding: V? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        showBackButton(isShowBackButton)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onClickBackButton()
        }
        return true
    }
}
