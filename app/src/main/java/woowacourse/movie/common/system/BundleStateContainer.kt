package woowacourse.movie.common.system

import android.os.Bundle

class BundleStateContainer(val bundle: Bundle) : StateContainer {
    override fun save(key: String, value: Int) {
        bundle.putInt(key, value)
    }

    override fun load(key: String): Int {
        return bundle.getInt(key)
    }
}
