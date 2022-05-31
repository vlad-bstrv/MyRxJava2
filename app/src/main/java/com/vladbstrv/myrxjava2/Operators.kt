package com.vladbstrv.myrxjava2

import android.util.Log
import io.reactivex.rxjava3.core.Observable

class Operators {

    fun exec() {
        Consumer(Producer()).exec()
    }
    class Producer {
        fun createJust() = Observable.just("1", "2", "3", "3")
    }
    class Consumer(val producer: Producer) {
        fun exec() {
            execDistinct()
        }

        fun execTake() {
            producer.createJust()
                .skip(2)
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        fun execMap() {
            producer.createJust()
                .map { it + it }
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        fun execDistinct() {
            producer.createJust()
                .distinct()
                .subscribe({ s ->
                    Log.d(TAG, "onNext: $s")
                }, {
                    Log.d(TAG, "onError: ${it.message}")
                })
        }

        }

    companion object {
        const val TAG = "TAG"
    }

}