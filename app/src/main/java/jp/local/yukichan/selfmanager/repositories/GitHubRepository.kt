package jp.local.yukichan.selfmanager.repositories

import jp.local.yukichan.selfmanager.application.CustomApplication
import jp.local.yukichan.selfmanager.data.Repo

/**
 * Created by takamk2 on 18/03/05.
 *
 * The Edit Fragment of Base Class.
 */
class GitHubRepository(application: CustomApplication) :Repository {

    private val gitHubService = application.gitHubService

    suspend fun getRepoList(): List<Repo>? {
        val response = gitHubService.listRepos("takamk2").execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
