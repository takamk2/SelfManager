package jp.local.yukichan.selfmanager.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import jp.local.yukichan.selfmanager.extensions.app
import jp.local.yukichan.selfmanager.room.entity.User
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by takamk2 on 18/02/28.
 *
 * The Edit Fragment of Base Class.
 */
class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val userList = MutableLiveData<List<User>>()
    val currentName = MutableLiveData<String>()

    private val userDao = application.app().db.userDao() // TODO: to repository

    suspend fun insertUser(firstName: String, lastName: String) = async(CommonPool) {
        val user = User(firstName, lastName)
        currentName.postValue("Wait...")
        delay(3000)
        userDao.insertAll(user)
        userList.postValue(userDao.getAll())
    }
}