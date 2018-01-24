package com.exz.hscal.hscoal.module.mine.inquiryo

import android.support.v4.content.ContextCompat
import android.view.View
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.mine.myorder.OrderCocalDetailActivity
import com.exz.hscal.hscoal.module.mine.myorder.OrderSteelDetailActivity
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
        initData()
    }

    private fun initView() {
       //状态：0报价中 1已确认 2已拒绝 3已过期
        when (intent.getStringExtra(Intent_State)) {
            "0" -> {//报价中
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "报价中")

            }
            "1" -> {//已确认
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已确认")
                auditStateTime.visibility = View.VISIBLE
            }
            "2" -> {//已拒绝
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已拒绝")
            }
            "3" -> {//已过期
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已过期")
            }
        }

        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.colorPrimary))
    }

    private fun initData() {
        DataCtrlClass.getSteelInfoQuote(mContext, intent.getStringExtra(Intent_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                className.text = "(" + it.data?.className + ")" ?: ""
                weight.text = String.format(mContext.getString(R.string.heavy), it.data?.weight ?: "")//件重
                specification.text = it.data?.specification ?: "" //规格
                materialQuality.text = it.data?.materialQuality ?: "" //材质
                provinceCity.text = it.data?.provinceCity ?: ""//交货地点
                remark.text = it.data?.remark ?: ""//备注

                price.text= "金额: ￥"+it.data?.price//单价
                createDate.text = String.format(mContext.getString(R.string.Offer_time), it.data?.createDate)//报价时间
                auditStateTime.text = String.format(mContext.getString(R.string.ConfirmTime), it.data?.auditStateTime)//确认时间

                deliveryTime.text = it.data?.plannedDeliveryTime//计划收货时间
                provinceCity.text=it.data?.provinceCity //交货地点
                placeDelivery.text=it.data?.placeDelivery //详细地址
                remark.text=it.data?.remark//备注

                if(!it.data?.auditStateTime.equals("")){
                    auditStateTime.text="拒绝时间:"+it.data?.auditStateTime
                    auditStateTime.visibility=View.VISIBLE
                }
                if(!it.data?.contactName.equals("")){
                    contactName.text=it.data?.contactName
                }else{
                    contactName.visibility=View.GONE
                }
                if(!it.data?.contactMobile.equals("")){
                    contactMobile.text=it.data?.contactMobile
                }else{
                    contactMobile.visibility=View.GONE
                }
            }
        })
    }


    companion object {
        var Intent_State = "intent_state"
        var Intent_Id = "id"
    }
}