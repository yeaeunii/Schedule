package com.example.schedule

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    lateinit var logout: ImageView
    lateinit var fl: FrameLayout
    lateinit var id: TextView

    var TAG = "kakaoLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout = findViewById(R.id.img_logout)
        fl = findViewById(R.id.fl)
        id = findViewById(R.id.email)

        supportFragmentManager.beginTransaction().replace(R.id.fl, Main_fm()).commit()

        //id 받아오기
        UserApiClient.instance.me { user, error ->
//            id.text = "${user?.id}"
            val preferences = getSharedPreferences("KaKao", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("userId", "${user?.id}")
            editor.apply()

        }


        logout.setOnClickListener {

            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

        }


    }
}