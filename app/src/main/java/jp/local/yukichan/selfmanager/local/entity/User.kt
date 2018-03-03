package jp.local.yukichan.selfmanager.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by takamk2 on 18/02/28.
 *
 * The Edit Fragment of Base Class.
 */
@Entity
data class User(var firstName: String, var lastName: String) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}

