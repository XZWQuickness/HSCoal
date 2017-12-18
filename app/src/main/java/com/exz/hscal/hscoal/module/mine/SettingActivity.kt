package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.login.EditPwdActivity
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.exz.hscal.hscoal.utils.DataCleanManager
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.widget.MyWebActivity
import com.exz.hscal.hscoal.widget.MyWebActivity.Intent_Title
import com.exz.hscal.hscoal.widget.MyWebActivity.Intent_Url
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.PreferencesService
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_setting.*



/**
 * Created by pc on 2017/12/15.
 * 设置
 */

class SettingActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {
        mTitle.text = "设置"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_setting
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        val data = DataCleanManager.getTotalCacheSize(mContext)
        tv_clear_cache.setText(if(TextUtils.isEmpty(data)) "" else data)
        bt_editPwd.setOnClickListener(this)
        bt_user_agreement.setOnClickListener(this)
        bt_about_us.setOnClickListener(this)
        bt_clear_cache.setOnClickListener(this)
        bt_usinghelp.setOnClickListener(this)
        bt_finish.setOnClickListener(this)
    }
    override fun onClick(p0: View) {
        when (p0) {
            bt_editPwd -> {
                startActivity(Intent(mContext,EditPwdActivity::class.java))
            }
            bt_user_agreement -> {//用户协议
                startActivity(Intent(mContext,MyWebActivity::class.java).putExtra(Intent_Url,"http://www.baidu.com/").putExtra(Intent_Title,"用户协议"))
            }
            bt_about_us -> {//关于我们
                startActivity(Intent(mContext,MyWebActivity::class.java).putExtra(Intent_Url,"http://www.baidu.com/").putExtra(Intent_Title,"关于我们"))
            }
            bt_clear_cache->{//清除缓存
                DialogUtils.clearCache(mContext,{
                    DataCleanManager.clearAllCache(mContext)
                    tv_clear_cache.setText("0.0K");
                })
            }
            bt_usinghelp -> {//关于我们
                startActivity(Intent(mContext,MyWebActivity::class.java).putExtra(Intent_Url,"http://www.baidu.com/").putExtra(Intent_Title,"使用帮助"))
            }
            bt_finish->{//退出当前账号
                PreferencesService.saveAccount(this, PreferencesService.getAccountKey(this) ?: "", "")
                MyApplication.user = null
                setResult(LoginActivity.RESULT_LOGIN_CANCELED)
                onBackPressed()
            }
        }
    }
}
