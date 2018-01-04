package com.exz.hscal.hscoal.module.mine

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bigkoo.pickerview.OptionsPickerView
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.CheckBusinessIdentityBean
import com.exz.hscal.hscoal.bean.CityBean
import com.exz.hscal.hscoal.module.mine.UserInfoActivity.Companion.Intent_Text
import com.exz.hscal.hscoal.module.mine.UserInfoActivity.Companion.RESULTCODE_TEXT
import com.exz.hscal.hscoal.pop.SchemePop
import com.exz.hscal.hscoal.utils.DialogUtils
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.PreferencesService
import com.szw.framelibrary.imageloder.GlideImageLoader
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_applyfor_developers.*
import kotlinx.android.synthetic.main.item_address_choose.*
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

    private var url = Urls.SubmitBusinessIdentity
    private var userName = ""
    private var mobile = ""
    private var IDNumber = ""
    private var shopName = ""
    private var logoImg = ""
    private var companyName = ""
    private var companyTel = ""
    private var companyEmail = ""
    private var companyAddress = ""
    private var businessImg = ""
    private var businessAuthentication = ""

    private lateinit var mPop: SchemePop
    private lateinit var mEntity: CheckBusinessIdentityBean.CheckResultBean
    override fun initToolbar(): Boolean {
        mTitle.text = "申请供应商"
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
        if (intent.hasExtra(Intent_State)) {
            businessAuthentication = intent.getStringExtra(Intent_State)
           if(businessAuthentication.equals("2")) checkData()
            mPop = SchemePop(mContext)
        }
        bt_contact_name.setOnClickListener(this)
        bt_contact_phone.setOnClickListener(this)
        bt_cardid_num.setOnClickListener(this)
        bt_shop_name.setOnClickListener(this)
        bt_shop_logo.setOnClickListener(this)
        bt_shop_phone.setOnClickListener(this)
        bt_email.setOnClickListener(this)
        bt_company_name.setOnClickListener(this)
        bt_company_location.setOnClickListener(this)
        bt_company_address.setOnClickListener(this)
        bt_business.setOnClickListener(this)
        bt_commit.setOnClickListener(this)
    }

    /*
    *
    * 供应商审核结果接口
    *
    */
    private fun checkData() {
        DataCtrlClass.CheckBusinessIdentityData(mContext, PreferencesService.getAccountKey(mContext) ?: "", PreferencesService.getAccountValue(mContext) ?: "", {
            if (it != null) {


                mEntity = it.data!!.checkResult
                if (!TextUtils.isEmpty(mEntity.reason)) {
                    mPop.data=mEntity.reason
                    mPop.showPopupWindow()
                }


                //联系人姓名
                tv_contact_name.text = mEntity.userName.value
                setCheckTextColor(tv_contact_name, mEntity.userName.check)

                //联系人手机号
                tv_contact_phone.text = mEntity.mobile.value
                setCheckTextColor(tv_contact_phone, mEntity.mobile.check)

                //身份证
                tv_cardid_num.text = mEntity.idNumber.value
                setCheckTextColor(tv_cardid_num, mEntity.idNumber.check)


                //店铺名称
                tv_shop_name.text = mEntity.shopName.value
                setCheckTextColor(tv_shop_name, mEntity.shopName.check)

                //店铺logo
                iv_logo_img.setImageURI(mEntity.shopLogo.value)
                setCheckTextColor(tvLogo, mEntity.shopLogo.check)

                //公司名称
                tv_company_name.text = mEntity.companyName.value
                setCheckTextColor(tv_company_name, mEntity.companyName.check)

                //公司电话
                tv_shop_phone.text = mEntity.companyTel.value
                setCheckTextColor(tv_shop_phone, mEntity.companyTel.check)

                //公司邮箱
                tv_email.text = mEntity.companyEmail.value
                setCheckTextColor(tv_email, mEntity.companyEmail.check)

                //公司地址
                tv_company_address.text = mEntity.companyAddress.value
                setCheckTextColor(tv_company_address, mEntity.companyAddress.check)

                //营业执照
                iv_business_img.setImageURI(mEntity.businessLicence.value)
                setCheckTextColor(tvbusiness, mEntity.shopLogo.check)

            }
        })
    }


    private fun setCheckTextColor(text: TextView, check: String) {
        if (check.equals("1")) {//审核通过
            text.setTextColor(ContextCompat.getColor(mContext, R.color.MaterialGrey600))
        } else {//审核被拒
            text.setTextColor(ContextCompat.getColor(mContext, R.color.Red))
        }
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
            bt_company_name -> {//公司名称
                type = 6
                companyName = tv_company_name.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司名称").putExtra(Intent_Text, companyName), RESULTCODE_TEXT)

            }
            bt_shop_phone -> {//公司电话
                type = 7
                var shopPhone = tv_shop_phone.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司电话").putExtra(Intent_Text, shopPhone), RESULTCODE_TEXT)
            }

            bt_email -> {//公司邮箱
                type = 8
                var shopEmail = tv_email.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺邮箱").putExtra(Intent_Text, shopEmail), RESULTCODE_TEXT)
            }

            bt_company_location -> {//公司所在地
                type = 9
                KeyboardUtils.hideSoftInput(this)
                pvOptionsAddress.setPicker(optionsProvinces, optionsCities, optionsCounties,
                        true)
                pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2, optionsAddress3)
                //三级选择器
                pvOptionsAddress.setCyclic(false)
                pvOptionsAddress.show()
            }
            bt_company_address -> {//公司地址
                type = 10
                var address = tv_company_address.text.toString().trim()
                startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司地址").putExtra(Intent_Text, address), RESULTCODE_TEXT)
            }
            bt_business -> {//营业执照
                type = 11
                PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
            }
            bt_commit -> {

                if (businessAuthentication.equals("-1")) {
                    userName = tv_contact_name.text.toString().trim()
                    mobile = tv_contact_phone.text.toString().trim()
                    IDNumber = tv_cardid_num.text.toString().trim()
                    shopName = tv_shop_name.text.toString().trim()
                    companyName = tv_company_name.text.toString().trim()
                    companyTel = tv_shop_phone.text.toString().trim()
                    companyEmail = tv_email.text.toString().trim()
                    companyAddress = tv_company_address.text.toString().trim()
                    if (TextUtils.isEmpty(userName)) {
                        type = 1
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(Intent_Text, userName), RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(mobile)) {
                        type = 2
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(Intent_Text, mobile), RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(IDNumber)) {
                        type = 3
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "身份证号码").putExtra(Intent_Text, IDNumber), RESULTCODE_TEXT)
                        return
                    }

                    if (TextUtils.isEmpty(shopName)) {
                        type = 4
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺名称").putExtra(Intent_Text, shopName), RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(logoImg)) {
                        type = 5
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }

                    if (TextUtils.isEmpty(companyName)) {
                        type = 6
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司名称").putExtra(Intent_Text, companyName), RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(companyTel)) {
                        type = 7
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司电话").putExtra(Intent_Text, companyTel), RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(companyEmail)) {
                        type = 8
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司邮箱").putExtra(Intent_Text, companyEmail), RESULTCODE_TEXT)
                        return
                    }

