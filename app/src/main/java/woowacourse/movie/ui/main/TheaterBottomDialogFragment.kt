package woowacourse.movie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.adapter.TheaterAdapter
import woowacourse.movie.data.mock.MockTheatersFactory
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import woowacourse.movie.databinding.FragmentTheaterBottomDialogBinding

class TheaterBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var adapter: TheaterAdapter

    private var _binding: FragmentTheaterBottomDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheaterBottomDialogBinding.inflate(inflater, container, false)

        setUpAdapter()
        setUpTheaters()

        return binding.root
    }

    private fun setUpAdapter() {
        adapter = TheaterAdapter()
        binding.theaterList.adapter = adapter
    }

    private fun setUpTheaters() {
        val theaters = MockTheatersFactory.generateTheaters()
        adapter.updateTheaterItems(theaters, ::setOnTheaterItemClick)
    }

    private fun setOnTheaterItemClick(theaterUiModel: TheaterUIModel) {
        setFragmentResult("requestKey", bundleOf("bundleKey" to theaterUiModel))
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}
