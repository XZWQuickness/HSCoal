package com.exz.hscal.hscoal.module.mine.inquiryo

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.mine.inquiry.OfferListActivity
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_inquiry_order_cocal_detail.*
import kotlinx.android.synthetic.main.activity_inquiry_order_cocal_detail.view.*

/**
 * Created by pc on 2017/12/14.
 * 询盘煤炭详情
 */

class InquiryOrderCocalDetailActivity : BaseActivity(), View.OnClickListener {

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
        return R.layout.activity_inquiry_order_cocal_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
        initData()
    }

    private fun initView() {
//0报价中 1已确认 2已拒绝 3已过期
        when (intent.getStringExtra(Intent_State)) {
            "0" -> {//报价中
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "报价中")

            }
            "1" -> {//已确认
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已确认")
                auditStateTime.visibility = View.VISIBLE
                llcontact.visibility = View.VISIBLE

            }
            "2" -> {//已拒绝
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已拒绝")
                llcontact.visibility = View.VISIBLE
                auditStateTime.visibility=View.VISIBLE
            }
            "3" -> {//已过期
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已过期")
            }
        }

        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.Red))
    }


    private fun initData() {

        DataCtrlClass.getCoalInfoQuote(mContext, intent.getStringExtra(Intent_Id), {
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
                        ashSpecification3.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        Y_Value3.text = it.data?.yValue + "(mm)" ?: ""
                    }
                }
                QTY.text = it.data?.purchaseQuantity + "吨" ?: ""//供应量
                price.text ="单价 ￥"+ it.data?.price + "元"
                remark.text = it.data?.remark//备注
                tv_price.text = "金额 ￥" + it.data?.price
                plannedDeliveryTime.text = it.data?.plannedDeliveryTime//计划收货时间
                provinceCity.text = it.data?.provinceCity  //省市区
                placeDelivery.text = it.data?.placeDelivery  //详细地址
                createDate.text = String.format(mContext.getString(R.string.Offer_time), it.data?.createDate)//报价时间

                when (intent.getStringExtra(Intent_State)) {
                    "1"->{
                        auditStateTime.text = String.format(mContext.getString(R.string.ConfirmTime), it.data?.auditStateTime)//确认时间
                    }
                    "2"->{
                        auditStateTime.text="拒绝时间:"+it.data?.auditStateTime
                    }
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


    private fun initEvent() {
        ll_offer.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            ll_offer -> {
                startActivity(Intent(mContext, OfferListActivity::class.java))
            }
//            bt_delete->{//删除询价信息
//                DialogUtils.delete(mContext, {
//                    DataCtrlClass.delteEnquiry(mContext, "1", intent.getStringExtra(SeekCocalDetailActivity.Intent_Id), {
//                        if (it != null) {
//                            finish()
//                        }
//                    })
//                })
//            }
        }
    }

    companion object {
        var Intent_State = "intent_state"
        var Intent_Id = "coalQuoteId"
    }
}