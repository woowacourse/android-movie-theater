package woowacourse.movie.presentation.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {
        private val historyFragment = HistoryFragment()

        fun newInstance(): HistoryFragment = historyFragment.apply {
            arguments = Bundle().apply {
            }
        }
    }
}
