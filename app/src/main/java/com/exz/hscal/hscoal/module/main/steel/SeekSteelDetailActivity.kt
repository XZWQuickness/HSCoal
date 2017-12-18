package com.exz.hscal.hscoal.module.main.steel

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.ConfirmOrderActivity
import com.exz.hscal.hscoal.pop.SelectGoodsTypePop
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_seek_steel_detail.*
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/7.
 * 找钢材详情
 */

class SeekSteelDetailActivity : BaseActivity(), OnRefreshListener, View.OnClickListener {


    private lateinit var pop: SelectGoodsTypePop
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

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "检验报告"
        actionView.setOnClickListener {
            toast("")
        }

        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_seek_steel_detail
    }

    override fun init() {
        super.init()
        initView()
        refreshLayout.autoRefresh()
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
        pop = SelectGoodsTypePop(mContext, {
            if (!TextUtils.isEmpty(it)) {
                startActivity(Intent(mContext, ConfirmOrderActivity::class.java).putExtra(ConfirmOrderActivity.Intent_Type,it))
            }
        })
        bt_submit.setOnClickListener(this)
        refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
    }

    override fun onClick(p0: View) {
        when (p0) {
            bt_submit -> {
                pop.showPopupWindow()
            }
        }
    }
}