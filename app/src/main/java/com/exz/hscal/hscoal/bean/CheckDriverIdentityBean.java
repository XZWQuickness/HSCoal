package com.exz.hscal.hscoal.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pc on 2017/12/29.
 */

public class CheckDriverIdentityBean  {

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
         * userName : {"value":"联系人姓名","check":"1"}
         * mobile : {"value":"手机","check":"1"}
         * driverLicense : {"value":"驾照图片","check":"1"}
         * vehicleLicense : {"value":"行驶证照片","check":"2"}
         * reason : 拒绝原因
         */

        @SerializedName("userName")
        private UserNameBean userName;
        @SerializedName("mobile")
        private MobileBean mobile;
        @SerializedName("driverLicense")
        private DriverLicenseBean driverLicense;
        @SerializedName("vehicleLicense")
        private VehicleLicenseBean vehicleLicense;
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

        public DriverLicenseBean getDriverLicense() {
            return driverLicense;
        }

        public void setDriverLicense(DriverLicenseBean driverLicense) {
            this.driverLicense = driverLicense;
        }

        public VehicleLicenseBean getVehicleLicense() {
            return vehicleLicense;
        }

        public void setVehicleLicense(VehicleLicenseBean vehicleLicense) {
            this.vehicleLicense = vehicleLicense;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public static class UserNameBean {
            /**
             * value : 联系人姓名
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

        public static class MobileBean {
            /**
             * value : 手机
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

        public static class DriverLicenseBean {
            /**
             * value : 驾照图片
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

        public static class VehicleLicenseBean {
            /**
             * value : 行驶证照片
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
