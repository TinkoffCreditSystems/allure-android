package io.qameta.allure.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import rx.Observable
import rx.schedulers.Schedulers

class CrashTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        findViewById<Button>(R.id.crash_button).setOnClickListener {
            stackOverflow()
        }

        findViewById<Button>(R.id.rx_crash_button).setOnClickListener {
            rxCrash()
        }
    }

    private fun stackOverflow() {
        stackOverflow()
    }

    private fun rxCrash() {
        Observable.error<RuntimeException>(RuntimeException())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        /* onNext      */ {},
                        /* onError     */ {t:Throwable -> throw RuntimeException(t)},
                        /* onCompleted */ {})
    }
}
