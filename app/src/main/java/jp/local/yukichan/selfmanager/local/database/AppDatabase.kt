package jp.local.yukichan.selfmanager.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import jp.local.yukichan.selfmanager.local.dao.UserDao
import jp.local.yukichan.selfmanager.local.entity.User

/**
 * Created by takamk2 on 18/03/01.
 *
 * The Edit Fragment of Base Class.
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "self_manager.db"
    }

    abstract fun userDao(): UserDao
}
