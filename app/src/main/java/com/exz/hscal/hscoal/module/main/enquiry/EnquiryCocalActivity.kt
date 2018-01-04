package com.exz.hscal.hscoal.module.main.enquiry

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.TypedValue
import android.view.View
import com.bigkoo.pickerview.OptionsPickerView
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.ReleaseAdapter
import com.exz.hscal.hscoal.bean.ReleaseBean
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
    var cocalType = "1"
    var dateType = ""

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
        return R.layout.activity_release_cocale
    }

    override fun init() {
        super.init()
        initListData()
        initView()
        initOptionPicker()
        initCamera()
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
                val format = SimpleDateFormat("yyyy-MM-dd-HH")
                if (dateType.equals("1")) {
                    entity.left = format.format(it)

                } else if (dateType.equals("2")) {
                    entity.right = format.format(it)
                }
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
                    "交货时间:" -> {
                        mTimePicker.show()
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
        data1.add(ReleaseBean(1, "求购数(吨):", "请输入求购数", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(1, "固定碳:", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "发热量(MJ/kg):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "灰份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "挥发份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "内水(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "全硫份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 15))
        data1.add(ReleaseBean(2, "计划收货时间:", "请选择", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(2, "交货地点:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data1.add(ReleaseBean(1, "详细地址:", "请输入详细地址", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "联系人:", "请输入联系人", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data1.add(ReleaseBean(1, "手机号:", "请输入手机号", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data1.add(ReleaseBean(1, "备注:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 15))
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data2.add(ReleaseBean(2, "煤种:", "炼焦煤", "3", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "品名:", "请输入品名", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "求购数(吨):", "请输入求购数", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(1, "灰份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "全硫份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "粘结(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "挥发份(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "Y值(mm):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "岩相:", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "CSR:", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 15))
        data2.add(ReleaseBean(2, "计划收货时间:", "请选择", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(2, "交货地点:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data2.add(ReleaseBean(1, "详细地址:", "请输入详细地址", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(1, "联系人:", "请输入联系人", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data2.add(ReleaseBean(1, "手机号:", "请输入手机号", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data2.add(ReleaseBean(1, "备注:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 15))
        //lay 1 输入文本 2 选择文本 3  交货时间 4 输入区间 5 图片
        data3.add(ReleaseBean(2, "煤种:", "动力煤", "3", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "品名:", "请输入品名", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "求购数(吨):", "请输入求购数", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(4, "低位热值(kcal/kg):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(4, "空干基硫分(%):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(4, "空干挥发分(%):", "请输入值", "0", InputType.TYPE_CLASS_NUMBER, 0))
        data3.add(ReleaseBean(1, "内水(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "固定碳(%):", "请输入值", "0", InputType.TYPE_CLASS_TEXT, 15))
        data3.add(ReleaseBean(2, "计划收货时间:", "请选择", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(2, "交货地点:", "请选择", "0", InputType.TYPE_CLASS_TEXT, 0))
        data3.add(ReleaseBean(1, "详细地址:", "请输入详细地址", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "联系人:", "请输入联系人", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL, 0))
        data3.add(ReleaseBean(1, "手机号:", "请输入手机号", "0", InputType.TYPE_CLASS_NUMBER, 15))
        data3.add(ReleaseBean(1, "备注:", "请输入", "0", InputType.TYPE_CLASS_TEXT, 15))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
                val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
                entity.v = "file://" + (images[0] as ImageItem).path
                mAdapter.notifyItemChanged(mAdapter.data.indexOf(entity))
            }
        } catch (e: Exception) {
        }
    }

}