package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.db.DBHelper
import woowacourse.movie.data.entity.Reservations
import woowacourse.movie.data.storage.SettingsStorage

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SettingsStorage.init(this)

        val db = DBHelper(this).readableDatabase
        Reservations.restore(db)
        db.close()
    }
}
