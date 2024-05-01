package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.FragmentTheaterSelectionBottomSheetBinding
import woowacourse.movie.model.data.TheatersImpl
import woowacourse.movie.ui.home.adapter.TheaterAdapter

class TheaterSelectionBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterSelectionBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(MovieHomeKey.FRAGMENT_REQUEST_KEY) { _, bundle ->
            val movieContentId = bundle.getLong(MovieHomeKey.ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTheaterSelectionBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TheaterAdapter(TheatersImpl.findAll())
        binding.theaterList.adapter = adapter
    }
}
