package com.exz.hscal.hscoal.module.main.enquiry

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.alibaba.fastjson.JSON
import com.bigkoo.pickerview.OptionsPickerView
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.ReleaseAdapter
import com.exz.hscal.hscoal.bean.CityBean
import com.exz.hscal.hscoal.bean.ReleaseBean
import com.exz.hscal.hscoal.bean.TextBean
import com.exz.hscal.hscoal.utils.AndroidBug5497Workaround
import com.exz.hscal.hscoal.utils.RecycleViewDivider
import com.exz.hscal.hscoal.utils.SZWUtils
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.imageloder.GlideImageLoader
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_release_cocale.*
import kotlinx.android.synthetic.main.footer_release_button.view.*
import org.jetbrains.anko.toast
import top.zibin.luban.Luban
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2017/12/7.
 * 发布煤炭询价
 */

class EnquiryCocalActivity : BaseActivity() {


    var data1 = ArrayList<ReleaseBean>()
    var data2 = ArrayList<ReleaseBean>()
    var data3 = ArrayList<ReleaseBean>()
    var cocalStr = ArrayList<String>()
    lateinit var mFooterView: View
    lateinit var mCoclaOptionPicker: OptionsPickerView<String>
    lateinit var mTimePicker: TimePickerView
    lateinit var entity: ReleaseBean
    var dateType = ""
    var requestCheck = ""
    var coalId = ""//煤炭id

    var coalVarietyId = "1"//煤炭品种id
    var name = ""//品名
    var purchaseQuantity = ""//求购数量（吨）
    var fixedCarbon = ""//固定碳（煤种：焦炭/焦粉/焦粒、动力煤）
    var calorificValue = ""//发热量（煤种：焦炭/焦粉/焦粒）
    var ashSpecification = ""//灰份（煤种：焦炭/焦粉/焦粒、炼焦煤、动力煤）
    var volatiles = ""//挥发份（煤种：焦炭/焦粉/焦粒、炼焦煤）
    var inherentMoisture = ""//内水（煤种：焦炭/焦粉/焦粒、动力煤）
    var totalSulfurContent = ""//全硫份（煤种：焦炭/焦粉/焦粒、炼焦煤）
    var bond = ""//粘结（煤种：炼焦煤）
    var Y_Value = ""//Y值（煤种：炼焦煤、动力煤）
    var lithofacies = ""//岩相（煤种：炼焦煤）
    var CSR = ""//CSR（煤种：炼焦煤）
    var lowerCalorificValue = ""//低位热值（煤种：动力煤）
    var airDrySulfur = ""//空干基硫分（煤种：动力煤）
    var airDryRadicalVolatiles = ""//空干基挥发分（煤种：动力煤）
    var provinceId = ""//交货省份id
    var cityId = ""//交货城市id
    var placeDelivery = ""//交货地
    var deliveryTime = ""//交货时间，如（2017-01-01,2017-03-01）
    var deliveryWayId = ""//交货方式id
    var plannedDeliveryTime = ""//计划收货时间
    var contactName = ""//联系人
    var contactMobile = ""//联系电话
    var remark = ""//备注

    var url = Urls.ReleaseCoalEnquiry
    private lateinit var mOptionsGoodsType: OptionsPickerView<String>

    private val deliveryWay = java.util.ArrayList<TextBean>()
    private val deliveryWayStr = java.util.ArrayList<String>()
    private lateinit var pvOptionsAddress: OptionsPickerView<String>
    private lateinit var listAddress: List<CityBean.InfoEntity.ProvincesEntity>
    private val optionsProvinces = java.util.ArrayList<String>()
    private val optionsCities = java.util.ArrayList<java.util.ArrayList<String>>()
    private val optionsCounties = java.util.ArrayList<java.util.ArrayList<java.util.ArrayList<String>>>()
    private var optionsAddress1 = 0
    private var optionsAddress2 = 0
    private var optionsAddress3 = 0

