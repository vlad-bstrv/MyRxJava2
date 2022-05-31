package com.vladbstrv.myrxjava2

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class Creation {

    fun exec() {
        Consumer(Producer()).exec()
    }

    /**Observable «генерирует» некоторый поток данных и уведомляет об
    этом либо о каком-либо другом событии подписчика Observer */
    class Producer {
        //Создаем Observable(поток) разными способами
        fun fromIterable(): Observable<String> {
            return Observable.fromIterable(listOf("1", "2", "3"))
        }
    }

    /**Observer — интерфейс.
    Реализация интерфейса Observer — класс, объект которого лишь «подписывается» на
    Observable */
    class Consumer(private val producer: Producer) {
        private val stringObserver = object :Observer<String> {
            var disposable: Disposable? = null
            override fun onSubscribe(d: Disposable) {
                disposable = d
                Log.d(TAG, "onSubscribe() called with: d = $d")
            }

            override fun onNext(t: String) {
                Log.d(TAG, "onNext() called with: t = $t")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError() called with: e = ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete() called")
            }

        }

        fun execJust() {
            producer.fromIterable().subscribe(stringObserver)
        }

        private fun execLambda() {
            producer.fromIterable()
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