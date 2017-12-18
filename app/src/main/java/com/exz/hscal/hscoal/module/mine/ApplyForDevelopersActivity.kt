package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import com.alibaba.fastjson.JSON
import com.bigkoo.pickerview.OptionsPickerView
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CityBean
import com.exz.hscal.hscoal.module.mine.UserInfoActivity.Companion.Intent_Text
import com.exz.hscal.hscoal.module.mine.UserInfoActivity.Companion.RESULTCODE_TEXT
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.imageloder.GlideImageLoader
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_applyfor_developers.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Created by pc on 2017/12/15.
 * 申请开发商
 */

class ApplyForDevelopersActivity : BaseActivity(), View.OnClickListener {
    private lateinit var pvOptionsAddress: OptionsPickerView<String>
    private lateinit var listAddress: List<CityBean.InfoEntity.ProvincesEntity>
    private val optionsProvinces = ArrayList<String>()
    private val optionsCities = ArrayList<ArrayList<String>>()
    private val optionsCounties = ArrayList<ArrayList<ArrayList<String>>>()
    private var optionsAddress1 = 0
    private var optionsAddress2 = 0
    private var optionsAddress3 = 0
    private var type = 0
    private var provinceId = ""
    private var cityId = ""
    private var districtId = ""
    private var logoImg = ""
    private var businessImg = ""
    override fun initToolbar(): Boolean {
        mTitle.text = "申请开发商"
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
        return R.layout.activity_applyfor_developers
    }

    override fun init() {
        super.init()
        initView()
        initCamera()
        initPicker()
    }

