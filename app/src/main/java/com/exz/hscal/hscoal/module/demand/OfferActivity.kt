package com.exz.hscal.hscoal.module.demand

import android.text.TextUtils
import android.view.View
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_offer.*

/**
 * Created by pc on 2017/12/12.
 * 报价
 */

class OfferActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {
        mTitle.text = "填写报价"
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
        return R.layout.activity_offer
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        bt_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            bt_submit -> {
                var price = ed_price.text.toString().trim()
                if (TextUtils.isEmpty(price)) {
                    ed_price.setShakeAnimation()
                    return
                }
                var content = ed_content.text.toString().trim()
                if (TextUtils.isEmpty(content)) {
                    ed_content.setShakeAnimation()
                    return
                }
                finish()
            }
        }
    }
}
