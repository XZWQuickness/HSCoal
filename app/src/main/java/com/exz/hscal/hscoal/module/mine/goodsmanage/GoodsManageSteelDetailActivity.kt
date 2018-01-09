package com.exz.hscal.hscoal.module.mine.goodsmanage
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.module.main.release.ReleaseSteelActivity
import com.exz.hscal.hscoal.module.main.steel.SeekSteelDetailActivity
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_goods_manage_steel_detail.*

/**
 * Created by pc on 2017/12/14.
 * 我的卖家 商品管理 金属详情
 */

class GoodsManageSteelDetailActivity : BaseActivity() {


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
        if (intent.getStringExtra(GoodsManageCocalDetailActivity.Intent_State).equals("2")) {
            var actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {

                startActivity(Intent(mContext, ReleaseSteelActivity::class.java))
            }
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_goods_manage_steel_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
        initData()
    }
    private fun initData() {

        DataCtrlClass.getSteelInfo(mContext, intent.getStringExtra(Intent_Id), {
            if (it != null) {
                name.text = it.data?.name ?: ""
                className.text = "(" + it.data?.className + ")" ?: ""
                weight.text = String.format(mContext.getString(R.string.heavy), it.data?.weight ?: "")//件重
                QTY.text = it.data?.qty + "吨" ?: ""//供应量
                price.text = it.data?.price + "元/件" ?: ""//价格
                specification.text = it.data?.specification ?: "" //规格
                materialQuality.text = it.data?.materialQuality ?: "" //材质
                warehouse.text = it.data?.warehouse ?: "" //仓库
                paymentModeName.text = it.data?.paymentModeName ?: "" //付款方式
                inspectonBody.text = it.data?.inspectonBody ?: "" //检验机构
                deliveryTime.text = it.data?.deliveryTime?.replace(",", "至") ?: ""//交货时间
                deliveryWayName.text = it.data?.deliveryWayName ?: ""//交货方式
                provinceCity.text = it.data?.provinceCity ?: ""//交货地点
                remark.text = it.data?.remark ?: ""//备注
            }
        })
    }
    private fun initView() {

        when (intent.getStringExtra(GoodsManageCocalDetailActivity.Intent_State)) {
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

        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.colorPrimary))
    }

    private fun initEvent() {
        bt_delete.setOnClickListener{
            DialogUtils.delete(mContext, {
                DataCtrlClass.delteGoods(mContext, "2", intent.getStringExtra(SeekCocalDetailActivity.Intent_Id), {
                    if (it != null) {
                        finish()
                    }
                })
            })

        }
    }


    companion object {
        var Intent_State="intent_state"
        var Intent_Id = "steelId" //有色金属id
    }
}