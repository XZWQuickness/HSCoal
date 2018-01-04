package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017/12/29.
 */

public class CheckBusinessIdentityBean {


    /**
     * userId : 3
     * checkState : 2
     * checkResult : {"userName":{"value":"姓名","check":"2"},"mobile":{"value":"13236027766","check":"1"},"IDNumber":{"value":"542322198305086983","check":"2"},"shopName":{"value":"小店","check":"2"},"shopLogo":{"value":"http://hs.xzsem.cn/applyImg/201712290226219241.jpg","check":"2"},"companyName":{"value":"公司","check":"2"},"companyTel":{"value":"5585","check":"2"},"companyEmail":{"value":"55@@qq.com","check":"2"},"companyAddress":{"value":"公司地瓜","check":"2"},"businessLicence":{"value":"http://hs.xzsem.cn/applyImg/201712290226219402.jpg","check":"2"},"reason":"不符合规范"}
     */

    @SerializedName("userId")
    private String userId;
    @SerializedName("checkState")
    private String checkState;
    @SerializedName("checkResult")
    private CheckResultBean checkResult;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public CheckResultBean getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(CheckResultBean checkResult) {
        this.checkResult = checkResult;
    }

    public static class CheckResultBean {
        /**
         * userName : {"value":"姓名","check":"2"}
         * mobile : {"value":"13236027766","check":"1"}
         * IDNumber : {"value":"542322198305086983","check":"2"}
         * shopName : {"value":"小店","check":"2"}
         * shopLogo : {"value":"http://hs.xzsem.cn/applyImg/201712290226219241.jpg","check":"2"}
         * companyName : {"value":"公司","check":"2"}
         * companyTel : {"value":"5585","check":"2"}
         * companyEmail : {"value":"55@@qq.com","check":"2"}
         * companyAddress : {"value":"公司地瓜","check":"2"}
         * businessLicence : {"value":"http://hs.xzsem.cn/applyImg/201712290226219402.jpg","check":"2"}
         * reason : 不符合规范
         */

        @SerializedName("userName")
        private UserNameBean userName;
        @SerializedName("mobile")
        private MobileBean mobile;
        @SerializedName("IDNumber")
        private IDNumberBean IDNumber;
        @SerializedName("shopName")
        private ShopNameBean shopName;
        @SerializedName("shopLogo")
        private ShopLogoBean shopLogo;
        @SerializedName("companyName")
        private CompanyNameBean companyName;
        @SerializedName("companyTel")
        private CompanyTelBean companyTel;
        @SerializedName("companyEmail")
        private CompanyEmailBean companyEmail;
        @SerializedName("companyAddress")
        private CompanyAddressBean companyAddress;
        @SerializedName("businessLicence")
        private BusinessLicenceBean businessLicence;
        @SerializedName("reason")
        private String reason;

        public UserNameBean getUserName() {
            return userName;
        }

        public void setUserName(UserNameBean userName) {
            this.userName = userName;
        }

        public MobileBean getMobile() {
            return mobile;
        }

        public void setMobile(MobileBean mobile) {
            this.mobile = mobile;
        }

        public IDNumberBean getIDNumber() {
            return IDNumber;
        }

        public void setIDNumber(IDNumberBean IDNumber) {
            this.IDNumber = IDNumber;
        }

        public ShopNameBean getShopName() {
            return shopName;
        }

        public void setShopName(ShopNameBean shopName) {
            this.shopName = shopName;
        }

        public ShopLogoBean getShopLogo() {
            return shopLogo;
        }

        public void setShopLogo(ShopLogoBean shopLogo) {
            this.shopLogo = shopLogo;
        }

        public CompanyNameBean getCompanyName() {
            return companyName;
        }

        public void setCompanyName(CompanyNameBean companyName) {
            this.companyName = companyName;
        }

        public CompanyTelBean getCompanyTel() {
            return companyTel;
        }

        public void setCompanyTel(CompanyTelBean companyTel) {
            this.companyTel = companyTel;
        }

        public CompanyEmailBean getCompanyEmail() {
            return companyEmail;
        }

        public void setCompanyEmail(CompanyEmailBean companyEmail) {
            this.companyEmail = companyEmail;
        }

        public CompanyAddressBean getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(CompanyAddressBean companyAddress) {
            this.companyAddress = companyAddress;
        }

        public BusinessLicenceBean getBusinessLicence() {
            return businessLicence;
        }

        public void setBusinessLicence(BusinessLicenceBean businessLicence) {
            this.businessLicence = businessLicence;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public static class UserNameBean {
            /**
             * value : 姓名
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class MobileBean {
            /**
             * value : 13236027766
             * check : 1
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class IDNumberBean {
            /**
             * value : 542322198305086983
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class ShopNameBean {
            /**
             * value : 小店
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class ShopLogoBean {
            /**
             * value : http://hs.xzsem.cn/applyImg/201712290226219241.jpg
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class CompanyNameBean {
            /**
             * value : 公司
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class CompanyTelBean {
            /**
             * value : 5585
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class CompanyEmailBean {
            /**
             * value : 55@@qq.com
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class CompanyAddressBean {
            /**
             * value : 公司地瓜
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }

        public static class BusinessLicenceBean {
            /**
             * value : http://hs.xzsem.cn/applyImg/201712290226219402.jpg
             * check : 2
             */

            @SerializedName("value")
            private String value;
            @SerializedName("check")
            private String check;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCheck() {
                return check;
            }

            public void setCheck(String check) {
                this.check = check;
            }
        }
    }
}