    private fun initCamera() {
        val w = ScreenUtils.getScreenWidth() * 0.2
        val layoutParams = iv_logo_img.layoutParams
        layoutParams.width = w.toInt()
        layoutParams.height = w.toInt()
        iv_logo_img.layoutParams = layoutParams
        iv_business_img.layoutParams = layoutParams
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

    private fun initView() {
        bt_contact_name.setOnClickListener(this)
        bt_contact_phone.setOnClickListener(this)
        bt_cardid_num.setOnClickListener(this)
        bt_shop_name.setOnClickListener(this)
        bt_shop_logo.setOnClickListener(this)
        bt_shop_phone.setOnClickListener(this)
        bt_email.setOnClickListener(this)
        bt_company_location.setOnClickListener(this)
        bt_company_address.setOnClickListener(this)
        bt_business.setOnClickListener(this)
        bt_commit.setOnClickListener(this)
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
                tv_company_location.text = String.format(listAddress[options1].areaName + listAddress[options1].cities[option2].areaName + listAddress[options1].cities[option2].counties[options3].areaName)
                provinceId = listAddress[options1].areaId
                cityId = listAddress[options1].cities[option2].areaId
                districtId = listAddress[options1].cities[option2].counties[options3].areaId
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

    override fun onClick(p0: View) {
        when (p0) {
            bt_contact_name -> {//联系人姓名
                type = 1
                var contactName = tv_contact_name.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(Intent_Text, contactName), RESULTCODE_TEXT)
            }
            bt_contact_phone -> {//联系人手机
                type = 2
                var contactPhone = tv_contact_phone.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(Intent_Text, contactPhone), RESULTCODE_TEXT)
            }
            bt_cardid_num -> {//身份证号码
                type = 3
                var carIdNum = tv_cardid_num.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "身份证号码").putExtra(Intent_Text, carIdNum), RESULTCODE_TEXT)
            }
            bt_shop_name -> {//店铺名称
                type = 4
                var shopName = tv_shop_name.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺名称").putExtra(Intent_Text, shopName), RESULTCODE_TEXT)

            }
            bt_shop_logo -> {//店铺logo
                type = 5
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)

            }
            bt_shop_phone -> {//店铺手机号
                type = 6
                var shopPhone = tv_shop_phone.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺电话").putExtra(Intent_Text, shopPhone), RESULTCODE_TEXT)
            }
            bt_email -> {//店铺邮箱
                type = 7
                var shopEmail = tv_shop_phone.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺邮箱").putExtra(Intent_Text, shopEmail), RESULTCODE_TEXT)
            }
            bt_company_location -> {//公司所在地
                type = 8
                KeyboardUtils.hideSoftInput(this)
                pvOptionsAddress.setPicker(optionsProvinces, optionsCities, optionsCounties,
                        true)
                pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2, optionsAddress3)
                //三级选择器
                pvOptionsAddress.setCyclic(false)
                pvOptionsAddress.show()
            }
            bt_company_address -> {//公司地址
                type = 9
                var address = tv_company_address.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司地址").putExtra(Intent_Text, address), RESULTCODE_TEXT)
            }
            bt_business -> {//营业执照
                type = 10
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
            }
            bt_commit -> {
                var contactName = tv_contact_name.text.toString().trim()
                if (TextUtils.isEmpty(contactName)) {
                    type=1
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(Intent_Text, contactName), RESULTCODE_TEXT)
                    return
                }
                var contactPhone = tv_contact_phone.text.toString().trim()
                if (TextUtils.isEmpty(contactPhone)) {
                    type=2
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(Intent_Text, contactPhone), RESULTCODE_TEXT)
                    return
                }
                var carIdNum = tv_cardid_num.text.toString().trim()
                if (TextUtils.isEmpty(carIdNum)) {
                    type=3
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "身份证号码").putExtra(Intent_Text, carIdNum), RESULTCODE_TEXT)
                    return
                }

                var shopName = tv_shop_name.text.toString().trim()
                if (TextUtils.isEmpty(shopName)) {
                    type=4
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺名称").putExtra(Intent_Text, shopName), RESULTCODE_TEXT)
                    return
                }
                if (TextUtils.isEmpty(logoImg)) {
                    type = 5
                    PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                    return
                }
                var shopPhone = tv_shop_phone.text.toString().trim()
                if (TextUtils.isEmpty(shopPhone)) {
                    type=6
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺电话").putExtra(Intent_Text, shopPhone), RESULTCODE_TEXT)
                    return
                }
                var shopEmail = tv_email.text.toString().trim()
                if (TextUtils.isEmpty(shopEmail)) {
                    type=7
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺邮箱").putExtra(Intent_Text, shopEmail), RESULTCODE_TEXT)
                    return
                }
                var location = tv_company_location.text.toString().trim()
                if (TextUtils.isEmpty(location)) {
                    type=8
                    KeyboardUtils.hideSoftInput(this)
                    pvOptionsAddress.setPicker(optionsProvinces, optionsCities, optionsCounties,
                            true)
                    pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2, optionsAddress3)
                    //三级选择器
                    pvOptionsAddress.setCyclic(false)
                    pvOptionsAddress.show()
                    return
                }
                var address = tv_company_address.text.toString().trim()
                if (TextUtils.isEmpty(address)) {
                    type=9
                    startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司地址").putExtra(Intent_Text, address), RESULTCODE_TEXT)
                    return
                }
                if (TextUtils.isEmpty(businessImg)) {
                    type = 10
                    PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                    return
                }
                DataCtrlClass.applyForDevelopers(mContext, contactName, contactPhone, shopName, logoImg, shopPhone, shopEmail, location, address, businessImg, {
                    finish()
                })

            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
            val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
            if (type == 5) {
                iv_logo_img.setImageURI(  "file://" + (images.get(0) as ImageItem).path)
                logoImg = EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream((images[0] as ImageItem).path))
            } else if (type == 10) {
                iv_business_img.setImageURI(  "file://" + (images.get(0) as ImageItem).path)
                businessImg = EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream((images[0] as ImageItem).path))
            }
        } else
            when (type) {
                1 -> {//联系人姓名
                    if (data != null) {
                        tv_contact_name.text = data.getStringExtra(Intent_Text)
                    }
                }
                2 -> {//联系人手机
                    if (data != null) {
                        tv_contact_phone.text = data.getStringExtra(Intent_Text)
                    }
                }
                3 -> {//身份证号码
                    if (data != null) {
                        tv_cardid_num.text = data.getStringExtra(Intent_Text)
                    }
                }
                4 -> {//店铺名称
                    if (data != null) {
                        tv_shop_name.text = data.getStringExtra(Intent_Text)
                    }
                }
                6 -> {//店铺手机号
                    if (data != null) {
                        tv_shop_phone.text = data.getStringExtra(Intent_Text)
                    }
                }
                7 -> {//店铺邮箱
                    if (data != null) {
                        tv_email.text = data.getStringExtra(Intent_Text)
                    }
                }
                9 -> {//公司地址
                    if (data != null) {
                        tv_company_address.text = data.getStringExtra(Intent_Text)
                    }
                }

            }
    }
}
