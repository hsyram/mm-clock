package com.marypeak.mmclock

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    private val fullScreenSubject = BehaviorSubject.create<Boolean>()

    fun getFullScreenObservable(): Observable<Boolean> {
        return fullScreenSubject
    }

    fun toggle() {
        val oldState = fullScreenSubject.value ?: false
        fullScreenSubject.onNext(!oldState)
    }

    fun fullScreen(delayMillis: Long) {
        Completable.timer(delayMillis, TimeUnit.MILLISECONDS)
            .subscribeBy(  // named arguments for lambda Subscribers
                onError = { it.printStackTrace() },
                onComplete = { fullScreenSubject.onNext(true) }
            )
    }

    fun exitFullScreen() {
        fullScreenSubject.onNext(false)
    }

    override fun onCleared() {
        super.onCleared()
    }
}