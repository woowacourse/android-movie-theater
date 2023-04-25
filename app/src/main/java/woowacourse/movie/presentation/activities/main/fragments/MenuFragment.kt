package woowacourse.movie.presentation.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    companion object {
        private val menuFragment = MenuFragment()

        fun newInstance(): MenuFragment = menuFragment.apply {
            arguments = Bundle().apply {
            }
        }
    }
}
