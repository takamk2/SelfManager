package jp.local.yukichan.selfmanager.activities

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import jp.local.yukichan.selfmanager.R
import jp.local.yukichan.selfmanager.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import android.speech.tts.UtteranceProgressListener
import jp.local.yukichan.selfmanager.application.CustomApplication
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var tts: TextToSpeech

    @Inject
    lateinit var locationManager: LocationManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CustomApplication.applicationComponent.inject(this)

        Toast.makeText(this, "locationManager=$locationManager", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            Toast.makeText(this, "init:${it}", Toast.LENGTH_SHORT).show()
        })

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

        RxView.clicks(btTalk).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            launch(UI) {
                btTalk.isEnabled = false
                viewModel.talk(etTalk.text.toString()).await()
                btTalk.isEnabled = true
            }
        }
    }

    private fun speechText(message: String) {
        if (tts.isSpeaking) {
            tts.stop()
            return
        }
        tts.setSpeechRate(1.5f)
        tts.setPitch(2.0f)
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "messageID")

        val listenerResult = tts.setOnUtteranceProgressListener(object: UtteranceProgressListener() {
            override fun onDone(utteranceId: String?) {
                Timber.d("DEBUG:: onDone utteranceId=$utteranceId")
            }

            override fun onError(utteranceId: String?) {
                Timber.d("DEBUG:: onError utteranceId=$utteranceId")
            }

            override fun onStart(utteranceId: String?) {
                Timber.d("DEBUG:: onStart utteranceId=$utteranceId")
            }
        })
        Timber.d("DEBUG:: listenerResult=$listenerResult")
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

        viewModel.talk.observe(this, Observer {
            var message = ""
            it?.results?.forEach {
                message = message.plus(it.reply)
            }
            message += "にゃん!"
            tvTalkRespond.text = message
            speechText(message)
        })

        viewModel.repos.observe(this, Observer {
            val sb = StringBuffer()
            it?.forEach {
                sb.append(it.name)
                sb.append("\n")
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show()
        })
    }
}
