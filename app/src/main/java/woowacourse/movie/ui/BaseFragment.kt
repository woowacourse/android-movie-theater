package woowacourse.movie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected abstract val presenter: BaseContract.Presenter
    protected abstract val binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding()
        initPresenter()
        return binding.root
    }

    protected abstract fun initPresenter()
    protected abstract fun initBinding()
}
