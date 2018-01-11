package com.exz.hscal.hscoal.module.mine.sellorder

import android.text.TextUtils
import android.view.View
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_cofirm_payment.*
import kotlinx.android.synthetic.main.activity_confirm_order.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2018/1/11.
 */

class ConfirmPaymentActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {

        mTitle.text = "确认付款"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_cofirm_payment
    }

    override fun init() {
        super.init()

        initEvent()

    }



    private fun initEvent() {
        llTitme.setOnClickListener(this)
        tv_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            llTitme -> {

            }
            tv_submit -> {

            }
        }
    }

    companion object {
        var Intent_Order_Id = "orderId"
    }
}
