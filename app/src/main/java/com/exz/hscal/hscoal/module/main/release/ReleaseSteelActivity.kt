package com.exz.hscal.hscoal.module.main.release

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bigkoo.pickerview.OptionsPickerView
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.ReleaseAdapter
import com.exz.hscal.hscoal.bean.ReleaseBean
import com.exz.hscal.hscoal.bean.CityBean
import com.exz.hscal.hscoal.utils.RecycleViewDivider
import com.exz.hscal.hscoal.utils.SZWUtils
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.imageloder.GlideImageLoader
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_release_cocale.*
import org.jetbrains.anko.toast
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
        return R.layout.activity_release_cocale
    }

    override fun init() {
        super.init()
        initListData()
        initView()
        initOptionPicker()
        initCamera()
        initPicker()
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
                entity.v = format.format(it)
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
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
                    "交货地点:" -> {
                        KeyboardUtils.hideSoftInput(mContext as ReleaseSteelActivity)
                        pvOptionsAddress.setPicker(optionsProvinces, optionsCities, optionsCounties,
                                true)
                        pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2, optionsAddress3)
                        //三级选择器
                        pvOptionsAddress.setCyclic(false)
                        pvOptionsAddress.show()
                    }
                    "计划收货时间:" -> {
                        mTimePicker.show()
                    }
                }

            }

        })
    }

    private fun initListData() {
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data1.add(ReleaseBean(2, "类别:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "品名:", "请输入品名", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "件重:", "请输入件重", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data1.add(ReleaseBean(1, "求购数(件):", "请输入求购数", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data1.add(ReleaseBean(1, "规格:", "请输规格", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "材质:", "请输入材质", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(2, "计划收货时间:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(2, "交货地点:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "详细地址:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "联系人:", "请输入", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "手机号:", "请输入", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data1.add(ReleaseBean(1, "备注:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
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
                entity.v = String.format(listAddress[options1].areaName + listAddress[options1].cities[option2].areaName + listAddress[options1].cities[option2].counties[options3].areaName)
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
//                provinceId = listAddress[options1].areaId
//                cityId = listAddress[options1].cities[option2].areaId
//                districtId = listAddress[options1].cities[option2].counties[options3].areaId
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


}