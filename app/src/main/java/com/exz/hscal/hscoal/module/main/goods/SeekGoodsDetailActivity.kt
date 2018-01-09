package com.exz.hscal.hscoal.module.main.goods

import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_seek_godos_detail.*
import kotlinx.android.synthetic.main.item_seek_goods_list.view.*

/**
 * Created by pc on 2017/12/7.
 * 找货源详情
 */

class SeekGoodsDetailActivity : BaseActivity(), OnRefreshListener {


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
        return R.layout.activity_seek_godos_detail
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

        refreshLayout.setOnRefreshListener(this)

        llMobile.setOnClickListener {
            var phone=mobile.text.toString().trim()
            if(!TextUtils.isEmpty(phone)){
                DialogUtils.Call(mContext as BaseActivity,phone)
            }
        }
    }
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        DataCtrlClass.waitDeliveryOrderInfo(mContext,intent.getStringExtra(Intent_Order_Id),{
            refreshlayout?.finishRefresh()
            if(it!=null){
                var type=intent.getStringExtra(Intent_Order_Type)
                name.text= it.data?.name ?: ""
                count.text= it.data?.count+(if(type.equals("1"))  "吨" else "件" )
                sendTime.text=it.data?.sendTime ?:""
                fromAddress.text=it.data?.fromAddress ?:""
                userName.text=it.data?.userName ?:""
                mobile.text=it.data?.mobile ?:""
                toAddress.text=it.data?.toAddress ?:""
                remark.text=it.data?.remark ?:""

                if(!TextUtils.isEmpty(it.data?.mobile ?:"")&&!TextUtils.isEmpty(it.data?.mobile ?:"")){
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,ContextCompat.getDrawable(mContext,R.mipmap. icon_main_gray_next),null)
                }else{
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null)
                }
            }
        })

    }
    companion object {
        var Intent_Order_Type="Order_Type"//类型：1煤炭 2有色金属
        var Intent_Order_Id="Order_Id"//订单id
}

}