package com.exz.carprofitmuch.config

/**
 * Created by 史忠文
 * on 2017/10/17.
 */
object Urls {
    var APP_ID = ""
    var url = "http://hs.xzsem.cn/"

    /**
     * 用户协议
     */
    val Information = url + "APP/BasicData/Information.aspx?i=1"


    /**
     * 关于我们
     */
    val Information2 = url + "APP/BasicData/Information.aspx?i=2"

    /**
     * 使用帮助
     */
    val help = url + "APP/BasicData/help.aspx"

    /**
     * 新闻资讯
     */
    val NewsList = url + "APP/News/List.aspx"





    /**
     * 获取验证码
     */
    val VerifyCode = url + "App/Account/ObtainCode.aspx"
    /**
     * 注册
     */
    val Register = url + "App/Account/Register.aspx"
    /**
     * 登录
     */
    val Login = url + "App/Account/Login.aspx"
    /**
     * 忘记密码
     */
    val ForgetPwd = url + "App/Account/ForgetPassword.aspx"
    /**
     * 修改密码
     */
    val EditPwd = url + "App/Account/ModifyPassword.aspx"

    /**
     * 首页banner图
     */
    val HomeBanner = url + "App/Home/Banner.aspx"

    /**
     * 首页_大宗热点(1条)
     */
    val TopNews = url + "App/Home/TopNews.aspx"

    /**
     * 首页_成交量/成交金额
     */
    val Turnover = url + "App/Home/Turnover.aspx"

    /**
     * 首页_热门煤炭货源（10条）
     */
    val HotCoal = url + "App/Home/HotCoal.aspx"

    /**
     * 首页_热门有色金属货源（10条）
     */
    val HotSteel = url + "App/Home/HotSteel.aspx"

    /**
     * 获取用户信息
     */
    val getUserInfo = url + "App/Account/GetUserInfo.aspx"


    /**
     * 获取用户信息
     */
    val getUserInfoTA = url + "App/Account/Buyer/GetUserInfo.aspx"



    /*
       * 设置-修改个人信息
       */
    var UpdateUserInfo = url + "App/Account/ModifyUserInfo.aspx"

    /*
   * 设置-收货地址列表
   */
    var AddressList = url + "App/Account/ShippingAddress.aspx"


    /*
     * 设置-默认收货地址
     */
    var AddressDefault = url + "App/Account/SetDefault.aspx"


    /*
     * 删除地址
     */
    var DeleteAddress = url + "App/Account/DeleteShippingAddress.aspx"


    /*
     * 添加/编辑收货地址（当编辑的是"默认地址"的收货地址时 / 新增第一条收货地址时,页面不存在state选项）
     */
    var SubmitShippingAddress = url + "App/Account/SubmitShippingAddress.aspx"

    /*
     * 收货地址详情
     */
    var ShippingAddressInfo = url + "App/Account/ShippingAddressInfo.aspx"

    /*
     * 提交供应商审核资料
     */
    var SubmitBusinessIdentity = url + "APP/Account/Apply/SubmitBusinessIdentity.aspx"

    /*
     * 编辑供应商审核资料
     */
    var EditBusinessIdentity = url + "APP/Account/Apply/EditBusinessIdentity.aspx"

    /*
     * 供应商审核结果接口
     */
    var CheckBusinessIdentity = url + "APP/Account/Apply/CheckBusinessIdentity.aspx"

    /*
   * 提交司机审核资料
   */
    var SubmitDriverIdentity = url + "APP/Account/Apply/SubmitDriverIdentity.aspx"

    /*
     * 编辑司机审核资料
     */
    var EditDriverIdentity = url + "APP/Account/Apply/EditDriverIdentity.aspx"

    /*
     * 司机审核结果接口
     */
    var CheckDriverIdentity = url + "APP/Account/Apply/CheckDriverIdentity.aspx"


    /*
     * 交货方式
     */
    var DeliveryWay = url + "App/BasicData/DeliveryWay.aspx"

    /*
     * 付款方式
     */
    var PaymentMode = url + "App/BasicData/PaymentMode.aspx"


    /*
     * 发布煤炭（供应商权限）
     */
    var ReleaseCoal = url + "App/Supply/ReleaseCoal.aspx"

    /*
     * 编辑煤炭，不允许修改煤种（供应商权限,未通过时，可以调用接口）
     */
    var EditCoal = url + "App/Supply/EditCoal.aspx"


    /*
     * 发布有色金属（供应商权限）
     */
    var ReleaseSteel = url + "App/Supply/ReleaseSteel.aspx"

    /*
     * 编辑有色金属（供应商权限,未通过时，可以调用接口）
     */
    var EditSteel = url + "App/Supply/EditSteel.aspx"


    /*
     * 发布煤炭询价
     */
    var ReleaseCoalEnquiry = url + "App/Enquiry/ReleaseCoal.aspx"


    /*
     * 编辑煤炭，不允许修改煤种（未通过时，可以调用接口）
     */
    var EditCoalEnquiry = url + "App/Enquiry/EditCoal.aspx"

    /*
     * 编辑有色金属（未通过时，可以调用接口）
     */
    var EditSteelEnquiry = url + "App/Enquiry/EditSteel.aspx"

    /*
     * 发布有色金属
     */
    var ReleaseSteelEnquiry = url + "App/Enquiry/ReleaseSteel.aspx"


