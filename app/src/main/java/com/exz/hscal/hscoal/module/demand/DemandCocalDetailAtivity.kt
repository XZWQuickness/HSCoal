package com.exz.hscal.hscoal.module.demand

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.exz.hscal.hscoal.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_demand_cocal.*

/**
 * Created by pc on 2017/12/11.
 */

class DemandCocalDetailAtivity : BaseActivity(), View.OnClickListener {
    private var mOffset = 0
    private var mScrollY = 0
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(mContext, toolbar)
        StatusBarUtil.setPaddingSmart(mContext, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }


        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_demand_cocal
    }

    override fun init() {
        super.init()
        initView()
        initData()
        bt_submit.setOnClickListener(this)
    }

    private fun initData() {


    }

    private fun initView() {
        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }

            override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }
        })
        mScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = DensityUtil.dp2px(170f)
            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                var scrollNewY = scrollY
                if (lastScrollY < h) {
                    scrollNewY = Math.min(h, scrollNewY)
                    mScrollY = if (scrollNewY > h) h else scrollNewY
                    parallax.translationY = (mOffset - mScrollY).toFloat()
                    blurView.alpha = 1f * mScrollY / h
                    buttonBarLayout.alpha = 1f * mScrollY / h
                }
                lastScrollY = scrollNewY

            }
        })
        buttonBarLayout.alpha = 0f
        blurView.alpha = 0f

    }


    override fun onClick(p0: View) {
        when (p0) {
            bt_submit -> {
                startActivity(Intent(mContext, OfferActivity::class.java))
            }
        }
    }


    companion object {
        var Intent_Id="id"
    }
}
