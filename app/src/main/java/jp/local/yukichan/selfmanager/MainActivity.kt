package jp.local.yukichan.selfmanager

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import jp.local.yukichan.selfmanager.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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
    }
}
