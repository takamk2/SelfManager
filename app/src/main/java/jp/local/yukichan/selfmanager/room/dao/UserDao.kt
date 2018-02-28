package jp.local.yukichan.selfmanager.room.dao

import android.arch.persistence.room.*
import jp.local.yukichan.selfmanager.room.entity.User

/**
 * Created by takamk2 on 18/02/28.
 *
 * The Edit Fragment of Base Class.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}