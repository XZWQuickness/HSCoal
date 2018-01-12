package com.exz.hscal.hscoal.module.mine.address;

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bigkoo.pickerview.OptionsPickerView
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.AddressBean
import com.exz.hscal.hscoal.bean.CityBean
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.widget.MyWebActivity
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_address_add_update.*
import org.jetbrains.anko.toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URLDecoder
import java.util.*

/**
 * on 2017/10/17.
 *
 */

class AddressAddOrUpdateActivity : BaseActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var pvOptionsAddress: OptionsPickerView<String>
    private lateinit var listAddress: List<CityBean.InfoEntity.ProvincesEntity>
    private val optionsProvinces = ArrayList<String>()
    private val optionsCities = ArrayList<ArrayList<String>>()
    private val optionsCounties = ArrayList<ArrayList<ArrayList<String>>>()
    private var optionsAddress1 = 0
    private var optionsAddress2 = 0
    private var optionsAddress3 = 0
    private var name = ""
    private var phone = ""
    private var zipCode = ""
    private var address = ""
    private var detail = ""
    private var cityId = ""
    private var provinceId = ""
    private var districtId = ""
    private var addressId = ""
    private var state = "1"
    private var requestCheck = ""
    private var url = Urls.SubmitShippingAddress

    override fun initToolbar(): Boolean {
        mTitle.text = getString(if (TextUtils.isEmpty(intent.getStringExtra(Intent_AddressId))) R.string.address_manager_add else R.string.address_update_name)
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, scrollView)

        toolbar.inflateMenu(R.menu.menu_favorite_goods)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = getString(R.string.address_edit_keep)
        actionView.setOnClickListener {
            name = ed_userName.text.toString().trim()
            phone = ed_userPhone.text.toString().trim()
            zipCode = ed_postal_code.text.toString().trim()
            address = bt_address.text.toString().trim()
            detail = tv_addressDetail.text.toString().trim()
            if (TextUtils.isEmpty(intent.getStringExtra(Intent_AddressId))) {

                if (TextUtils.isEmpty(name)) {
                    toast(mContext.getString(R.string.address_hint_userName))
                    return@setOnClickListener
                }

                if (TextUtils.isEmpty(phone)) {
                    toast(mContext.getString(R.string.address_hint_userPhone))
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(zipCode)) {
                    toast(mContext.getString(R.string.input_postal_code))
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(address)) {
                    toast(mContext.getString(R.string.address_hint_area))
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(detail)) {
                    toast(mContext.getString(R.string.address_hint_area_detail))
                    return@setOnClickListener
                }
                requestCheck = EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase()
            } else {
                requestCheck = EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase()
            }

            state = if (bt_setDefault.isChecked) "1" else "0"

            DataCtrlClass.AddAddressData(mContext, name, phone, zipCode, provinceId, cityId, districtId, detail, addressId, url, state, requestCheck, {
                if (it != null) {
                    //执行保存操作
                    setResult(Activity.RESULT_OK)
                    onBackPressed()
                }


            })


        }
        return false
    }

    override fun setInflateId(): Int = R.layout.activity_address_add_update

    override fun init() {
        initData()
        initPicker()
        initEvent()
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
                bt_address.text = String.format(listAddress[options1].areaName + listAddress[options1].cities[option2].areaName + listAddress[options1].cities[option2].counties[options3].areaName)
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

    private fun initData() {
        if (intent.hasExtra(Intent_AddressId) && !TextUtils.isEmpty(Intent_AddressId)) {
            bt_delete.visibility = View.VISIBLE
            addressId = intent.getStringExtra(Intent_AddressId)
            if (!TextUtils.isEmpty(Intent_AddressId)) {
                DataCtrlClass.AddressInfoData(mContext, addressId, {
                    if (it != null) {
                        ed_userName.setText(it.userName)
                        ed_userPhone.setText(it.mobile)
                        ed_postal_code.setText(it.zipCode)
                        tv_addressDetail.text = it.address
                        bt_address.text = String.format(it.provinceCity)
                        name = it.userName
                        phone = it.mobile
                        zipCode = it.zipCode
                        provinceId = it.provinceId
                        cityId = it.cityId
                        districtId = it.areaId
                        address = it.address
                        state = it.state
                        bt_setDefault.isChecked = it.isDefault()
                    }

                })
            }
        } else {
            bt_delete.visibility = View.GONE
        }
    }


    private fun initEvent() {
        if (intent.hasExtra(INTENT_IS_DELETE)) {
            bt_setDefault.visibility = if (intent.getBooleanExtra(INTENT_IS_DELETE, true)) View.VISIBLE else View.GONE
        }

        toolbar.setNavigationOnClickListener { finish() }
        bt_address.setOnClickListener(this)
        bt_delete.setOnClickListener(this)
        tv_addressDetail.setOnClickListener(this)

    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            bt_delete -> {
                DialogUtils.delete(mContext) {
                    DataCtrlClass.deleteAddressData(mContext, addressId) {
                        if (it != null) {
                            setResult(Activity.RESULT_OK)
                            onBackPressed()
                        }

                    }
                }
            }
            bt_address -> {
                KeyboardUtils.hideSoftInput(this)
                pvOptionsAddress.setPicker(optionsProvinces, optionsCities, optionsCounties,
                        true)
                pvOptionsAddress.setSelectOptions(optionsAddress1, optionsAddress2, optionsAddress3)
                //三级选择器
                pvOptionsAddress.setCyclic(false)
                pvOptionsAddress.show()
            }
            tv_addressDetail -> {
                startActivityForResult(Intent(mContext, MyWebActivity::class.java)
                        .putExtra(MyWebActivity.Intent_Title, "设置详细地址").
                        putExtra(MyWebActivity.Intent_Url, "http://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://3gimg.qq.com/lightmap/components/locationPicker2/back.html&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp")
                        , 100)
            }
        }
    }

    companion object {
        var Intent_AddressId = "shippingAddressId"
        var INTENT_IS_DELETE = "is_delete"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                if (data != null) {
                    val content = data.getStringExtra("content")
                    tv_addressDetail.text = content
                }
            }
        }
    }
}