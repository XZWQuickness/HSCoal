package com.exz.hscal.hscoal.module.mine.sellorder

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.release.ReleaseCocalActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_sell_order_cocal_detail.*

/**
 * Created by pc on 2017/12/14.
 * 我的卖家 已售订单
 */

class SellOrderCocalDetailActivity : BaseActivity(), View.OnClickListener {

    override fun initToolbar(): Boolean {
        mTitle.text = "订单详情"
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
        if (intent.getStringExtra(Intent_State).equals("3")) {
            var actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {

                startActivity(Intent(mContext, ReleaseCocalActivity::class.java))
            }
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_sell_order_cocal_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
    }

    private fun initView() {


//        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.Red))
    }

    private fun initEvent() {
    }

    override fun onClick(p0: View) {
        when (p0) {
        }
    }

    companion object {
        var Intent_State = "intent_state"
    }
}