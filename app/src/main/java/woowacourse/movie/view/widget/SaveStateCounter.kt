package woowacourse.movie.view.widget

import android.os.Bundle
import woowacourse.movie.domain.model.Count

class SaveStateCounter(val counter: Counter, override val saveStateKey: String) : SaveState {

    override fun save(outState: Bundle) {
        outState.putInt(saveStateKey, counter.count.value)
    }

    override fun load(savedInstanceState: Bundle?): Count {
        return if (savedInstanceState == null) {
            Count(1)
        } else {
            Count(savedInstanceState.getInt(saveStateKey))
        }
    }
}
