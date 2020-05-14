package com.danny.roomdb

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(app: Application): AndroidViewModel(app) {

    lateinit var liveData: LiveData<List<User>>
    private var userDao: UserDao = MyDatabase.instance(app).userDao()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
    init {
        liveData = userDao.getAll()
    }


    fun insertUser(user: User) {
        userDao.insertAll(user)
    }

    @SuppressLint("CheckResult")
    fun insertUser(id: Int, firstName: String, lastName: String, callback: (() -> Unit)? = null) {
        disposable.add(
        userDao.insert(User(id, firstName, lastName))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ( {
                callback?.invoke()
                Log.d("myInfo insert ", "success")
            } , { error -> Log.e("myInfo", error.message) }))
    }

    fun getUser() {
        disposable.add(
            userDao.all()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ( {data ->
                    Log.d("myInfo size = ","${data.size}")
                } , { error -> Log.e("myInfo", error.message) })
//        disposable.dispose()
        )
    }
}