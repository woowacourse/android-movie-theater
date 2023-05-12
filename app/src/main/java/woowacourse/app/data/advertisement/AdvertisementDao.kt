package woowacourse.app.data.advertisement

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.app.data.CgvContract.Advertisement.TABLE_COLUMN_LINK
import woowacourse.app.data.CgvContract.Advertisement.TABLE_NAME
import woowacourse.app.data.CgvDbHelper

class AdvertisementDao(context: Context) : AdvertisementDataSource {
    private val cgvDb by lazy { CgvDbHelper.getInstance(context).readableDatabase }

    override fun getAdvertisementEntities(): List<AdvertisementEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val cursor = makeCursor(null, null, orderBy)
        val advertisements = readAdvertisements(cursor)
        cursor.close()
        return advertisements
    }

    override fun getAdvertisementEntity(advertisementId: Long): AdvertisementEntity? {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$advertisementId")
        val cursor = makeCursor(selection, selectionArgs, null)
        val advertisement = readAdvertisement(cursor)
        cursor.close()
        return advertisement
    }

    override fun addAdvertisementEntity(link: String): AdvertisementEntity {
        val data = ContentValues()
        data.put(TABLE_COLUMN_LINK, link)
        val advertisementId = cgvDb.insert(TABLE_NAME, null, data)
        return getAdvertisementEntity(advertisementId)!!
    }

    private fun readAdvertisements(cursor: Cursor): List<AdvertisementEntity> {
        val advertisements = mutableListOf<AdvertisementEntity>()
        while (true) {
            val advertisement: AdvertisementEntity = readAdvertisement(cursor) ?: break
            advertisements.add(advertisement)
        }
        return advertisements
    }

    private fun readAdvertisement(cursor: Cursor): AdvertisementEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val link = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_LINK))
        return AdvertisementEntity(id, link)
    }

    private fun makeCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        orderBy: String?,
    ): Cursor {
        return cgvDb.query(
            TABLE_NAME,
            arrayOf(BaseColumns._ID, TABLE_COLUMN_LINK),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }

    fun closeDb() {
        cgvDb.close()
    }
}
