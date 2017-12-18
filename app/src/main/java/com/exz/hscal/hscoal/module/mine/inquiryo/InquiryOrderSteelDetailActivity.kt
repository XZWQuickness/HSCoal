package com.exz.hscal.hscoal.module.mine.inquiryo

import android.support.v4.content.ContextCompat
import android.view.View
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_inquiry_order_steel_detail.*

/**
 * Created by pc on 2017/12/14.
 * 询盘煤炭详情
 */

class InquiryOrderSteelDetailActivity : BaseActivity() {


    override fun initToolbar(): Boolean {
        mTitle.text = "询盘信息详情"
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
        return R.layout.activity_inquiry_order_steel_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
    }

    private fun initView() {

        when (intent.getStringExtra(Intent_State)) {
            "1" -> {//报价中
                tv_state.text=String.format(mContext.getString(R.string.inquriy_cocal_detail_state),"报价中")

            }
            "2" -> {//已确认
                tv_state.text=String.format(mContext.getString(R.string.inquriy_cocal_detail_state),"已确认")
                tv_confirm_time.visibility= View.VISIBLE
            }
            "3" -> {//已拒绝
                tv_state.text=String.format(mContext.getString(R.string.inquriy_cocal_detail_state),"已拒绝")
            }
            "4" -> {//已过期
                tv_state.text=String.format(mContext.getString(R.string.inquriy_cocal_detail_state),"已过期")
            }
        }

        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.colorPrimary))
    }

    private fun initEvent() {
    }


    companion object {
        var Intent_State="intent_state"
    }
}