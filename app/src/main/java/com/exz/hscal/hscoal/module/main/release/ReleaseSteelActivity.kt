package com.exz.hscal.hscoal.module.main.release

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
import com.exz.hscal.hscoal.bean.ReleaseBean
import com.exz.hscal.hscoal.bean.CityBean
import com.exz.hscal.hscoal.bean.PopStairListBean
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

/**
 * Created by pc on 2017/12/7.
 * 有色金属
 */

class ReleaseSteelActivity : BaseActivity() {

    var data1 = ArrayList<ReleaseBean>()
    var cocalStr = ArrayList<String>()
    lateinit var mFooterView: View
    lateinit var mCoclaOptionPicker: OptionsPickerView<String>
    lateinit var mTimePicker: TimePickerView
    lateinit var entity: ReleaseBean
    var cocalType = "1"
    var dateType = ""

    private lateinit var pvOptionsAddress: OptionsPickerView<String>
    private lateinit var listAddress: List<CityBean.InfoEntity.ProvincesEntity>
    private val optionsProvinces = ArrayList<String>()
    private val optionsCities = ArrayList<ArrayList<String>>()
    private val optionsCounties = ArrayList<ArrayList<ArrayList<String>>>()
    private var optionsAddress1 = 0
    private var optionsAddress2 = 0
    private var optionsAddress3 = 0
    private lateinit var mOptionsSteelClass: OptionsPickerView<String>

    private var steelClass = java.util.ArrayList<PopStairListBean>()
    private var steelClassStr = java.util.ArrayList<String>()

    private lateinit var mOptionsGoodsType: OptionsPickerView<String>
    private lateinit var mOptionsPaymentMode: OptionsPickerView<String>
    private val deliveryWay = java.util.ArrayList<TextBean>()
    private val deliveryWayStr = java.util.ArrayList<String>()
    private val paymentMode = java.util.ArrayList<TextBean>()
    private val paymentModeStr = java.util.ArrayList<String>()

    private var steelClassId = ""//有色金属分类id
    private var name = ""//品名
    private var steelworks = ""//钢厂
    private var specification = ""//规格
    private var materialQuality = ""//材质
    private var warehouse = ""//仓库
    private var remark = ""//备注
    private var provinceId = ""//交货省份id
    private var cityId = ""//交货城市id
    private var deliveryTime = ""//交货时间，如（2017-01-01,2017-03-01）
    private var deliveryWayId = ""//交货方式id
    private var weight = ""//件重,最多3位小数
    private var QTY = ""//供应数量（件）
    private var price = ""//价格（元/件）
    private var paymentModeId = ""//付款方式id
    private var inspectonBody = ""//检验机构
    private var inspectonBody_Img = ""//检验机构_报告图片
    private var image = ""//商品图

    var url = Urls.ReleaseSteel