//                    var location = tv_company_location.text.toString().trim()
//                    if (TextUtils.isEmpty(location)) {
//                        type = 9
//                        KeyboardUtils.hideSoftInput(this)
//                        pvOptionsAddress.setPicker(optionsProvinces, optionsCities, optionsCounties,
//                                true)
//                        pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2, optionsAddress3)
//                        //三级选择器
//                        pvOptionsAddress.setCyclic(false)
//                        pvOptionsAddress.show()
//                        return
//                    }

                    if (TextUtils.isEmpty(companyAddress)) {
                        type = 10
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司地址").putExtra(Intent_Text, companyAddress), RESULTCODE_TEXT)
                        return
                    }
                    if (TextUtils.isEmpty(businessImg)) {
                        type = 11
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }

                } else {
                    url = Urls.EditBusinessIdentity
                    if (mEntity.userName.check.equals("2")) {
                        type = 1
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人姓名").putExtra(Intent_Text, mEntity.userName.value), RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.mobile.check.equals("2")) {
                        type = 2
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "联系人手机").putExtra(Intent_Text, mEntity.mobile.value), RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.idNumber.check.equals("2")) {
                        type = 3
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "身份证号码").putExtra(Intent_Text, mEntity.idNumber.value), RESULTCODE_TEXT)
                        return
                    }

                    if (mEntity.shopName.check.equals("2")) {
                        type = 4
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "店铺名称").putExtra(Intent_Text, mEntity.shopName.value), RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.shopLogo.check.equals("2")) {
                        type = 5
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }

                    if (mEntity.companyName.check.equals("2")) {
                        type = 6
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司名称").putExtra(Intent_Text, mEntity.companyName.value), RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.companyTel.check.equals("2")) {
                        type = 7
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司电话").putExtra(Intent_Text, mEntity.companyTel.value), RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.companyEmail.check.equals("2")) {
                        type = 8
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司邮箱").putExtra(Intent_Text, mEntity.companyEmail.value), RESULTCODE_TEXT)
                        return
                    }

                    if (mEntity.companyAddress.check.equals("2")) {
                        type = 10
                        startActivityForResult(Intent(mContext, UserInfoTextActivity::class.java).putExtra(UserInfoTextActivity.Intent_ClassName, "公司地址").putExtra(Intent_Text, mEntity.companyAddress.value), RESULTCODE_TEXT)
                        return
                    }
                    if (mEntity.businessLicence.check.equals("2")) {
                        type = 11
                        PermissionCameraWithCheck(Intent(this, ImageGridActivity::class.java), false)
                        return
                    }

                }
                DataCtrlClass.applyForDevelopers(mContext, userName, mobile, IDNumber, shopName, logoImg, companyTel, companyName, companyEmail, "", companyAddress, businessImg, url, {
                    if (it != null) {

                        finish()
                    }
                })

            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) { //图片选择
            val images = data?.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
            if (type == 5) {
                iv_logo_img.setImageURI("file://" + (images.get(0) as ImageItem).path)
                logoImg = (images.get(0) as ImageItem).path
                if (businessAuthentication.equals("2")) {
                    mEntity.shopLogo.check = "1"
                    setCheckTextColor(tvLogo, mEntity.shopLogo.check)
                }
            } else if (type == 11) {
                iv_business_img.setImageURI("file://" + (images.get(0) as ImageItem).path)
                businessImg = (images.get(0) as ImageItem).path
                if (businessAuthentication.equals("2")) {
                    mEntity.businessLicence.check = "1"
                    setCheckTextColor(tvbusiness, mEntity.businessLicence.check)
                }
            }
        } else
            when (type) {
                1 -> {//联系人姓名
                    if (data != null) {
                        tv_contact_name.text = data.getStringExtra(Intent_Text)
                        if (businessAuthentication.equals("2")) {
                            userName = data.getStringExtra(Intent_Text)
                            mEntity.userName.check = "1"
                            setCheckTextColor(tv_contact_name, mEntity.userName.check)
                        }
                    }
                }
                2 -> {//联系人手机
                    if (data != null) {
                        tv_contact_phone.text = data.getStringExtra(Intent_Text)
                        if (businessAuthentication.equals("2")) {
                            mobile = data.getStringExtra(Intent_Text)
                            mEntity.mobile.check = "1"
                            setCheckTextColor(tv_contact_phone, mEntity.mobile.check)
                        }
                    }
                }
                3 -> {//身份证号码
                    if (data != null) {
                        tv_cardid_num.text = data.getStringExtra(Intent_Text)

                        if (businessAuthentication.equals("2")) {
                            IDNumber = data.getStringExtra(Intent_Text)
                            mEntity.idNumber.check = "1"
                            setCheckTextColor(tv_cardid_num, mEntity.idNumber.check)

                        }
                    }
                }
                4 -> {//店铺名称
                    if (data != null) {
                        tv_shop_name.text = data.getStringExtra(Intent_Text)

                        if (businessAuthentication.equals("2")) {
                            shopName = data.getStringExtra(Intent_Text)
                            mEntity.shopName.check = "1"
                            setCheckTextColor(tv_shop_name, mEntity.shopName.check)
                        }
                    }
                }
                6 -> {//公司名称
                    if (data != null) {
                        tv_company_name.text = data.getStringExtra(Intent_Text)

                        if (businessAuthentication.equals("2")) {
                            companyName = data.getStringExtra(Intent_Text)
                            mEntity.companyName.check = "1"
                            setCheckTextColor(tv_company_name, mEntity.companyName.check)
                        }
                    }
                }
                7 -> {//公司电话
                    if (data != null) {
                        tv_shop_phone.text = data.getStringExtra(Intent_Text)

                        if (businessAuthentication.equals("2")) {
                            companyTel = data.getStringExtra(Intent_Text)
                            mEntity.companyTel.check = "1"
                            setCheckTextColor(tv_shop_phone, mEntity.companyTel.check)

                        }
                    }
                }
                8 -> {//公司邮箱
                    if (data != null) {
                        tv_email.text = data.getStringExtra(Intent_Text)

                        if (businessAuthentication.equals("2")) {
                            companyEmail = data.getStringExtra(Intent_Text)
                            mEntity.companyEmail.check = "1"
                            setCheckTextColor(tv_email, mEntity.companyEmail.check)
                        }
                    }
                }
                10 -> {//公司地址
                    if (data != null) {
                        tv_company_address.text = data.getStringExtra(Intent_Text)
                        if (businessAuthentication.equals("2")) {
                            companyAddress = data.getStringExtra(Intent_Text)
                            mEntity.companyAddress.check = "1"
                            setCheckTextColor(tv_company_address, mEntity.companyAddress.check)
                        }
                    }
                }

            }
    }

    companion object {
        var Intent_State = "state"
    }
}
