package com.exz.hscal.hscoal.module.demand

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.MineFragment
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.exz.hscal.hscoal.module.main.steel.SeekSteelDetailActivity
import com.exz.hscal.hscoal.module.mine.ApplyForDevelopersActivity
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_demand_steel.*
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/11.
 * 询价有色金属详情
 */

class DemandSteelDetailAtivity : BaseActivity(), View.OnClickListener, OnRefreshListener {


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
        return R.layout.activity_demand_steel
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
        refreshLayout.autoRefresh()
    }

    private fun initEvent() {
        bt_submit.setOnClickListener(this)
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
    }
    private fun initData() {

        DataCtrlClass.getSteelInfoEnquiry(mContext, intent.getStringExtra(Intent_Detaile_Id), {
            refreshLayout?.finishRefresh()
            if (it != null) {
                name.text = it.data?.name ?: ""
                className.text = "(" + it.data?.className + ")" ?: ""
                purchaseQuantity.text = it.data?.purchaseQuantity +"件"?: ""
                weight.text = String.format(mContext.getString(R.string.heavy), it.data?.weight ?: "")//件重
                specification.text = it.data?.specification ?: "" //规格
                materialQuality.text = it.data?.materialQuality ?: "" //材质
                plannedDeliveryTime.text = it.data?.plannedDeliveryTime?.replace(",", "至") ?: ""//交货时间
                placeDelivery.text = it.data?.placeDelivery ?: ""//交货地点
                remark.text = it.data?.remark ?: ""//备注
            }
        })
    }
    override fun onRefresh(refreshlayout: RefreshLayout?) {
        initData()
    }

    override fun onClick(p0: View) {
        when (p0) {
            bt_submit -> {
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(mContext, LoginActivity::class.java), LoginActivity.RESULT_LOGIN_CANCELED)
                    return
                }


                //"供应商认证：-1未申请 0待审核，1已认证 2未通过",
                when (MineFragment.businessAuthentication) {
                    "-1" -> {
                        startActivity(Intent(mContext, ApplyForDevelopersActivity::class.java))
                    }
                    "0" -> {
                        toast("供应商认证审核中")
                    }
                    "1" -> { //立即报价
                        startActivity(Intent(mContext, OfferActivity::class.java)
                                .putExtra(OfferActivity.Intent_objectId_Key, intent.getStringExtra(Intent_Detaile_Id)).
                                putExtra(OfferActivity.Intent_type, "2"))//有色金属
                        finish()
                    }
                    "2" -> {
                        startActivity(Intent(mContext, ApplyForDevelopersActivity::class.java).putExtra(ApplyForDevelopersActivity.Intent_State, MineFragment.businessAuthentication))
                    }
                }

            }
        }
    }


    companion object {
        var Intent_Detaile_Id = "steelEnquiryId"
    }
}