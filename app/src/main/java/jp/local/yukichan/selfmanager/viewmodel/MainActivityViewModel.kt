package jp.local.yukichan.selfmanager.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import jp.local.yukichan.selfmanager.data.RandomUser
import jp.local.yukichan.selfmanager.data.Repo
import jp.local.yukichan.selfmanager.data.TalkResult
import jp.local.yukichan.selfmanager.extensions.app
import jp.local.yukichan.selfmanager.local.entity.User
import jp.local.yukichan.selfmanager.repositories.GitHubRepository
import jp.local.yukichan.selfmanager.repositories.RandomUserRepository
import jp.local.yukichan.selfmanager.repositories.TalkRepository
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
    val talk = MutableLiveData<TalkResult>()
    val repos = MutableLiveData<List<Repo>>()

    private val randomUserRepository = RandomUserRepository(application.app())
    private val talkRepository = TalkRepository(application.app())
    private val gitHubRepository = GitHubRepository(application.app())

    suspend fun addRandomUser() = async(CommonPool) {
        randomUser.postValue(randomUserRepository.getRandomUser())
    }

    suspend fun talk(message: String) = async(CommonPool) {
        talk.postValue(talkRepository.talk(message))
//        repos.postValue(gitHubRepository.getRepoList())
    }
}