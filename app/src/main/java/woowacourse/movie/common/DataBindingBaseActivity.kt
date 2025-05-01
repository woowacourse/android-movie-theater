package woowacourse.movie.common

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import woowacourse.movie.R

abstract class DataBindingBaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutRes: Int @LayoutRes get
    abstract var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen()
    }

    protected fun setupScreen() {
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
