package jp.local.yukichan.selfmanager.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import jp.local.yukichan.selfmanager.R
import jp.local.yukichan.selfmanager.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        initEvents()
        initObservesForVM()
    }

    private fun initEvents() {
        RxView.clicks(btClick).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            launch(UI) {
                btClick.isEnabled = false
                viewModel.addRandomUser().await()
                btClick.isEnabled = true
            }
        }
    }

    private fun initObservesForVM() {
        viewModel.currentName.observe(this, Observer {
            tvName.text = it ?: "None"
        })

        viewModel.userList.observe(this, Observer {
            if (it != null) {
                tvName.text = "${it.last().id}:${it.last().firstName} ${it.last().lastName}"
            } else {
                tvName.text = "error" // TODO: errorハンドリングはnullかどうかで判定しない
            }
        })

        viewModel.userList.observe(this, Observer {
            Toast.makeText(this, "count: ${it?.count()}", Toast.LENGTH_SHORT).show()
        })

        viewModel.randomUser.observe(this, Observer {
            it?.results?.forEach { result ->
                tvName.text = "${result.name.first} ${result.name.last}"
            }
        })
    }
}
