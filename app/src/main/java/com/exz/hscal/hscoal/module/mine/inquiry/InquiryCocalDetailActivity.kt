package com.exz.hscal.hscoal.module.mine.inquiry

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_inquiry_cocal_detail.*

/**
 * Created by pc on 2017/12/14.
 * 询盘煤炭详情
 */

class InquiryCocalDetailActivity : BaseActivity(), View.OnClickListener {


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

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "修改"
        actionView.setOnClickListener {
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_inquiry_cocal_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
    }

    private fun initView() {
    }

    private fun initEvent() {
        ll_offer.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            ll_offer -> {
                startActivity(Intent(mContext, OfferListActivity::class.java))
            }
        }
    }
}
