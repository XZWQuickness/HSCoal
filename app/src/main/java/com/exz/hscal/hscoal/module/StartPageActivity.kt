package com.exz.hscal.hscoal.module

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.User
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.PreferencesService
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_start_page.*


/**
 * Created by pc on 2017/12/4.
 */

class StartPageActivity : BaseActivity() {
    private var type = 1
    override fun initToolbar(): Boolean {
        StatusBarUtil.immersive(this)
        return false
    }


    override fun setInflateId(): Int {
        return R.layout.activity_start_page
    }

    override fun init() {
        super.init()
        initView()
    }


    private fun initView() {

        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }

//        PermissionLocationWithCheck(Intent(this, LocationService::class.java),true)
        val anim = AnimationUtils.loadAnimation(this, R.anim.logo_fade_in)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                login()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                val preferences = getSharedPreferences("CPM",
                        Context.MODE_PRIVATE)
                val flag = preferences.getBoolean("FirstRun", false)
                if (flag) {
                    val editor = preferences.edit()
                    editor.putBoolean("FirstRun", false)
                    editor.apply()
                    //                    LogoActivity.this.startActivity(new Intent(
                    //                            LogoActivity.this, FirstRunActivity.class));
                    //                    finish();
                } else {

//                    type = 1
//                    jump(type)
                }
            }
        })
        img_logo.animation = anim

    }

    /**
     * 登录
     * */
    fun login() {
        DataCtrlClass.loginNoDialog(PreferencesService.getAccountKey(this) ?: "", PreferencesService.getAccountValue(this) ?: "") {
            if (it != null) {
                LoginActivity.loginSuccess(this, PreferencesService.getAccountKey(this) ?: "", PreferencesService.getAccountValue(this) ?: "", User(it))
            }else{
                MyApplication.user = null
            }
            jump(type)
        }
    }

    /**
     * @param type 0 主界面，1， 登录
     */
    private fun jump(type: Int) {
        var intent: Intent? = null
        if (type == 0) {
            intent = Intent(this, MainActivity::class.java)
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            //            intent = new Intent(LogoActivity.this, LoginActivity.class);
        }
        if (intent != null)
            startActivity(intent)
        this.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
