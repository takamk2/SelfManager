package jp.local.yukichan.selfmanager

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.jakewharton.rxbinding2.view.RxView
import jp.local.yukichan.selfmanager.samples.RandomUserApiService
import jp.local.yukichan.selfmanager.samples.RandomUserDemo
import jp.local.yukichan.selfmanager.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // relation viewModel
        viewModel.currentName.observe(this, Observer {
            tvName.text = it ?: "None"
        })

        viewModel.userList.observe(this, Observer {
            if (it != null) {
                tvName.text = "${it.last().id}:${it.last().firstName} ${it.last().lastName}"
            } else {
                tvName.text = "error" // TODO: errorハンドリングはnullかどうかで判定しない
            }
            btClick.isEnabled = true
        })

        viewModel.userList.observe(this, Observer {
            Toast.makeText(this, "count: ${it?.count()}", Toast.LENGTH_SHORT).show()
        })

        // event
        RxView.clicks(btClick).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            launch(UI) {
                Timber.d("DEBUG:: throttle 1 sec")
                btClick.isEnabled = false
                viewModel.insertUser("佐々木", "小次郎").await()
            }
        }

        // Async
        randomUserApiService.getUser().enqueue(object : Callback<RandomUserDemo> {
            override fun onResponse(call: Call<RandomUserDemo>?, response: Response<RandomUserDemo>?) {
                val results = response?.body()?.results
                if (results != null) {
                    for (result in results) {
                        Toast.makeText(this@MainActivity, "gender=${result.gender} email=${result.email}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<RandomUserDemo>?, t: Throwable?) {
            }
        })
    }

    private val randomUserApiService: RandomUserApiService = create(RandomUserApiService::class.java)

    private lateinit var retrofit: Retrofit

    private fun <S> create(serviceClass: Class<S>): S {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        // create retrofit
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://randomuser.me/") // Put your base URL
                .client(httpBuilder.build())
                .build()

        return retrofit.create(serviceClass)
    }

    private val httpBuilder: OkHttpClient.Builder
        get() {
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor({ chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                                .header("Accept", "application.json")
                                .method(original.method(), original.body())
                                .build()
                        return@addInterceptor chain.proceed(request)
                    })
                    .readTimeout(10, TimeUnit.SECONDS)

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)

            return httpClient
        }
}
