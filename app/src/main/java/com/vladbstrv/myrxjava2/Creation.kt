package com.vladbstrv.myrxjava2

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class Creation {

    fun exec() {
        Consumer(Producer()).exec()
    }

    /**Observable «генерирует» некоторый поток данных и уведомляет об
    этом либо о каком-либо другом событии подписчика Observer */
    class Producer {
        //Создаем Observable(поток) разными способами
        fun interval(): Observable<Long> {
            return Observable.timer(10, TimeUnit.SECONDS)
        }
    }

    /**Observer — интерфейс.
    Реализация интерфейса Observer — класс, объект которого лишь «подписывается» на
    Observable */
    class Consumer(private val producer: Producer) {

        private fun execLambda() {
            producer.interval()
                .subscribe({
                    Log.d(TAG, "onNext: $it")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                }, {
                    Log.d(TAG, "onComplete")
                })
        }
        //подписываемся на Observable
        fun exec() {
            execLambda()
        }
    }

    companion object {
        const val TAG = "TAG"
    }
}