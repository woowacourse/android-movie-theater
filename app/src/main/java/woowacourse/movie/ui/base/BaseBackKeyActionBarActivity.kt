package woowacourse.movie.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseBackKeyActionBarActivity : AppCompatActivity() {
    protected abstract val presenter: BaseContract.Presenter
    protected abstract val binding: ViewDataBinding

    @Deprecated("deprecated", ReplaceWith("onCreateView"), DeprecationLevel.WARNING)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initBinding()
        initPresenter()
        onCreateView(savedInstanceState)
        setContentView(binding.root)
    }

    protected abstract fun initPresenter()
    protected abstract fun initBinding()

    open fun onCreateView(savedInstanceState: Bundle?) {
        // do nothing
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
