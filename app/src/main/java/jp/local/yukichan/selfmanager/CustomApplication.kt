package jp.local.yukichan.selfmanager

import android.app.Application
import android.arch.persistence.room.Room
import jp.local.yukichan.selfmanager.room.database.AppDatabase
import jp.local.yukichan.selfmanager.room.database.AppDatabase.Companion.DB_NAME
import timber.log.Timber

/**
 * Created by takamk2 on 18/03/01.
 *
 * The Edit Fragment of Base Class.
 */
class CustomApplication : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // DEBUG
        deleteDatabase(AppDatabase.DB_NAME)
        db = Room.databaseBuilder(this, AppDatabase::class.java, DB_NAME).build()
    }
}