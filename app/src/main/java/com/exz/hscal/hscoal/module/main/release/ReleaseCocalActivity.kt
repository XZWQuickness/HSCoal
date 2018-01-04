package com.exz.hscal.hscoal.module.main.release

import android.content.Intent
import android.os.Bundle
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
import com.exz.hscal.hscoal.utils.RecycleViewDivider
import com.exz.hscal.hscoal.utils.SZWUtils
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
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
import kotlin.collections.ArrayList

/**
 * Created by pc on 2017/12/7.
 * 煤炭
 */

class ReleaseCocalActivity : BaseActivity() {

    var data1 = ArrayList<ReleaseBean>()
    var data2 = ArrayList<ReleaseBean>()
    var data3 = ArrayList<ReleaseBean>()
    var cocalStr = ArrayList<String>()
    lateinit var mFooterView: View
    lateinit var mCoclaOptionPicker: OptionsPickerView<String>
    lateinit var mTimePicker: TimePickerView
    lateinit var entity: ReleaseBean
    var cocalType = "1"
    var dateType = ""


    var coalVarietyId: String = "1"//煤炭品种id
    var name: String = ""//品名
    var place: String = ""//产地
    var fixedCarbon: String = ""//固定碳（煤种：焦炭/焦粉/焦粒、动力煤）
    var calorificValue: String = ""//发热量（煤种：焦炭/焦粉/焦粒）
    var ashSpecification: String = ""//灰份（煤种：焦炭/焦粉/焦粒、炼焦煤、动力煤）
    var volatiles: String = ""//挥发份（煤种：焦炭/焦粉/焦粒、炼焦煤）
    var inherentMoisture: String = ""//内水（煤种：焦炭/焦粉/焦粒、动力煤）
    var totalSulfurContent: String = ""//全硫份（煤种：焦炭/焦粉/焦粒、炼焦煤）
    var bond: String = ""//粘结（煤种：炼焦煤）
    var Y_Value: String = ""//Y值（煤种：炼焦煤、动力煤）
    var lithofacies: String = ""//岩相（煤种：炼焦煤）
    var CSR: String = ""//CSR（煤种：炼焦煤）
    var lowerCalorificValue: String = ""//低位热值（煤种：动力煤）
    var airDrySulfur: String = ""//空干基硫分（煤种：动力煤）
    var ashFusionPoint: String = ""//灰熔点（煤种：动力煤）
    var airDryRadicalVolatiles: String = ""//空干基挥发分（煤种：动力煤）
    var baseVolatiles: String = ""//收到基挥发分（煤种：动力煤）
    var totalMoisture: String = ""//全水分（煤种：动力煤）
    var G_Value: String = ""//G值（煤种：动力煤）
    var remark: String = ""//备注
    var povinceId: String = ""//交货省份id
    var cityId: String = ""//交货城市id
    var deliveryTime: String = ""//交货时间，如（2017-01-01,2017-03-01）
    var deliveryWayId: String = ""//交货方式id
    var QTY: String = ""//供应数量（吨）
    var price: String = ""//价格（元/吨）
    var paymentModeId: String = ""//付款方式id
    var inspectonBody: String = ""//检验机构
    var inspectonBody_Img: String = ""//检验机构_报告图片（数据流）
    var image: String = ""//商品图（数据流）
    var url = Urls.ReleaseCoal
    private lateinit var mOptionsGoodsType: OptionsPickerView<String>
    private lateinit var mOptionsPaymentMode: OptionsPickerView<String>

    private val deliveryWay = java.util.ArrayList<TextBean>()
    private val deliveryWayStr = java.util.ArrayList<String>()
    private val paymentMode = java.util.ArrayList<TextBean>()
    private val paymentModeStr = java.util.ArrayList<String>()
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


    override fun init() {
        super.init()
        initListData()
        initView()
        initOptionPicker()
        initCamera()
        getDeliveryWay()
        getPaymentMode()
        initCommint()
    }

