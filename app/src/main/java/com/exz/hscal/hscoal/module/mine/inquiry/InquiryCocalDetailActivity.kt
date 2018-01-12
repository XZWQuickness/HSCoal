package com.exz.hscal.hscoal.module.mine.inquiry

import android.content.Intent
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.main.enquiry.EnquiryCocalActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_inquiry_cocal_detail.*
import org.jetbrains.anko.textColor

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

        if (intent.hasExtra(Intent_Order_state) && intent.getStringExtra(Intent_Order_state) == "2") {
            toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
            val actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {
                startActivity(Intent(mContext, EnquiryCocalActivity::class.java)
                        .putExtra(EnquiryCocalActivity.Intent_Id, intent.getStringExtra(InquirySteelDetailActivity.Intent_Id)))
                finish()
            }
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
        initData()
    }

    private fun initView() {
        //状态：0审核中 1询价中 2未通过 3已过期 4已确认
        if (intent.hasExtra(Intent_Order_state) && !TextUtils.isEmpty(intent.getStringExtra(Intent_Order_state))) {
            when (intent.getStringExtra(Intent_Order_state)) {
                "0" -> {
                    tvState.text = "状态: 审核中"
                    tvQuoteCount.visibility = View.GONE
                }
                "1" -> {
                    tvState.text = "状态: 询价中"
                }
                "2" -> {
                    tvState.text = "状态: 未通过"
                    bt_submit.visibility = View.VISIBLE

                    tvQuoteCount.visibility = View.GONE
                }
                "3" -> {
                    tvState.text = "状态: 已过期"
                }
                "4" -> {
                    tvState.text = "状态: 已确认"
                    llQuote.visibility = View.VISIBLE
                    llcontact.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun initData() {

        DataCtrlClass.getCoalInfoEnquiryMamage(mContext, intent.getStringExtra(Intent_Id), {
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
                tvQuoteCount.text = String.format(mContext.getString(R.string.referrer_offer), it.data?.quoteCount)
                QTY.text = it.data?.purchaseQuantity + "吨" ?: ""//供应量
                tvQquoteContactName.text = "报价联系人: " + it.data?.quoteContactName
                tvQquoteContactPhone.text = it.data?.quoteContactPhone
                tvQquoteContactPhone.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
                deliveryTime.text = it.data?.plannedDeliveryTime
                provinceCity.text = it.data?.provinceCity //详细地址
                placeDelivery.text = it.data?.placeDelivery //交货地点
                remark.text = it.data?.remark//备注
                if (!it.data?.contactName.equals("")) {
                    contactName.text = it.data?.contactName
                } else {
                    contactName.visibility = View.GONE
                }
                if (!it.data?.contactMobile.equals("")) {
                    contactMobile.text = it.data?.contactMobile
                } else {
                    contactMobile.visibility = View.GONE
                }

            }
        })
    }

    private fun initEvent() {
        ll_offer.setOnClickListener(this)
        tvQquoteContactPhone.setOnClickListener(this)
        bt_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            ll_offer -> {
                startActivity(Intent(mContext, OfferListActivity::class.java)
                        .putExtra(OfferListActivity.Intent_ObjectId, intent.getStringExtra(Intent_Id)).
                        putExtra(OfferListActivity.Intent_Type, "1"))//煤炭
            }
            tvQquoteContactPhone -> {
                DialogUtils.Call(mContext as BaseActivity, tvQquoteContactPhone.text.toString().trim())
            }
            bt_submit -> {
                com.exz.hscal.hscoal.utils.DialogUtils.delete(mContext, {
                    DataCtrlClass.delteEnquiry(mContext, "1", intent.getStringExtra(Intent_Id), {
                        if (it != null) {
                            finish()
                        }
                    })
                })
            }
        }
    }

    companion object {
        var Intent_Id = "OrderId"
        var Intent_Order_state = "OrderState"
    }
}
