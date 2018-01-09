package com.exz.hscal.hscoal.module.demand

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.view.View
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.MineFragment
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.main.release.ReleaseGoodsActivity
import com.exz.hscal.hscoal.module.mine.ApplyForDevelopersActivity
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_demand_cocal.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/11.
 * 煤炭需求详情
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

        DataCtrlClass.getCoalInfoEnquiry(mContext, intent.getStringExtra(Intent_Detaile_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                coalVarietyName.text = "(" + it.data?.coalVarietyName + ")" ?: ""
                when (it.data?.coalVarietyName) {
                    "焦炭/焦粉/焦粒" -> {
                        llLayCocal2.visibility = View.GONE
                        llLayCocal3.visibility = View.GONE
                        fixedCarbon.text = it.data?.fixedCarbon ?: ""//固定碳
                        calorificValue.text = it.data?.calorificValue + "(MJ/kg)" ?: ""//发热量
                        ashSpecification.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        volatiles.text = it.data?.volatiles + "(%)" ?: "" //挥发份
                        inherentMoisture.text = it.data?.inherentMoisture + "(%)" ?: "" //内水
                        totalSulfurContent.text = it.data?.totalSulfurContent + "(%)" ?: "" //全硫份
                    }
                    "炼焦煤" -> {
                        llLayCocal1.visibility = View.GONE
                        llLayCocal3.visibility = View.GONE
                        ashSpecification2.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        totalSulfurContent2.text = it.data?.totalSulfurContent + "(%)" ?: "" //全硫份
                        bond.text = it.data?.bond ?: "" //粘结
                        volatiles2.text = it.data?.volatiles + "(%)" ?: "" //挥发份
                        Y_Value.text = it.data?.yValue + "(mm)" ?: "" //v值
                        lithofacies.text = it.data?.lithofacies ?: "" //岩相（煤种：炼焦煤）
                        scr.text = it.data?.csr ?: ""
                    }
                    "动力煤" -> {
                        llLayCocal1.visibility = View.GONE
                        llLayCocal2.visibility = View.GONE

                        lowerCalorificValue.text = it.data?.lowerCalorificValue + "(kcal/kg)" ?: "" //低位热值
                        airDrySulfur.text = it.data?.airDrySulfur + "(%)" ?: "" //空干基硫分
                        airDryRadicalVolatiles.text = it.data?.airDryRadicalVolatiles + "(%)" ?: "" //空干基挥发分
                        inherentMoisture3.text = it.data?.inherentMoisture + "(%)" ?: "" //内水
                        fixedCarbon3.text = it.data?.fixedCarbon + "(%)" ?: ""//固定碳
                    }
                }
                purchaseQuantity.text = it.data?.purchaseQuantity + "吨" ?: ""//求购数
                plannedDeliveryTime.text = it.data?.plannedDeliveryTime//计划收货时间
                placeDelivery.text = it.data?.placeDelivery//交货地点
                remark.text = it.data?.remark//备注

            }
        })

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
                                putExtra(OfferActivity.Intent_type, "1"))//煤炭
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
        var Intent_Detaile_Id = "coalEnquiryId"
    }
}
