package com.exz.carprofitmuch.config

/**
 * Created by 史忠文
 * on 2017/10/17.
 */
object Urls{
    var APP_ID = ""
    var url = "http://jingu.xzsem.cn/"

    /**
     * 获取验证码
     */
    val VerifyCode=url+"App/Account/VerifyCode.aspx"
    /**
     * 注册
     */
    val Register=url+"App/Account/Register.aspx"
    /**
     * 登录
     */
    val Login=url+"App/Account/Login.aspx"
    /**
     * 忘记密码
     */
    val ForgetPwd=url+"App/Account/ForgetPwd.aspx"
    /**
     * 修改密码
     */
    val EditPwd=url+"App/Account/EditPwd.aspx"
    /**
     * 首页banner图
     */
    val HomeBanner=url+"App/Home/Banner.aspx"
    /**
     * 首页banner图
     */
    val AdsList=url+"App/Home/AdsList.aspx"
    /*
       * 设置-修改个人信息
       */
    var UpdateUserInfo = url + "App/UserCenter/Set/UpdateUserInfo.aspx"

    /*
   * 设置-收货地址列表
   */
    var AddressList = url + "App/Address/List.aspx"


    /*
     * 设置-默认收货地址
     */
    var AddressDefault = url + "App/Address/Default.aspx"


    /*
     * 设置-删除收货地址
     */
    var AddressDelete = url + "App/Address/Delete.aspx"


    /*
     * 新增收货地址（当用户添加地址时，后台判断该用户是否有其他地址，若没有，将该地址设为默认地址）
     */
    var AddAddress = url + "App/Address/Add.aspx"

    /*
     * 修改收货地址
     */
    var ModifyAddAddress = url + "App/Address/Modify.aspx"










}