package jp.local.yukichan.selfmanager.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay

/**
 * Created by takamk2 on 18/02/28.
 *
 * The Edit Fragment of Base Class.
 */
class MainActivityViewModel : ViewModel() {

    val currentName = MutableLiveData<String>()

    suspend fun update() = async(CommonPool) {
        updateEntity()
    }

    private suspend fun updateEntity() {
        delay(5000)
        currentName.postValue(Math.random().toString())
    }
}