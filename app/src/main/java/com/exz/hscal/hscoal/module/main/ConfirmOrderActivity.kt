package com.exz.hscal.hscoal.module.main

import android.content.Intent
import android.view.View
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.mine.address.AddressChooseActivity
import com.exz.hscal.hscoal.utils.DialogUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_confirm_order.*

/**
 * Created by pc on 2017/12/12.
 * 确认订单
 */

class ConfirmOrderActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {
        mTitle.text = "确认订单"
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
        return R.layout.activity_confirm_order
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
        add.setOnClickListener(this)
        count.setOnClickListener(this)
        minus.setOnClickListener(this)
        tv_name.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        var mCount = count.text.toString().trim().toLong()
        when (p0) {
            tv_name -> {
                startActivity(Intent(mContext, AddressChooseActivity::class.java))
            }
            add -> {
                mCount++
                count.text = mCount.toString()

            }
            count -> {
                DialogUtils.changeNum(mContext, mCount, {
                    if (it != null) {
                        count.text = it.toString()
                    }
                })
            }
            minus -> {
                if (mCount > 1) {
                    mCount--
                    count.text = mCount.toString()
                }
            }
        }
    }

    companion object {
        var Intent_Type = "intent_type" // 1 物流配送 2 到场自提
    }
}