    private fun isReturn(content: String): Boolean {
        return content.equals("请输入值")  || content.equals("请输入可供吨数")  || content.equals("请输入单价")
                || content.equals("请选择")  || content.equals("请输入") || content.equals("请输入品名")  || content.equals("请输入产地")
    }

    private fun initCommint() {
        mFooterView.bt_submit.setOnClickListener {
            // 发布煤炭

            for (bean in mAdapter.data) {

                when (bean.lay) {
                    1 -> {
                        if (bean.k.contains("选填")  || TextUtils.isEmpty(bean.v)  || bean.check.equals("0") && isReturn(bean.v)) {
                            toast("请输入" + bean.k.replace(":", ""))
                            return@setOnClickListener
                        }
                    }
                    2 -> {
                        if (bean.k.contains("选填")  || TextUtils.isEmpty(bean.v)  || bean.check.equals("0") && isReturn(bean.v)) {
                            toast("请选择" + bean.k.replace(":", ""))
                            return@setOnClickListener
                        }
                    }
                    3 -> {
                        if (bean.k.contains("选填")  || TextUtils.isEmpty(bean.left)  || TextUtils.isEmpty(bean.right)  || bean.check.equals("0") && isReturn(bean.v)) {
                            toast("请选择" + bean.k.replace(":", ""))
                            return@setOnClickListener
                        }
                    }
                    4 -> {
                        if (bean.k.contains("选填")  || TextUtils.isEmpty(bean.left)  || TextUtils.isEmpty(bean.right)  || bean.check.equals("0") && isReturn(bean.v)) {
                            toast("请输入" + bean.k.replace(":", ""))
                            return@setOnClickListener
                        }
                    }
                }

                if (bean.k.equals("煤种:")) {
                    coalVarietyId = if (bean.v.equals("焦炭/焦粉/焦粒")) "1" else if (bean.v.equals("炼焦煤")) "2" else "3"
                }
                if (bean.k.equals("品名:")) {
                    name = bean.v
                }
                if (bean.k.equals("产地:")) {
                    place = bean.v
                }
                if (bean.k.equals("固定碳:")) {
                    fixedCarbon = bean.v
                }

                if (bean.k.equals("发热量(MJ/kg):")) {
                    calorificValue = bean.v
                }

                if (bean.k.equals("灰份(%):")) {
                    ashSpecification = bean.v
                }
                if (bean.k.equals("挥发份(%):")) {
                    volatiles = bean.v
                }
                if (bean.k.equals("内水(%):")) {
                    inherentMoisture = bean.v
                }
                if (bean.k.equals("全硫份(%):")) {
                    totalSulfurContent = bean.v
                }
                if (bean.k.equals("粘结(%):")) {
                    bond = bean.v
                }
                if (bean.k.equals("Y值(mm):")) {
                    Y_Value = bean.v
                }
                if (bean.k.equals("岩相:")) {
                    lithofacies = bean.v
                }
                if (bean.k.equals("CSR:")) {
                    CSR = bean.v
                }
                if (bean.k.equals("低位热值(kcal/kg):")) {
                    lowerCalorificValue = bean.left + "," + bean.right
                }
                if (bean.k.equals("空干基硫分(%):")) {
                    airDrySulfur = bean.left + "," + bean.right
                }
                if (bean.k.equals("灰熔点(选填):")) {
                    ashFusionPoint = bean.v
                }
                if (bean.k.equals("空干挥发分(%):")) {
                    airDryRadicalVolatiles = bean.left + "," + bean.right
                }
                if (bean.k.equals("收到基挥发份(%[选填]):")) {
                    baseVolatiles = bean.v
                }
                if (bean.k.equals("全水分(%[选填]):")) {
                    totalMoisture = bean.left + "," + bean.right
                }
                if (bean.k.equals("G值(选填):")) {
                    G_Value =bean.v
                }
                if (bean.k.equals("备注:")) {
                    remark = bean.v
                }

                if (bean.k.equals("交货时间:")) {
                    deliveryTime = bean.left + "," + bean.right
                }
                if (bean.k.equals("可供吨数(吨):")) {
                    QTY = bean.v
                }
                if (bean.k.equals("单价(元):")) {
                    price = bean.v
                }
                if (bean.k.equals("检测机构:")) {
                    inspectonBody = bean.v
                }
                if (bean.k.equals("点击上传检测报告照片:")) {
                    inspectonBody_Img = EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(Luban.with(mContext).load(bean.v).get(bean.v)))
                }
                if (bean.k.equals("点击上传产品照片:")) {
                    image = EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(Luban.with(mContext).load(bean.v).get(bean.v)))
                }


            }

