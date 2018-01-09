package com.exz.hscal.hscoal.module.mine.goodsmanage

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.main.release.ReleaseCocalActivity
import com.exz.hscal.hscoal.module.mine.inquiry.OfferListActivity
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_goods_manage_cocal_detail.*

/**
 * Created by pc on 2017/12/14.
 * 我的卖家 商品管理 煤炭详情
 */

class GoodsManageCocalDetailActivity : BaseActivity(), View.OnClickListener {

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
        if (intent.getStringExtra(Intent_State).equals("2")) {
            var actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {

                startActivity(Intent(mContext, ReleaseCocalActivity::class.java).
                        putExtra(ReleaseCocalActivity.Intent_Order_Id, intent.getStringExtra(SeekCocalDetailActivity.Intent_Id)))
                finish()
            }
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_goods_manage_cocal_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
        initData()
    }

    private fun initData() {

        DataCtrlClass.getCoalInfo(mContext, intent.getStringExtra(SeekCocalDetailActivity.Intent_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                coalVarietyName.text = "(" + it.data?.coalVarietyName + ")" ?: ""
                when (it.data?.coalVarietyName) {
                    "焦炭/焦粉/焦粒" -> {
                        llLayCocal2.visibility = View.GONE
                        llLayCocal3.visibility = View.GONE
                        place.text = String.format(mContext.getString(R.string.origin), it.data?.place ?: "")//产地
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
                        baseVolatiles.text = it.data?.baseVolatiles + "(%)" ?: ""//收到基挥发分
                        totalMoisture.text = it.data?.totalMoisture + "(%)" ?: ""//全水分
                        ashSpecification3.text = it.data?.ashSpecification + "(%)" ?: "" //灰份
                        ashFusionPoint.text = it.data?.ashFusionPoint ?: "" //灰熔点
                        G_Value.text = it.data?.gValue ?: ""
                        Y_Value3.text = it.data?.yValue + "(mm)" ?: ""
                    }
                }
                place.text = String.format(mContext.getString(R.string.origin), it.data?.place ?: "")//产地
                QTY.text = it.data?.qty + "吨" ?: ""//供应量
                price.text = it.data?.price+"元"
                paymentModeName.text = it.data?.paymentModeName //付款方式
                inspectonBody.text = it.data?.inspectonBody // 检验机构
                deliveryTime.text = it.data?.deliveryTime?.replace(",", "至") ?: "" //交货时间
                deliveryWayName.text = it.data?.deliveryWayName //交货方式
                provinceCity.text = it.data?.provinceCity //交货地点
                remark.text = it.data?.remark//备注
            }
        })
    }

    private fun initView() {

        when (intent.getStringExtra(Intent_State)) {
            "0" -> {//审核中
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "审核中")
            }
            "2" -> {//未通过
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "未通过")
                bt_delete.visibility = View.VISIBLE

            }
            "1" -> {//已通过
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已通过")

            }
        }

        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.Red))
    }

    private fun initEvent() {
        ll_offer.setOnClickListener(this)
        bt_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            ll_offer -> {
                startActivity(Intent(mContext, OfferListActivity::class.java))
            }
            bt_delete -> {//删除货源
                DialogUtils.delete(mContext, {
                    DataCtrlClass.delteGoods(mContext, "1", intent.getStringExtra(SeekCocalDetailActivity.Intent_Id), {
                        if (it != null) {
                            finish()
                        }
                    })
                })

            }
        }
    }

    companion object {
        var Intent_State = "intent_state"
        var Intent_Id = "coalId" //煤炭货源id
    }
}