    private lateinit var mAdapter: ReleaseAdapter<ReleaseBean>
    override fun initToolbar(): Boolean {
        mTitle.text = mContext.getString(R.string.metals)
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

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)

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
        initPicker()
        initSteelClass()
        getDeliveryWay()
        getPaymentMode()
        initCommint()
    }

    private fun isReturn(content: String): Boolean {
        return content.equals("请输入值") || content.equals("请输入可供吨数") || content.equals("请输入单价")
                || content.equals("请选择") || content.equals("请输入") || content.equals("请输入品名") || content.equals("请输入产地")
    }

    private fun initCommint() {
        mFooterView.bt_submit.setOnClickListener {
            // 发布煤炭

            for (bean in mAdapter.data) {

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
                        if (!bean.k.contains("选填") && TextUtils.isEmpty(bean.left) || TextUtils.isEmpty(bean.right) || bean.check.equals("0") && isReturn(bean.v)) {
                            toast("请选择" + bean.k.replace(":", ""))
                            return@setOnClickListener
                        }
                    }
                    4 -> {
                        if (!bean.k.contains("选填") && TextUtils.isEmpty(bean.left) || TextUtils.isEmpty(bean.right) || bean.check.equals("0") && isReturn(bean.v)) {
                            toast("请输入" + bean.k.replace(":", ""))
                            return@setOnClickListener
                        }
                    }
                }


                if (bean.k.equals("品名:")) {
                    name = bean.v
                }
                if (bean.k.equals("钢厂:")) {
                    steelworks = bean.v
                }
                if (bean.k.equals("规格:")) {
                    specification = bean.v
                }
                if (bean.k.equals("材质:")) {
                    materialQuality = bean.v
                }
                if (bean.k.equals("仓库:")) {
                    warehouse = bean.v
                }
                if (bean.k.equals("备注:")) {
                    remark = bean.v
                }

                if (bean.k.equals("交货时间:")) {
                    deliveryTime = bean.left + "," + bean.right
                }
                if (bean.k.equals("件重:")) {
                    weight = bean.v
                }
                if (bean.k.equals("可供数(件):")) {
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
            DataCtrlClass.releaseSteel(mContext, steelClassId, name, steelworks, specification, materialQuality, warehouse, remark, provinceId, cityId, deliveryTime,
                    deliveryWayId, weight, QTY, price, paymentModeId
                    , inspectonBody, inspectonBody_Img, image, url, {
                if (it != null) {
                    finish()
                }
            })

        }
    }

    private fun initSteelClass() {
        DataCtrlClass.steelClassData(mContext, {
            if (it != null) {
                steelClass = it as ArrayList<PopStairListBean>
                for (bean in steelClass) {
                    steelClassStr.add(bean.name)
                }

                mOptionsSteelClass.setPicker(steelClassStr)
                mOptionsSteelClass.setCyclic(false)
            }
        })

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
            }
            mAdapter.loadMoreEnd()
        }

        mTimePicker.setOnTimeSelectListener {
            if (it != null) {
                val format = SimpleDateFormat("yyyy-MM-dd-HH")
                if (dateType.equals("1")) {
                    entity.left = format.format(it)

                } else if (dateType.equals("2")) {
                    entity.right = format.format(it)
                }

                if (!TextUtils.isEmpty(entity.left) && !TextUtils.isEmpty(entity.right)) {
                    entity.v = entity.left + "," + entity.right
                    entity.check = "3"
                }
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            }
        }

        mOptionsSteelClass = OptionsPickerView(mContext)
        mOptionsSteelClass.setOnoptionsSelectListener { options1, option2, options3 ->
            steelClassId = steelClass.get(options1).id
            entity.v = steelClass.get(options1).name
            entity.check = "3"
            mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))

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
                    "类别:" -> {
                        KeyboardUtils.hideSoftInput(mContext as ReleaseSteelActivity)
                        mOptionsSteelClass.show()
                    }
                    "交货地点:" -> {
                        KeyboardUtils.hideSoftInput(mContext as ReleaseSteelActivity)
                        pvOptionsAddress.setPicker(optionsProvinces, optionsCities,
                                true)
                        pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2)
                        //三级选择器
                        pvOptionsAddress.setCyclic(false)
                        pvOptionsAddress.show()
                    }
                    "交货时间:" -> {
                        KeyboardUtils.hideSoftInput(this@ReleaseSteelActivity)
                        mTimePicker.show()
                    }
                    "交货方式:" -> {
                        KeyboardUtils.hideSoftInput(this@ReleaseSteelActivity)
                        mOptionsGoodsType.show()
                    }

                    "付款方式:" -> {
                        KeyboardUtils.hideSoftInput(this@ReleaseSteelActivity)
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
        data1.add(ReleaseBean(2, "类别:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "品名:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "钢厂:", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(1, "规格:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "材质:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "仓库:", "", "0", InputType.TYPE_CLASS_TEXT, 15))

        data1.add(ReleaseBean(1, "件重:", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "可供数(件):", "", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data1.add(ReleaseBean(1, "单价(元):", "", "0", EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, 15))

        data1.add(ReleaseBean(2, "付款方式:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(3, "交货时间:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(2, "交货方式:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(2, "交货地点:", "", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "备注:", "", "0", InputType.TYPE_CLASS_TEXT, 15))

        data1.add(ReleaseBean(1, "检测机构:", "", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(5, "点击上传检测报告照片:", "", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(5, "点击上传产品照片:", "", "0", InputType.TYPE_CLASS_TEXT, 0))


    }

    private fun initPicker() {
        pvOptionsAddress = OptionsPickerView(this)
        Thread {
            val cityBean = JSON.parseObject<CityBean>(getJson(), CityBean::class.java)
            var city: ArrayList<String>
            var counties: ArrayList<String>
            var countiesS: ArrayList<ArrayList<String>>

            listAddress = cityBean.info.provinces
            for (p in listAddress) {
                city = ArrayList()
                countiesS = ArrayList()
                optionsProvinces.add(p.areaName)
                for (c in p.cities) {
                    city.add(c.areaName)
                    counties = c.counties.mapTo(ArrayList()) { it.areaName }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
                val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
                entity.v = (images[0] as ImageItem).path
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            }
        } catch (e: Exception) {
        }
    }
}