    private lateinit var mAdapter: ReleaseAdapter<ReleaseBean>
    override fun initToolbar(): Boolean {
        mTitle.text = "煤炭"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 10f)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return R.layout.activity_release_cocale
    }

    companion object {
        var Intent_Id = "coalId"
    }

    override fun init() {
        super.init()
        initListData()
        initView()
        initOptionPicker()
        getDeliveryWay()
        initCommint()
        if (intent.hasExtra(Intent_Id) && !TextUtils.isEmpty(intent.getStringExtra(Intent_Id))) {

            iniDetalie()
        }
    }

    private fun iniDetalie() {
        coalId = intent.getStringExtra(Intent_Id)
        url = Urls.EditCoalEnquiry
        DataCtrlClass.getCoalInfoEnquiry(mContext, coalId, {

            if (it != null) {
                when (it.data?.coalVarietyName) {
                    "焦炭/焦粉/焦粒" -> {
                        mAdapter.setNewData(data1)
                        coalVarietyId = "1"
                    }
                    "炼焦煤" -> {
                        mAdapter.setNewData(data2)
                        coalVarietyId = "2"
                    }
                    "动力煤" -> {
                        mAdapter.setNewData(data3)
                        coalVarietyId = "3"
                    }
                }
                for (bean in mAdapter.data) {
                    when (bean.k) {
                        "品名:" -> {
                            bean.v = it.data?.name ?: ""
                        }
                        "求购数(吨):" -> {
                            bean.v = it.data?.purchaseQuantity ?: ""
                        }
                        "固定碳(%):" -> {
                            bean.v = it.data?.fixedCarbon ?: ""//固定碳
                        }
                        "发热量(MJ/kg):" -> {
                            bean.v = it.data?.calorificValue ?: ""//发热量
                        }
                        "灰份(%):" -> {
                            bean.v = it.data?.ashSpecification ?: "" //灰份
                        }
                        "挥发份(%):" -> {
                            bean.v = it.data?.volatiles ?: "" //挥发份
                        }
                        "内水(%):" -> {
                            bean.v = it.data?.inherentMoisture ?: "" //内水
                        }
                        "全硫份(%):" -> {
                            bean.v = it.data?.totalSulfurContent ?: "" //全硫份
                        }
                        "粘结(%):" -> {
                            bean.v = it.data?.bond ?: "" //粘结
                        }
                        "挥发份(%):" -> {
                            bean.v = it.data?.volatiles ?: "" //挥发份
                        }
                        "Y值(mm):" -> {
                            bean.v = it.data?.yValue ?: "" //y值
                        }
                        "岩相:" -> {
                            bean.v = it.data?.lithofacies ?: "" //岩相（煤种：炼焦煤）
                        }
                        "CSR:" -> {
                            bean.v = it.data?.csr ?: ""
                        }
                        "低位热值(kcal/kg):" -> {
                            val lowerCalorificValue = it.data?.lowerCalorificValue?.split("-")
                            if (lowerCalorificValue?.size ?: 0 == 2) {
                                bean.left = lowerCalorificValue?.get(0) ?: ""
                                bean.right = lowerCalorificValue?.get(1) ?: ""
                            }
                        }
                        "空干基硫分(%):" -> {

                            val airDrySulfur = it.data?.airDrySulfur?.split("-")
                            if (airDrySulfur?.size ?: 0 == 2) {
                                bean.left = airDrySulfur?.get(0) ?: ""
                                bean.right = airDrySulfur?.get(1) ?: ""
                            }
                        }
                        "空干基挥发分(%):" -> {

                            val airDryRadicalVolatiles = it.data?.airDryRadicalVolatiles?.split("-")
                            if (airDryRadicalVolatiles?.size ?: 0 == 2) {
                                bean.left = airDryRadicalVolatiles?.get(0) ?: ""
                                bean.right = airDryRadicalVolatiles?.get(1) ?: ""
                            }
                        }
                        "计划收货时间:" -> {
                            bean.v = it.data?.plannedDeliveryTime ?: "" //计划收货时间
                        }
                        "交货方式:" -> {
                            bean.v = it.data?.deliveryWayName ?: "" //交货方式
                        }
                        "交货地点:" -> {
                            bean.v = it.data?.provinceCity ?: ""//交货地点
                        }
                        "详细地址:" -> {
                            bean.v = it.data?.placeDelivery ?: ""//交货地
                        }
                        "联系人:" -> {
                            bean.v = it.data?.contactName ?: ""//联系人
                        }
                        "手机号:" -> {
                            bean.v = it.data?.contactMobile ?: ""//联系电话
                        }
                        "备注:" -> {
                            bean.v = it.data?.remark ?: ""//备注
                        }
                    }
                }
                mAdapter.notifyDataSetChanged()

            }
        })
    }

    private fun isReturn(content: String): Boolean {
        return content.equals("请输入值") || content.equals("请输入可供吨数") || content.equals("请输入单价")
                || content.equals("请选择") || content.equals("请输入") || content.equals("请输入品名") || content.equals("请输入产地")
    }

    private fun initCommint() {
        mFooterView.bt_submit.setOnClickListener {
            // 发布煤炭

            for (bean in mAdapter.data) {
                if (TextUtils.isEmpty(coalId)) {
                    requestCheck = MyApplication.loginUserId + coalVarietyId
                    when (bean.lay) {
                        1 -> {
                            if (!bean.k.contains("选填") && TextUtils.isEmpty(bean.v) || bean.check.equals("0") && isReturn(bean.v)) {
                                toast("请输入" + bean.k.replace(":", ""))
                                return@setOnClickListener
                            }
                        }
                        2 -> {
                            if (!bean.k.contains("选填") && TextUtils.isEmpty(bean.v) || bean.check.equals("0") && isReturn(bean.v)) {
                                toast("请选择" + bean.k.replace(":", ""))
                                return@setOnClickListener
                            }
                        }
                        3 -> {
                            if (!bean.k.contains("选填") && TextUtils.isEmpty(bean.left) || !bean.k.contains("选填") && TextUtils.isEmpty(bean.right) || bean.check.equals("0") && isReturn(bean.v)) {
                                toast("请选择" + bean.k.replace(":", ""))
                                return@setOnClickListener
                            }
                        }
                        4 -> {
                            if (!bean.k.contains("选填") && TextUtils.isEmpty(bean.left) || !bean.k.contains("选填") && TextUtils.isEmpty(bean.right) || bean.check.equals("0") && isReturn(bean.v)) {
                                toast("请输入" + bean.k.replace(":", ""))
                                return@setOnClickListener
                            }
                        }
                    }

                } else{
                    requestCheck=MyApplication.loginUserId + coalId
                }

                if (bean.k.equals("品名:")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        name = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        name = bean.v
                    }
                }
                if (bean.k.equals("求购数(吨):")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        purchaseQuantity = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        purchaseQuantity = bean.v
                    }
                }
                if (bean.k.equals("固定碳(%):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        fixedCarbon = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        fixedCarbon = bean.v
                    }
                }

                if (bean.k.equals("发热量(MJ/kg):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        calorificValue = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        calorificValue = bean.v
                    }
                }

                if (bean.k.equals("灰份(%):")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        ashSpecification = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        ashSpecification = bean.v
                    }
                }
                if (bean.k.equals("挥发份(%):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        volatiles = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        volatiles = bean.v
                    }
                }
                if (bean.k.equals("内水(%):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        inherentMoisture = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        inherentMoisture = bean.v
                    }
                }
                if (bean.k.equals("全硫份(%):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        totalSulfurContent = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        totalSulfurContent = bean.v
                    }
                }
                if (bean.k.equals("粘结(%):")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        bond = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        bond = bean.v
                    }
                }
                if (bean.k.equals("Y值(mm):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        Y_Value = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        Y_Value = bean.v
                    }
                }
                if (bean.k.equals("岩相:")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        lithofacies = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        lithofacies = bean.v
                    }
                }
                if (bean.k.equals("CSR:")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        CSR = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        CSR = bean.v
                    }
                }

                if (bean.k.equals("低位热值(kcal/kg):") || bean.k.equals("低位热值(kcal/kg):")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        lowerCalorificValue =if ((bean.left + "-" + bean.right).equals("-")) "" else bean.left + "-" + bean.right
                    }else if(TextUtils.isEmpty(coalId)){
                        lowerCalorificValue =if ((bean.left + "-" + bean.right).equals("-")) "" else bean.left + "-" + bean.right
                    }
                }
                if (bean.k.equals("空干基硫分(%):") || bean.k.equals("空干基硫分(%):")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        airDrySulfur =if ((bean.left + "-" + bean.right).equals("-")) "" else bean.left + "-" + bean.right
                    }else if(TextUtils.isEmpty(coalId)){
                        airDrySulfur =if ((bean.left + "-" + bean.right).equals("-")) "" else bean.left + "-" + bean.right
                    }
                }
                if (bean.k.contains("空干基挥发分(%):")) {


                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        airDryRadicalVolatiles =if ((bean.left + "-" + bean.right).equals("-")) "" else bean.left + "-" + bean.right
                    }else if(TextUtils.isEmpty(coalId)){
                        airDryRadicalVolatiles =if ((bean.left + "-" + bean.right).equals("-")) "" else bean.left + "-" + bean.right
                    }
                }

                if (bean.k.equals("详细地址:")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        placeDelivery = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        placeDelivery = bean.v
                    }
                }
                if (bean.k.equals("交货时间:")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        deliveryTime = if ((bean.left + "," + bean.right).equals(",")) "" else bean.left + "," + bean.right
                    }else if(TextUtils.isEmpty(coalId)){
                        deliveryTime = if ((bean.left + "," + bean.right).equals(",")) "" else bean.left + "," + bean.right
                    }
                }
                if (bean.k.equals("计划收货时间:")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        plannedDeliveryTime = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        plannedDeliveryTime = bean.v
                    }
                }
                if (bean.k.equals("联系人:")) {
                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        contactName = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        contactName = bean.v
                    }
                }
                if (bean.k.equals("手机号:")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        contactMobile = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        contactMobile = bean.v
                    }
                }
                if (bean.k.equals("备注:")) {

                    if(!TextUtils.isEmpty(coalId)&&bean.check.equals("3")){
                        remark = bean.v
                    }else if(TextUtils.isEmpty(coalId)){
                        remark = bean.v
                    }
                }



            }



            DataCtrlClass.releaseCoalEnquiry(mContext, coalId,coalVarietyId, name, purchaseQuantity, fixedCarbon, calorificValue
                    , ashSpecification, volatiles, inherentMoisture, totalSulfurContent, bond, Y_Value, lithofacies, CSR, lowerCalorificValue, airDrySulfur, airDryRadicalVolatiles
                    , provinceId, cityId, placeDelivery, deliveryTime, deliveryWayId, plannedDeliveryTime, contactName, contactMobile, remark, url,requestCheck, {
                if (it != null) {
                    finish()
                }
            })
        }
    }

    /*
    *
    * 交货方式
    */
    private fun getDeliveryWay() {
        //交货方式
        DataCtrlClass.deliveryWayData(mContext, {
            if (it != null) {
                deliveryWay.addAll(it)
                for (bean in deliveryWay) {
                    deliveryWayStr.add(bean.name)
                }
                mOptionsGoodsType.setPicker(deliveryWayStr)
                mOptionsGoodsType.setCyclic(false)
            }

        })
    }


    private fun initOptionPicker() {
        cocalStr.add("焦炭/焦粉/焦粒")
        cocalStr.add("炼焦煤")
        cocalStr.add("动力煤")
        mTimePicker = TimePickerView(mContext, TimePickerView.Type.YEAR_MONTH_DAY)
        mTimePicker.setRange(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) + 20)
        mTimePicker.setTime(Calendar.getInstance().time)
        mCoclaOptionPicker = OptionsPickerView<String>(mContext)
        mCoclaOptionPicker.setPicker(cocalStr)
        mCoclaOptionPicker.setCyclic(false)
        mCoclaOptionPicker.setOnoptionsSelectListener { options1, option2, options3 ->
            when (cocalStr.get(options1)) {
                "焦炭/焦粉/焦粒" -> {
                    mAdapter.setNewData(data1)
                    coalVarietyId = "1"
                }
                "炼焦煤" -> {
                    mAdapter.setNewData(data2)
                    coalVarietyId = "2"
                }

                "动力煤" -> {
                    mAdapter.setNewData(data3)
                    coalVarietyId = "3"
                }
            }
            mAdapter.notifyDataSetChanged()
        }

        mTimePicker.setOnTimeSelectListener {
            if (it != null) {
                val format = SimpleDateFormat("yyyy-MM-dd")
                if (dateType.equals("1")) {
                    entity.left = format.format(it)

                } else if (dateType.equals("2")) {
                    entity.right = format.format(it)
                } else if (dateType.equals("3")) {//计划收货时间
                    entity.v = format.format(it)
                }
                if (!TextUtils.isEmpty(entity.left) && !TextUtils.isEmpty(entity.right)) {
                    entity.v = entity.left + "," + entity.right
                    entity.check = "3"
                }
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            }
        }
        pvOptionsAddress = OptionsPickerView(this)
        Thread {
            val cityBean = JSON.parseObject<CityBean>(getJson(), CityBean::class.java)
            var city: java.util.ArrayList<String>
            var counties: java.util.ArrayList<String>
            var countiesS: java.util.ArrayList<java.util.ArrayList<String>>

            listAddress = cityBean.info.provinces
            for (p in listAddress) {
                city = java.util.ArrayList()
                countiesS = java.util.ArrayList()
                optionsProvinces.add(p.areaName)
                for (c in p.cities) {
                    city.add(c.areaName)
                    counties = c.counties.mapTo(java.util.ArrayList()) { it.areaName }
                    countiesS.add(counties)
                }
                optionsCities.add(city)
                optionsCounties.add(countiesS)
            }

        }.start()

        pvOptionsAddress.setOnoptionsSelectListener { options1, option2, options3 ->
            try {
                optionsAddress1 = options1
                optionsAddress2 = option2
                optionsAddress3 = options3
                entity.v = String.format(listAddress[options1].areaName + listAddress[options1].cities[option2].areaName)
                entity.check = "3"
                provinceId = listAddress[options1].areaId
                cityId = listAddress[options1].cities[option2].areaId
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mOptionsGoodsType = OptionsPickerView(mContext)
        mOptionsGoodsType.setOnoptionsSelectListener { options1, option2, options3 ->
            try {
                entity.v = deliveryWay[options1].name
                deliveryWayId = deliveryWay[options1].id
                entity.check = "3"
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun getJson(): String {

        val stringBuilder = StringBuilder()
        try {
            val assetManager = mContext.assets
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open("city.json")))
            var b = true
            while (b) {
                val line = bf.readLine()
                if (line != null) {
                    stringBuilder.append(line)
                } else {
                    b = false
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }


    private fun initView() {
        AndroidBug5497Workaround.assistActivity(this)
        mAdapter = ReleaseAdapter()
        mAdapter.setNewData(data1)
        mFooterView = View.inflate(mContext, R.layout.footer_release_button, null)
        mAdapter.addFooterView(mFooterView)
        mAdapter.bindToRecyclerView(mRecyclerView)
        var mLinearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLinearLayoutManager
        //这是重点
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                entity = mAdapter.data.get(position)
                when (entity.k) {
                    "煤种:" -> {
                        if (!TextUtils.isEmpty(coalId)) {
                            return
                        }
                        KeyboardUtils.hideSoftInput(this@EnquiryCocalActivity)
                        mCoclaOptionPicker.show()
                    }
                    "交货地点:" -> {
                        KeyboardUtils.hideSoftInput(this@EnquiryCocalActivity)
                        pvOptionsAddress.setPicker(optionsProvinces, optionsCities,
                                true)
                        pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2)
                        //三级选择器
                        pvOptionsAddress.setCyclic(false)
                        pvOptionsAddress.show()
                    }
                    "计划收货时间:" -> {
                        dateType = "3"
                        KeyboardUtils.hideSoftInput(this@EnquiryCocalActivity)
                        mTimePicker.show()
                    }

                    "交货方式:" -> {
                        KeyboardUtils.hideSoftInput(this@EnquiryCocalActivity)
                        mOptionsGoodsType.show()
                    }
                }

            }

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                super.onItemChildClick(adapter, view, position)
                entity = mAdapter.data.get(position)
                when (view.id) {
                    R.id.tvLeft -> {//交货时间 左边
                        KeyboardUtils.hideSoftInput(this@EnquiryCocalActivity)
                        dateType = "1"
                        mTimePicker.show()
                    }
                    R.id.tvRight -> {//交货时间 右边
                        KeyboardUtils.hideSoftInput(this@EnquiryCocalActivity)
                        dateType = "2"
                        mTimePicker.show()
                    }


                }
            }
        })
    }

    private var textType = "1" // 1 正常输入 2 左边输入 3 右边输入

    private fun initListData() {
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data1.add(ReleaseBean(2, "煤种:", "焦炭/焦粉/焦粒", "3", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "品名:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "求购数(吨):", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 15))
        data1.add(ReleaseBean(1, "固定碳(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "发热量(MJ/kg):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "灰份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "挥发份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "内水(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "全硫份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(2, "计划收货时间:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
//        data1.add(ReleaseBean(3, "交货时间:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(2, "交货地点:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(2, "交货方式:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "详细地址:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "联系人:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "手机号:", "", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data1.add(ReleaseBean(1, "备注:", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data2.add(ReleaseBean(2, "煤种:", "炼焦煤", "3", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "品名:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "求购数(吨):", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 15))
        data2.add(ReleaseBean(1, "灰份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "全硫份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "粘结(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "挥发份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "Y值(mm):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "岩相:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "CSR:", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(2, "计划收货时间:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
//        data2.add(ReleaseBean(3, "交货时间:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(2, "交货地点:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(2, "交货方式:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(1, "详细地址:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(1, "联系人:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(1, "手机号:", "", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data2.add(ReleaseBean(1, "备注:", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data3.add(ReleaseBean(2, "煤种:", "动力煤", "3", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "品名:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "求购数(吨):", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 15))
        data3.add(ReleaseBean(1, "固定碳(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "灰份(%):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "Y值(mm):", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(4, "低位热值(kcal/kg):", "", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(4, "空干基硫分(%):", "", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(4, "空干基挥发分(%):", "", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(1, "内水(%):", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(2, "计划收货时间:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
//        data3.add(ReleaseBean(3, "交货时间:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(2, "交货地点:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(2, "交货方式:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "详细地址:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "联系人:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "手机号:", "", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data3.add(ReleaseBean(1, "备注:", "", "0", InputType.TYPE_CLASS_TEXT, 15))


    }


}