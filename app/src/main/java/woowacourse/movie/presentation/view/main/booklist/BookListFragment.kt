package woowacourse.movie.presentation.view.main.booklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import woowacourse.movie.R

class BookListFragment : Fragment(R.layout.fragment_book_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(view, ReservationRepository.findAll())
    }

    private fun setRecyclerView(view: View, reservations: List<Reservation>) {
        view.findViewById<RecyclerView>(R.id.rv_book_list).adapter =
            BookingListAdapter(reservations)
    }
}