    /*
     * 煤炭货源
     */
    var CoalList = url + "App/Supply/Coal.aspx"

    /*
     * 煤炭货源详情
     */
    var GetCoalInfo = url + "App/Supply/GetCoalInfo.aspx"

    /*
     * 有色金属分类
     */
    var SteelClass = url + "App/BasicData/SteelClass.aspx"

    /*
     * 发布钢材
     */
    var SteelList = url + "App/Supply/Steel.aspx"


    /*
     * 有色金属货源详情
     */
    var GetSteelInfo = url + "App/Supply/GetSteelInfo.aspx"

    /*
     * 获取确认订单信息
     */
    var ConfirmOrder = url + "App/Supply/ConfirmOrder.aspx"

    /*
     * 确认订单_配送地址
     */
    var ConfirmOrder_ShippingAddress = url + "App/Supply/ConfirmOrder_ShippingAddress.aspx"


    /*
     * 生成订单
     */
    var GenerateOrder = url + "App/Supply/GenerateOrder.aspx"

    /*
     * 待发货订单（司机权限）
     */
    var WaitDeliveryOrders = url + "App/WaitDelivery/Orders.aspx"

    /*
     * 订单详情（司机权限）
     */
    var WaitDeliveryOrderInfo = url + "App/WaitDelivery/OrderInfo.aspx"

    /*
     *  煤炭询价列表
     */
    var coalEnquiry = url + "App/Enquiry/Coal.aspx"

    /*
     *  有色金属询价列表
     */
    var steelEnquiry = url + "App/Enquiry/Steel.aspx"

    /*
     *  煤炭询价详情
     */
    var getCoalInfoEnquiry = url + "App/Enquiry/GetCoalInfo.aspx"
    /*
     *  有色金属询价详情
     */
    var GetSteelInfoEnquiry = url + "App/Enquiry/GetSteelInfo.aspx"

    /*
     *  立即报价（供应商权限）
     */
    var SubmitQuote = url + "App/Enquiry/SubmitQuote.aspx"


    /*
     *  商品管理 煤炭货源
     */
    var CoalSeller = url + "App/Account/Seller/Coal.aspx"


    /*
     *  商品管理 有色金属货源
     */
    var SteelSeller = url + "App/Account/Seller/Steel.aspx"


    /*
     *  删除货源信息（未通过时，可以调用接口）
     */
    var DeleteGoods = url + "App/Supply/Delete.aspx"


    /*
     *  煤炭订单列表
     */
    var CoalOrder = url + "App/Account/Buyer/CoalOrder.aspx"


    /*
     *  有色金属订单列表
     */
    var SteelOrder = url + "App/Account/Buyer/SteelOrder.aspx"

    /*
     *  煤炭订单详情（与卖家共用）
     */
    var getCoalOrderInfo = url + "App/Account/Buyer/GetCoalOrderInfo.aspx"

    /*
     *  有色金属订单详情（与卖家共用）
     */
    var getSteelOrderInfo = url + "App/Account/Buyer/GetSteelOrderInfo.aspx"


    /*
     *  煤炭订单列表
     */
    var CoalOrderSeller = url + "App/Account/Seller/CoalOrder.aspx"

    /*
     *  有色金属订单列表
     */
    var SteelOrderSeller = url + "App/Account/Seller/SteelOrder.aspx"


    /*
     *  煤炭报价列表
     */
    var CoalQuote = url + "App/Account/Quote/Coal.aspx"

    /*
     *  有色金属报价列表
     */
    var SteelQuote = url + "App/Account/Quote/Steel.aspx"

    /*
     *  煤炭报价详情
     */
    var getCoalInfoQuote = url + "App/Account/Quote/GetCoalInfo.aspx"


    /*
     *  确认付款（未付款的订单，可以调用此接口）
     */
    var ConfirmPay = url + "App/Account/Seller/ConfirmPay.aspx"

    /*
     *  删除询价信息（未通过时，可以调用接口）
     */
    var DeleteEnquiry = url + "App/Enquiry/Delete.aspx"


    /*
     *  有色金属报价详情
     */
    var GetSteelInfoQuote = url + "App/Account/Quote/GetSteelInfo.aspx"


    /*
        * 询盘管理  煤炭报价列表
        */
    var CoalEnquiryManage= url + "App/Account/Enquiry/Coal.aspx"

    /*
     *  询盘管理 有色金属报价列表
     */
    var SteelEnquiryManage = url + "App/Account/Enquiry/Steel.aspx"

    /*
     *  询盘管理 煤炭询价详情
     */
    var GetCoalInfoyEnquiryManage = url + "App/Account/Enquiry/GetCoalInfo.aspx"

    /*
     *  询盘管理 有色金属询价详情
     */
    var GetSteelInfoManage = url + "App/Account/Enquiry/GetSteelInfo.aspx"

    /*
     *  报价列表
     */
    var QuoteEnquiry = url + "App/Account/Enquiry/Quote.aspx"

    /*
     *  接收/拒绝报价（待确认时，调用此接口）
     */
    var ConfirmEnquiry = url + "App/Account/Enquiry/Confirm.aspx"

    /*
     * 退出
     */
    var ExitAccount = url + "App/Account/Exit.aspx"

    /*
     * 更新接口
     */
    var UpgradeApk = url + "App/Upgrade.aspx"


}