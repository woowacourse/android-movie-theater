package woowacourse.movie.view.widget

import android.os.Bundle
import woowacourse.movie.view.data.SeatsViewData
import woowacourse.movie.view.getSerializable

class SaveStateSeats(override val saveStateKey: String, val seatTableLayout: SeatTableLayout) :
    SaveState {
    override fun save(outState: Bundle) {
        outState.putSerializable(saveStateKey, seatTableLayout.selectedSeats())
    }

    override fun load(savedInstanceState: Bundle?): SeatsViewData {
        savedInstanceState ?: return SeatsViewData(listOf())
        return savedInstanceState.getSerializable<SeatsViewData>(saveStateKey)
            ?: return SeatsViewData(listOf())
    }
}
