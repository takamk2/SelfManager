package jp.local.yukichan.selfmanager

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import jp.local.yukichan.selfmanager.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // binding
        viewModel.currentName.observe(this, Observer {
            tvName.text = it ?: "None"
            btClick.isEnabled = true
        })

        // event
        RxView.clicks(btClick).throttleFirst(1, TimeUnit.SECONDS).subscribe {
            launch(UI) {
                Log.d(javaClass.simpleName, "DEBUG:: throttle 1 sec")
                btClick.isEnabled = false
                viewModel.update().await()
            }
        }
    }
}
