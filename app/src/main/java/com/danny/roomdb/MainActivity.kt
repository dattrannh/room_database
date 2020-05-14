package com.danny.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelFactory(this)).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
//        viewModel.liveData.observe(this, Observer { data ->
//            Log.d("myInfo size = ","${data.size}")
//        })
//       viewModel.getUser()
//        Handler().postDelayed({
//            viewModel.insertUser("danny", "tran") {
//                Log.d("myInfo", "success")
//            }
//        }, 3000)

        Observable.interval(2, TimeUnit.SECONDS).subscribe{next ->
                        viewModel.insertUser(next.toInt(), "danny", "tran") {
//                Log.d("myInfo", "success")
            }
        }
    }


}
