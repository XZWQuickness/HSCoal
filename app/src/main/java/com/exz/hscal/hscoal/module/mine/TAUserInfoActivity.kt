package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_ta_userinfo.*

/**
 * Created by pc on 2018/1/17.
 * TA的用户信息
 */

class TAUserInfoActivity : BaseActivity() {
    override fun initToolbar(): Boolean {
        mTitle.text = intent.getStringExtra(Intent_ClassName)
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_ta_userinfo
    }

    override fun init() {
        super.init()
        getUserInfo()
    }

    private fun getUserInfo() {
        if (!MyApplication.checkUserLogin()) {
            startActivityForResult(Intent(mContext, LoginActivity::class.java), LoginActivity.RESULT_LOGIN_CANCELED)
            return
        }
        DataCtrlClass.getUserInfoTA(mContext,intent.getStringExtra(Intent_TA_Id)) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                iv_header.setImageURI(it.headImg)
                tv_nicekname.text=it.nickname
                tv_phone.text=it.mobile
                tv_telephone.text=it.tel
                tv_qq.text=it.qq
            }

        }
    }
companion object {
    var Intent_TA_Id="hisUserId"
    var Intent_ClassName="className"
}
}
