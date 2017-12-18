package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.text.InputType
import android.text.TextUtils
import android.view.View
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.mine.UserInfoActivity.Companion.Intent_Text
import com.exz.hscal.hscoal.module.mine.UserInfoActivity.Companion.RESULTCODE_TEXT
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_userinfo_text.*

/**
 * Created by pc on 2017/12/5.
 *
 */

class UserInfoTextActivity : BaseActivity(), View.OnClickListener {


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
        return R.layout.activity_userinfo_text
    }

    override fun init() {
        initView()
        initEvent()
    }

    private fun initView() {

    if(intent.getStringExtra(Intent_ClassName).equals("联系人手机")||intent.getStringExtra(Intent_ClassName).equals("店铺电话")){
        ed_text.inputType= InputType.TYPE_CLASS_NUMBER
    }

        ed_text.setText(intent.getStringExtra(Intent_Text))
    }

    private fun initEvent() {
        bt_confirm.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.bt_confirm -> {
                var text = ed_text.text.toString().trim()
                if (TextUtils.isEmpty(text)) {
                    ed_text.setShakeAnimation()
                    return
                }
                setResult(RESULTCODE_TEXT, Intent().putExtra(Intent_Text, text))
                finish()
            }
        }
    }

    companion object {
        var Intent_ClassName = "ClassName"
    }
}
