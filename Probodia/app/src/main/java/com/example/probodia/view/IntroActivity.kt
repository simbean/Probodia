package com.example.probodia.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.probodia.R
import com.example.probodia.databinding.ActivityIntroBinding
import com.example.probodia.viewmodel.IntroViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var viewModel: IntroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        viewModel = ViewModelProvider(this).get(IntroViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.autoLogin(::goMain)

        viewModel.liveKakaoLogin.observe(this, Observer {
            if (it) {
                kakaoLogin()
                viewModel.setKakaoLoginFalse()
            }
        })
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("KAKAOLOGIN", "Web Login : ${error.message}")
            } else if (token != null) {
                // 로그인 성공
                Log.e("KAKAOLOGIN", "LOGIN SUCCESS")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@IntroActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@IntroActivity) { token, error ->
                if (error != null) {
                    Log.e("KAKAOLOGIN", "Kakao Login : ${error.message}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(applicationContext, callback = callback)
                } else if (token != null) {
                    // 로그인 성공
                    Log.e("KAKAOLOGIN", "APP LOGIN SUCCESS")
                    goMain()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(applicationContext, callback = callback)
        }
    }

    fun goMain() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }
}