            DataCtrlClass.releaseCoal(mContext, coalVarietyId, name, place, fixedCarbon, calorificValue, ashSpecification, volatiles, inherentMoisture, totalSulfurContent, bond, Y_Value, lithofacies, CSR, lowerCalorificValue, airDrySulfur, ashFusionPoint, airDryRadicalVolatiles, baseVolatiles, totalMoisture, G_Value, remark, povinceId, cityId, deliveryTime, deliveryWayId, QTY, price, paymentModeId, inspectonBody, inspectonBody_Img, image, url, {
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

    /*
    *
    * 付款方式
    */
    private fun getPaymentMode() {
        //交货方式
        DataCtrlClass.paymentMode(mContext, {
            if (it != null) {
                paymentMode.addAll(it)
                for (bean in paymentMode) {
                    paymentModeStr.add(bean.name)
                }
                mOptionsPaymentMode.setPicker(paymentModeStr)
                mOptionsPaymentMode.setCyclic(false)
            }

        })
    }

    private fun initCamera() {
        val w = ScreenUtils.getScreenWidth() * 0.2
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader()
        //显示相机
        imagePicker.isShowCamera = true
        //是否裁剪
        imagePicker.isCrop = true
        //是否按矩形区域保存裁剪图片
        imagePicker.isSaveRectangle = true
        //圖片緩存
        imagePicker.imageLoader = GlideImageLoader()
        imagePicker.isMultiMode = false//单选
        //矩形尺寸
        imagePicker.style = CropImageView.Style.RECTANGLE
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics).toInt()
        imagePicker.focusWidth = width
        imagePicker.focusHeight = width
        //圖片輸出尺寸
        imagePicker.outPutX = width
        imagePicker.outPutY = width
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
                }
                "炼焦煤" -> {
                    mAdapter.setNewData(data2)
                }

                "动力煤" -> {
                    mAdapter.setNewData(data3)
                }
            }
            mAdapter.loadMoreEnd()
        }

        mTimePicker.setOnTimeSelectListener {
            if (it != null) {
                val format = SimpleDateFormat("yyyy-MM-dd")
                if (dateType.equals("1")) {
                    entity.left = format.format(it)

                } else if (dateType.equals("2")) {
                    entity.right = format.format(it)
                }

                if (!TextUtils.isEmpty(entity.left) && !TextUtils.isEmpty(entity.right)) {
                    entity.v = entity.left + "," + entity.right
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
                povinceId = listAddress[options1].areaId
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
        mOptionsPaymentMode = OptionsPickerView(mContext)
        mOptionsPaymentMode.setOnoptionsSelectListener { options1, option2, options3 ->
            try {
                entity.v = paymentMode[options1].name
                paymentModeId = paymentMode[options1].id
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

        mAdapter = ReleaseAdapter()
        mAdapter.setNewData(data1)
        mFooterView = View.inflate(mContext, R.layout.footer_release_button, null)
        mAdapter.addFooterView(mFooterView)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.app_bg)))

        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                entity = mAdapter.data.get(position)
                when (entity.k) {
                    "煤种:" -> {
                        mCoclaOptionPicker.show()
                    }
                    "交货地点:" -> {
                        pvOptionsAddress.setPicker(optionsProvinces, optionsCities,
                                true)
                        pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2)
                        //三级选择器
                        pvOptionsAddress.setCyclic(false)
                        pvOptionsAddress.show()
                    }
                    "交货时间:" -> {
                        mTimePicker.show()                    }

                    "交货方式:" -> {
                        mOptionsGoodsType.show()
                    }
                    "付款方式:" -> {
                        mOptionsPaymentMode.show()
                    }
                    "点击上传检测报告照片:" -> {
                        PermissionCameraWithCheck(Intent(mContext, ImageGridActivity::class.java), false)
                    }
                    "点击上传产品照片:" -> {
                        PermissionCameraWithCheck(Intent(mContext, ImageGridActivity::class.java), false)
                    }
                }

            }

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                super.onItemChildClick(adapter, view, position)
                entity = mAdapter.data.get(position)
                when (view.id) {
                    R.id.tvLeft -> {//交货时间 左边
                        dateType = "1"
                        mTimePicker.show()
                    }
                    R.id.tvRight -> {//交货时间 右边
                        dateType = "2"
                        mTimePicker.show()
                    }


                }
            }
        })
    }

    private fun initListData() {
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data1.add(ReleaseBean(2, "煤种:", "焦炭/焦粉/焦粒", "3", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "品名:", "请输入品名", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "产地:", "请输入产地", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(1, "固定碳:", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "发热量(MJ/kg):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "灰份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "挥发份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "内水(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "全硫份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(1, "可供吨数(吨):", "请输入可供吨数", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "单价(元):", "请输入单价", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 15))
        data1.add(ReleaseBean(2, "付款方式:", "请选择", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(3, "交货时间:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(2, "交货方式:", "请选择", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(2, "交货地点:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "备注:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(1, "检测机构:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(5, "点击上传检测报告照片:", "0", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(5, "点击上传产品照片:", "0", "0", InputType.TYPE_CLASS_TEXT, 0))
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data2.add(ReleaseBean(2, "煤种:", "炼焦煤", "3", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "品名:", "请输入品名", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "产地:", "请输入产地", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(1, "灰份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "全硫份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "粘结(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "挥发份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "Y值(mm):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "岩相:", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(1, "CSR:", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "可供吨数(吨):", "请输入可供吨数", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(1, "单价(元):", "请输入单价", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 15))
        data2.add(ReleaseBean(2, "付款方式:", "请选择", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(3, "交货时间:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(2, "交货方式:", "请选择", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(2, "交货地点:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "备注:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(1, "检测机构:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(5, "点击上传检测报告照片:", "0", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(5, "点击上传产品照片:", "0", "0", InputType.TYPE_CLASS_TEXT, 0))
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data3.add(ReleaseBean(2, "煤种:", "动力煤", "3", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "品名:", "请输入品名", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "产地:", "请输入产地", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(4, "低位热值(kcal/kg):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(4, "空干基硫分(%):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(4, "空干挥发分(%):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(1, "内水(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "固定碳(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "收到基挥发份(%[选填]):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(4, "全水分(%[选填]):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(1, "灰份(%[选填]):", "请输入值", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "灰熔点(选填):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(2, "G值(选填):", "请选择", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(2, "Y值(mm[选填]):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "可供吨数(吨):", "请输入可供吨数", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "单价(元):", "请输入单价", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 15))
        data3.add(ReleaseBean(2, "付款方式:", "请选择", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(3, "交货时间:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(2, "交货方式:", "请输入值", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo . TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(2, "交货地点:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "备注:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(1, "检测机构:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(5, "点击上传检测报告照片:", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(5, "点击上传产品照片:", "", "0", InputType.TYPE_CLASS_TEXT, 0))


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
                val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
                entity.v =  (images[0] as ImageItem).path
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            }
        } catch (e: Exception) {
        }
    }

}
