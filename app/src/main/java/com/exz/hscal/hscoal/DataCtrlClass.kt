package com.exz.hscal.hscoal

import android.content.Context
import android.text.TextUtils
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.FileIOUtils
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.bean.*
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.net.NetEntity
import com.szw.framelibrary.utils.net.callback.DialogCallback
import com.szw.framelibrary.utils.net.callback.JsonCallback
import org.jetbrains.anko.toast
import top.zibin.luban.Luban
import java.util.*

/**
 * Created by pc on 2017/12/4.
 */

object DataCtrlClass {

    /**
     * banner
     * */
    fun bannerData(context: Context, listener: (bannersBean: ArrayList<BannersBean>?) -> Unit) {
//        type	string	必填	位置

        val params = HashMap<String, String>()
        params.put("requestCheck", EncryptUtils.encryptMD5ToString("Banner", MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ArrayList<BannersBean>>>(Urls.HomeBanner)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ArrayList<BannersBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ArrayList<BannersBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ArrayList<BannersBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })


    }

    /**
     * banner
     * */
    fun TopNewsData(context: Context, listener: (bannersBean: ArrayList<NewsBean>?) -> Unit) {
//        type	string	必填	位置

        val params = HashMap<String, String>()
        params.put("requestCheck", EncryptUtils.encryptMD5ToString("TopNews", MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ArrayList<NewsBean>>>(Urls.TopNews)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ArrayList<NewsBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ArrayList<NewsBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ArrayList<NewsBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 首页_成交量/成交金额
     * */
    fun TurnoverData(context: Context, listener: (s: TurnoverBean?) -> Unit) {

        val params = HashMap<String, String>()
//      params.put("jpushToken", JPushInterface.getRegistrationID(this))
        params.put("requestCheck", EncryptUtils.encryptMD5ToString("Turnover", MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<TurnoverBean>>(Urls.Turnover)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<TurnoverBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<TurnoverBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<TurnoverBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 首页_热门
     * */
    fun HomeHotData(context: Context, url: String, requestCheck: String, listener: (bannersBean: ArrayList<CargoListBean>?) -> Unit) {
//        type	string	必填	位置

        val params = HashMap<String, String>()
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(requestCheck, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ArrayList<CargoListBean>>>(url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ArrayList<CargoListBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ArrayList<CargoListBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ArrayList<CargoListBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 登录
     * */
    fun login(context: Context, mobile: String, pwd: String, listener: (s: LoginEntity?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("mobile", mobile)
        params.put("password", pwd)
        params.put("deviceType", "1")
//      params.put("jpushToken", JPushInterface.getRegistrationID(this))
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(mobile + pwd, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<LoginEntity>>(Urls.Login)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<LoginEntity>>(context) {
                    override fun onSuccess(response: Response<NetEntity<LoginEntity>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<LoginEntity>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 登录
     * */
    fun loginNoDialog(mobile: String, pwd: String, listener: (s: LoginEntity?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("mobile", mobile)
        params.put("password", pwd)
        params.put("deviceType", "1")
//      params.put("jpushToken", JPushInterface.getRegistrationID(this))
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(mobile + pwd, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<LoginEntity>>(Urls.Login)
                .params(params)
                .tag(this)
                .execute(object : JsonCallback<NetEntity<LoginEntity>>() {
                    override fun onSuccess(response: Response<NetEntity<LoginEntity>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<LoginEntity>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 获取验证码
     * @param[phone] string	必填	手机号
     * @param[purpose] string	必填	用途：1注册，2忘记密码，3设置支付密码
     * */
    fun getSecurityCode(context: Context, phone: String, purpose: String, listener: (NetEntity<Void>?) -> Unit) {
//        phone	string	必填	手机号
//        purpose	string	必填	用途：1注册，2忘记密码，3设置支付密码
//        requestCheck	string	必填	验证请求

        val params = HashMap<String, String>()
        params.put("mobile", phone)
        params.put("purpose", purpose)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(phone + MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.VerifyCode)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                        context.toast(response.body().message)
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 注册
     * @param[phone] string	必填	手机号
     * @param[code] string	必填	验证码
     * @param[pwd] string	必填	密码
     * @param[wechat] string	必填	微信号
     * @param[invitePhone] string	选填	推荐人手机号
     * */
    fun register(context: Context, phone: String, code: String, pwd: String, password: String, jpushToken: String, listener: (string: LoginEntity?) -> Unit) {
//        phone	string	必填	手机号
//        code	string	必填	验证码
//        pwd	string	必填	密码
//        wechat	string	必填	微信号
//        putPhone	string	选填	推荐人手机号
//        deviceType	string	必填	设备类型：1 Android；2 iOS
//        jpushToken	string	选填	极光推送令牌
//        requestCheck	string	必填	验证请求 "手机号+验证码+秘钥"的 MD5加密

        val params = HashMap<String, String>()
        params.put("mobile", phone)
        params.put("code", code)
        params.put("password", password)
        params.put("jpushToken", jpushToken)
        params.put("deviceType", "1")
//        params.put("jpushToken", JPushInterface.getRegistrationID(this))
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(phone + code, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<LoginEntity>>(Urls.Register)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<LoginEntity>>(context) {
                    override fun onSuccess(response: Response<NetEntity<LoginEntity>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<LoginEntity>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 忘记密码
     * */
    fun forgetPwd(context: Context, mobile: String, code: String, pwd: String, listener: (NetEntity<Void>?) -> Unit) {
//        phone	string	必填	手机号
//        code	string	必填	验证码
//        pwd	string	必填	密码
//        requestCheck	string	必填	验证请求

        val params = HashMap<String, String>()
        params.put("mobile", mobile)
        params.put("code", code)
        params.put("password", pwd)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(mobile + code, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.ForgetPwd)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                        context.toast(response.body().message)
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 忘记密码
     * */
    fun editPwd(context: Context, oldPwd: String, pwd: String, listener: (NetEntity<Void>?) -> Unit) {
//        phone	string	必填	手机号
//        code	string	必填	验证码
//        pwd	string	必填	密码
//        requestCheck	string	必填	验证请求

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("oldPassword", oldPwd)
        params.put("newPassword", pwd)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + pwd, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.EditPwd)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                        context.toast(response.body().message)
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 获取用户信息
     * */
    fun getUserInfo(context: Context, listener: (s: UserBean?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<UserBean>>(Urls.getUserInfo)
                .params(params)
                .tag(this)
                .execute(object : JsonCallback<NetEntity<UserBean>>() {

                    override fun onSuccess(response: Response<NetEntity<UserBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                            context.toast(response.body().message)
                        }
                    }

                    override fun onError(response: Response<NetEntity<UserBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 编辑个人信息
     * */
    fun editPersonInfo(context: Context, key: String, value: String, listener: (data: NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)//用户id
        if (key == "headImg")
            params.put(key, EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(value)))
        else
            params.put(key, value)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.UpdateUserInfo)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                            context.toast(response.message())
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     *地址选择列表 15105200983
     * */
    fun addressChooseData(context: Context, listener: (addressBean: List<AddressBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<AddressBean>>>(Urls.AddressList)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<AddressBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<AddressBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<AddressBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 地址编辑 删除
     * */
    fun deleteAddressData(context: Context, shippingAddressId: String, listener: (addressBean: NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("shippingAddressId", shippingAddressId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + shippingAddressId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.DeleteAddress)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 地址编辑 设置默认
     * */
    fun setAddressData(context: Context, shippingAddressId: String, listener: (addressBean: NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("shippingAddressId", shippingAddressId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + shippingAddressId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.AddressDefault)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     *  新增收货地址（当用户添加地址时，后台判断该用户是否有其他地址，若没有，将该地址设为默认地址）
     * */
    fun AddAddressData(context: Context, name: String, phone: String, zipCode: String, provinceId: String, cityId: String, districtId: String, detail: String, addressId: String, url: String, state: String, requestCheck: String, listener: (addressBean: NetEntity<Void>?) -> Unit) {
        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(addressId)) params.put("shippingAddressId", addressId)
        if (!TextUtils.isEmpty(name)) params.put("userName", name)
        if (!TextUtils.isEmpty(phone)) params.put("mobile", phone)
        if (!TextUtils.isEmpty(zipCode)) params.put("zipCode", zipCode)
        if (!TextUtils.isEmpty(provinceId)) params.put("provinceId", provinceId)
        if (!TextUtils.isEmpty(cityId)) params.put("cityId", cityId)
        if (!TextUtils.isEmpty(districtId)) params.put("areaId", districtId)
        if (!TextUtils.isEmpty(detail)) params.put("address", detail)
        if (!TextUtils.isEmpty(state)) params.put("state", state)
        params.put("requestCheck", requestCheck)
        OkGo.post<NetEntity<Void>>(url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 收货地址详情
     * */
    fun AddressInfoData(context: Context, addressId: String, listener: (s: AddressBean?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("shippingAddressId", addressId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + addressId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<AddressBean>>(Urls.ShippingAddressInfo)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<AddressBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<AddressBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<AddressBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 报价列表
     * */
    fun OfferListData(context: Context, currentPage: Int, listener: (addressBean: List<OfferBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<OfferBean>>>(Urls.url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<OfferBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<OfferBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<OfferBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 询盘订单
     * */
    fun InquiryOrderListData(context: Context, currentPage: Int, listener: (addressBean: List<InquiryOrderBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<InquiryOrderBean>>>(Urls.url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<InquiryOrderBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<InquiryOrderBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<InquiryOrderBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 商品管理
     * */
    fun GoodsManageListData(context: Context, currentPage: Int, listener: (addressBean: List<CargoListBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<CargoListBean>>>(Urls.url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<CargoListBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<CargoListBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<CargoListBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 申请开发商
     */
    fun applyForDevelopers(context: Context, contactName: String, contactPhone: String, IDNumber: String, shopName: String, logoImg: String, shopPhone: String, companyName: String, shopEmail: String, location: String, address: String, businessImg: String, url: String, listener: (NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(contactName)) params.put("userName", contactName)
        if (!TextUtils.isEmpty(contactPhone)) params.put("mobile", contactPhone)
        if (!TextUtils.isEmpty(IDNumber)) params.put("IDNumber", IDNumber)
        if (!TextUtils.isEmpty(shopName)) params.put("shopName", shopName)
        if (!TextUtils.isEmpty(logoImg)) params.put("shopLogo", EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(Luban.with(context).load(logoImg).get(logoImg))))
        if (!TextUtils.isEmpty(companyName)) params.put("companyName", companyName)
        if (!TextUtils.isEmpty(shopPhone)) params.put("companyTel", shopPhone)
        if (!TextUtils.isEmpty(shopEmail)) params.put("companyEmail", shopEmail)
        if (!TextUtils.isEmpty(address)) params.put("companyAddress", address)
        if (!TextUtils.isEmpty(businessImg)) params.put("businessLicence", EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(Luban.with(context).load(businessImg).get(businessImg))))
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 供应商审核结果接口
     */
    fun CheckBusinessIdentityData(context: Context, phone: String, passowrd: String, listener: (NetEntity<CheckBusinessIdentityBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(phone)) params.put("mobile", phone)
        if (!TextUtils.isEmpty(passowrd)) params.put("password", passowrd)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(phone, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<CheckBusinessIdentityBean>>(Urls.CheckBusinessIdentity)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<CheckBusinessIdentityBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<CheckBusinessIdentityBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<CheckBusinessIdentityBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 申请司机
     * */
    fun applyForDriver(context: Context, userName: String, mobile: String, driverLicense: String, vehicleLicense: String, url: String, listener: (NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(userName)) params.put("userName", userName)
        if (!TextUtils.isEmpty(mobile)) params.put("mobile", mobile)
        if (!TextUtils.isEmpty(driverLicense)) params.put("driverLicense", EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(Luban.with(context).load(driverLicense).get(driverLicense))))
        if (!TextUtils.isEmpty(vehicleLicense)) params.put("vehicleLicense", EncodeUtils.base64Encode2String(FileIOUtils.readFile2BytesByStream(Luban.with(context).load(vehicleLicense).get(vehicleLicense))))
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 司机审核结果接口
     */
    fun checkDriverIdentityData(context: Context, phone: String, passowrd: String, listener: (NetEntity<CheckDriverIdentityBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(phone)) params.put("mobile", phone)
        if (!TextUtils.isEmpty(passowrd)) params.put("password", passowrd)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(phone, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<CheckDriverIdentityBean>>(Urls.CheckDriverIdentity)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<CheckDriverIdentityBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<CheckDriverIdentityBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<CheckDriverIdentityBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 交货方式
     * */
    fun deliveryWayData(context: Context, listener: (addressBean: List<TextBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("requestCheck", EncryptUtils.encryptMD5ToString("DeliveryWay", MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<TextBean>>>(Urls.DeliveryWay)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<TextBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<TextBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<TextBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 付款方式
     * */
    fun paymentMode(context: Context, listener: (addressBean: List<TextBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("requestCheck", EncryptUtils.encryptMD5ToString("PaymentMode", MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<TextBean>>>(Urls.PaymentMode)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<TextBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<TextBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<TextBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 发布煤炭（供应商权限）
     * */
    fun releaseCoal(context: Context, coalVarietyId: String, name: String, place: String, fixedCarbon: String, calorificValue: String, ashSpecification: String, volatiles: String, inherentMoisture: String,
                    totalSulfurContent: String, bond: String, y_Value: String, lithofacies: String, csr: String, lowerCalorificValue: String, airDrySulfur: String, ashFusionPoint: String, airDryRadicalVolatiles: String,
                    baseVolatiles: String, totalMoisture: String, g_Value: String, remark: String, provinceId: String, cityId: String, deliveryTime: String, deliveryWayId: String, qty: String,
                    price: String, paymentModeId: String, inspectonBody: String, inspectonBody_Img: String, image: String, url: String, listener: (NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(coalVarietyId)) params.put("coalVarietyId", coalVarietyId)
        if (!TextUtils.isEmpty(name)) params.put("name", name)
        if (!TextUtils.isEmpty(place)) params.put("place", place)
        if (!TextUtils.isEmpty(fixedCarbon)) params.put("fixedCarbon", fixedCarbon)
        if (!TextUtils.isEmpty(calorificValue)) params.put("calorificValue", calorificValue)
        if (!TextUtils.isEmpty(ashSpecification)) params.put("ashSpecification", ashSpecification)
        if (!TextUtils.isEmpty(volatiles)) params.put("volatiles", volatiles)
        if (!TextUtils.isEmpty(inherentMoisture)) params.put("inherentMoisture", inherentMoisture)
        if (!TextUtils.isEmpty(totalSulfurContent)) params.put("totalSulfurContent", totalSulfurContent)
        if (!TextUtils.isEmpty(bond)) params.put("bond", bond)
        if (!TextUtils.isEmpty(y_Value)) params.put("y_Value", y_Value)
        if (!TextUtils.isEmpty(lithofacies)) params.put("lithofacies", lithofacies)
        if (!TextUtils.isEmpty(csr)) params.put("csr", csr)
        if (!TextUtils.isEmpty(lowerCalorificValue)) params.put("lowerCalorificValue", lowerCalorificValue)
        if (!TextUtils.isEmpty(airDrySulfur)) params.put("airDrySulfur", airDrySulfur)
        if (!TextUtils.isEmpty(ashFusionPoint)) params.put("ashFusionPoint", ashFusionPoint)
        if (!TextUtils.isEmpty(airDryRadicalVolatiles)) params.put("airDryRadicalVolatiles", airDryRadicalVolatiles)
        if (!TextUtils.isEmpty(baseVolatiles)) params.put("baseVolatiles", baseVolatiles)
        if (!TextUtils.isEmpty(totalMoisture)) params.put("totalMoisture", totalMoisture)
        if (!TextUtils.isEmpty(g_Value)) params.put("g_Value", g_Value)
        if (!TextUtils.isEmpty(remark)) params.put("remark", remark)
        if (!TextUtils.isEmpty(provinceId)) params.put("provinceId", provinceId)
        if (!TextUtils.isEmpty(cityId)) params.put("cityId", cityId)
        if (!TextUtils.isEmpty(deliveryTime)) params.put("deliveryTime", deliveryTime)
        if (!TextUtils.isEmpty(deliveryWayId)) params.put("deliveryWayId", deliveryWayId)
        if (!TextUtils.isEmpty(qty)) params.put("qty", qty)
        if (!TextUtils.isEmpty(price)) params.put("price", price)
        if (!TextUtils.isEmpty(paymentModeId)) params.put("paymentModeId", paymentModeId)
        if (!TextUtils.isEmpty(inspectonBody)) params.put("inspectonBody", inspectonBody)
        if (!TextUtils.isEmpty(inspectonBody_Img)) params.put("inspectonBody_Img", inspectonBody_Img)
        if (!TextUtils.isEmpty(image)) params.put("image", image)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + coalVarietyId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(url)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })

    }


    /**
     * 煤炭货源
     * */
    fun CoalListData(context: Context, page: Int, keyword: String, coalVarietyId: String, provinceId: String, cityId: String, sortType: String, listener: (bannersBean: ArrayList<CargoListBean>?) -> Unit) {
//        type	string	必填	位置

        val params = HashMap<String, String>()
        if (!TextUtils.isEmpty(keyword)) params.put("keyword", keyword)
        if (!TextUtils.isEmpty(coalVarietyId)) params.put("coalVarietyId", coalVarietyId)
        if (!TextUtils.isEmpty(provinceId)) params.put("provinceId", provinceId)
        if (!TextUtils.isEmpty(cityId)) params.put("cityId", cityId)
        if (!TextUtils.isEmpty(sortType)) params.put("sortType", sortType)
        if (!TextUtils.isEmpty(page.toString())) params.put("page", page.toString())
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(page.toString(), MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ArrayList<CargoListBean>>>(Urls.CoalList)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ArrayList<CargoListBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ArrayList<CargoListBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ArrayList<CargoListBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 煤炭货源详情
     */
    fun getCoalInfo(context: Context, coalEnquiryId: String, listener: (NetEntity<Void>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("coalEnquiryId", coalEnquiryId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(coalEnquiryId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<Void>>(Urls.GetCoalInfo)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<Void>>(context) {
                    override fun onSuccess(response: Response<NetEntity<Void>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<Void>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 有色金属分类
     * */
    fun steelClassData(context: Context, listener: (addressBean: List<PopStairListBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("requestCheck", EncryptUtils.encryptMD5ToString("SteelClass", MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<List<PopStairListBean>>>(Urls.SteelClass)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<List<PopStairListBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<List<PopStairListBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<List<PopStairListBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 煤炭货源
     * */
    fun SteelListtData(context: Context, page: Int, keyword: String, steelClassId: String, provinceId: String, cityId: String, sortType: String, listener: (bannersBean: ArrayList<CargoListBean>?) -> Unit) {
//        type	string	必填	位置

        val params = HashMap<String, String>()
        if (!TextUtils.isEmpty(keyword)) params.put("keyword", keyword)
        if (!TextUtils.isEmpty(steelClassId)) params.put("coalVarietyId", steelClassId)
        if (!TextUtils.isEmpty(provinceId)) params.put("provinceId", provinceId)
        if (!TextUtils.isEmpty(cityId)) params.put("cityId", cityId)
        if (!TextUtils.isEmpty(sortType)) params.put("sortType", sortType)
        if (!TextUtils.isEmpty(page.toString())) params.put("page", page.toString())
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(page.toString(), MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ArrayList<CargoListBean>>>(Urls.SteelList)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ArrayList<CargoListBean>>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ArrayList<CargoListBean>>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body().data)
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ArrayList<CargoListBean>>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 有色金属货源详情
     */
    fun getSteelInfo(context: Context, steelId: String, listener: (NetEntity<SeekSteelDetailentity>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("steelId", steelId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(steelId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<SeekSteelDetailentity>>(Urls.GetSteelInfo)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<SeekSteelDetailentity>>(context) {
                    override fun onSuccess(response: Response<NetEntity<SeekSteelDetailentity>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<SeekSteelDetailentity>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }


    /**
     * 确认订单_配送地址
     */
    fun confirmOrderShippingAddress(context: Context, shippingAddressId: String, listener: (NetEntity<AddressBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        if (!TextUtils.isEmpty(shippingAddressId)) params.put("shippingAddressId", shippingAddressId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(shippingAddressId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<AddressBean>>(Urls.ConfirmOrder_ShippingAddress)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<AddressBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<AddressBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<AddressBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 获取确认订单信息
     */
    fun ConfirmOrderData(context: Context, type: String, objectId: String, listener: (NetEntity<ConfirmOrderBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("type", type)
        params.put("objectId", objectId)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + objectId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ConfirmOrderBean>>(Urls.ConfirmOrder)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ConfirmOrderBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ConfirmOrderBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ConfirmOrderBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }

    /**
     * 生成订单
     *
     */
    fun GenerateOrder(context: Context, type: String, objectId: String, deliveryWayId: String, shippingAddressId: String, count: String, remark: String, listener: (NetEntity<ConfirmOrderBean>?) -> Unit) {

        val params = HashMap<String, String>()
        params.put("userId", MyApplication.loginUserId)
        params.put("type", type)
        params.put("objectId", objectId)
        params.put("deliveryWayId", deliveryWayId)
        params.put("shippingAddressId", shippingAddressId)
        params.put("count", count)
        params.put("remark", remark)
        params.put("requestCheck", EncryptUtils.encryptMD5ToString(MyApplication.loginUserId + objectId, MyApplication.salt).toLowerCase())
        OkGo.post<NetEntity<ConfirmOrderBean>>(Urls.GenerateOrder)
                .params(params)
                .tag(this)
                .execute(object : DialogCallback<NetEntity<ConfirmOrderBean>>(context) {
                    override fun onSuccess(response: Response<NetEntity<ConfirmOrderBean>>) {
                        if (response.body().getCode() == Constants.NetCode.SUCCESS) {
                            listener.invoke(response.body())
                        } else {
                            context.toast(response.body().message)
                            listener.invoke(null)
                        }
                    }

                    override fun onError(response: Response<NetEntity<ConfirmOrderBean>>) {
                        super.onError(response)
                        listener.invoke(null)
                    }

                })
    }
}
