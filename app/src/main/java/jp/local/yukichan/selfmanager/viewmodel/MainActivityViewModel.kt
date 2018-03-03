package jp.local.yukichan.selfmanager.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import jp.local.yukichan.selfmanager.data.RandomUser
import jp.local.yukichan.selfmanager.extensions.app
import jp.local.yukichan.selfmanager.local.entity.User
import jp.local.yukichan.selfmanager.repositories.RandomUserRepository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

/**
 * Created by takamk2 on 18/02/28.
 *
 * The Edit Fragment of Base Class.
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val userList = MutableLiveData<List<User>>()
    val currentName = MutableLiveData<String>()
    val randomUser = MutableLiveData<RandomUser>()

    private val randomUserRepository = RandomUserRepository(application.app())

    private val userDao = application.app().db.userDao() // TODO: to repository

    suspend fun addRandomUser() = async(CommonPool) {
        randomUser.postValue(randomUserRepository.getRandomUser())
    }
}