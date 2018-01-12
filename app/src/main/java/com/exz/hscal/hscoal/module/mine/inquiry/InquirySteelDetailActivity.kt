package com.exz.hscal.hscoal.module.mine.inquiry

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.enquiry.EnquiryCocalActivity
import com.exz.hscal.hscoal.module.main.enquiry.EnquirySteelActivity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_inquiry_steel_detail.*

/**
 * Created by pc on 2017/12/14.
 * 询盘煤炭详情
 */

class InquirySteelDetailActivity : BaseActivity(), View.OnClickListener {
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

        if(intent.hasExtra(Intent_Order_state) && intent.getStringExtra(InquiryCocalDetailActivity.Intent_Order_state) == "2") {
            toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
            val actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {
                startActivity(Intent(mContext,EnquirySteelActivity::class.java)
                        .putExtra(EnquirySteelActivity.Intent_Id,intent.getStringExtra(Intent_Id)))
                finish()
            }
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_inquiry_steel_detail
    }

    override fun init() {
        super.init()
        initView()
        initData()
        initEvent()
    }
    private fun initView() {
        //状态：0审核中 1询价中 2未通过 3已过期 4已确认
        if (intent.hasExtra(InquiryCocalDetailActivity.Intent_Order_state) && !TextUtils.isEmpty(intent.getStringExtra(InquiryCocalDetailActivity.Intent_Order_state))) {
            when (intent.getStringExtra(InquiryCocalDetailActivity.Intent_Order_state)) {
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

        SZWUtils.matcherSearchTitle(tvState, tvState.text.toString().trim(), 3, tvState.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.colorPrimary))
    }

    private fun initData() {
        DataCtrlClass.getSteelInfoMamage(mContext, intent.getStringExtra(Intent_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                className.text = "(" + it.data?.className + ")" ?: ""
                weight.text = String.format(mContext.getString(R.string.heavy), it.data?.weight ?: "")//件重
                specification.text = it.data?.specification ?: "" //规格
                materialQuality.text = it.data?.materialQuality ?: "" //材质
                provinceCity.text = it.data?.provinceCity ?: ""//交货地点
                remark.text = it.data?.remark ?: ""//备注


                deliveryTime.text = it.data?.plannedDeliveryTime//计划收货时间
                provinceCity.text=it.data?.provinceCity //交货地点
                placeDelivery.text=it.data?.placeDelivery //详细地址
                remark.text=it.data?.remark//备注
                tvQquoteContactName.text = "报价联系人: " + it.data?.quoteContactName
                tvQquoteContactPhone.text = it.data?.quoteContactPhone
                tvQuoteCount.text = String.format(mContext.getString(R.string.referrer_offer), it.data?.quoteCount)
                if(!it.data?.contactName.equals("")){
                    contactName.text=it.data?.contactName
                }else{
                    contactName.visibility= View.GONE
                }
                if(!it.data?.contactMobile.equals("")){
                    contactMobile.text=it.data?.contactMobile
                }else{
                    contactMobile.visibility= View.GONE
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
                        .putExtra(OfferListActivity.Intent_ObjectId,intent.getStringExtra(InquiryCocalDetailActivity.Intent_Id)).
                        putExtra(OfferListActivity.Intent_Type,"2"))//有色金属
            }
            tvQquoteContactPhone -> {
                DialogUtils.Call(mContext as BaseActivity, tvQquoteContactPhone.text.toString().trim())
            }
            bt_submit -> {
                com.exz.hscal.hscoal.utils.DialogUtils.delete(mContext,{
                    DataCtrlClass.delteEnquiry(mContext, "2", intent.getStringExtra(InquiryCocalDetailActivity.Intent_Id), {
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