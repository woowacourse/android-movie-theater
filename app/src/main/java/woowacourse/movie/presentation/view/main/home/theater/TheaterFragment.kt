package woowacourse.movie.presentation.view.main.home.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.presentation.model.Theater

class TheaterFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private lateinit var binding: FragmentTheaterBinding
    private val presenter: TheaterContract.Presenter by lazy {
        TheaterPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getTheaters(requireArguments().getString(KEY)!!)
    }

    override fun showTheatersView(theaters: List<Theater>, movieSchedule: List<List<String>>) {
        binding.rvTheater.adapter = TheaterAdapter(theaters, movieSchedule)
    }

    companion object {
        private const val KEY = "key"
        fun newInstance(value: String) = TheaterFragment().apply {
            arguments = bundleOf().apply {
                putString(KEY, value)
            }
        }
    }
}