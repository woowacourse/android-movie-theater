package woowacourse.movie.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.BookingDetailActivity
import woowacourse.movie.R

class TheaterFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_theater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.btn_select_theater).setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                val intent = Intent(activity, BookingDetailActivity::class.java)
                startActivity(intent)
                addToBackStack(null)
                dismiss()
            }
        }
    }

    companion object {
        const val KEY_THEATERS = "theatersData"
    }
}
