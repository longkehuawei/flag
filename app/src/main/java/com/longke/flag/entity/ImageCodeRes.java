package com.longke.flag.entity;

/**
 * Created by longke on 2017/12/15.
 */

public class ImageCodeRes {

    /**
     * FormateStr : yyyy-MM-dd
     * ResultCode : 200
     * Success : true
     * Message :
     * Data : {"ValidCode":"33960","SessionId":"02xfe33ynq2ibqdzulmizrgl"}
     * TotalCount : 0
     * ContentEncoding : null
     * ContentType : null
     * JsonRequestBehavior : 0
     * MaxJsonLength : null
     * RecursionLimit : null
     */

    private String FormateStr;
    private int ResultCode;
    private boolean Success;
    private String Message;
    private DataBean Data;
    private int TotalCount;
    private Object ContentEncoding;
    private Object ContentType;
    private int JsonRequestBehavior;
    private Object MaxJsonLength;
    private Object RecursionLimit;

    public String getFormateStr() {
        return FormateStr;
    }

    public void setFormateStr(String FormateStr) {
        this.FormateStr = FormateStr;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public Object getContentEncoding() {
        return ContentEncoding;
    }

    public void setContentEncoding(Object ContentEncoding) {
        this.ContentEncoding = ContentEncoding;
    }

    public Object getContentType() {
        return ContentType;
    }

    public void setContentType(Object ContentType) {
        this.ContentType = ContentType;
    }

    public int getJsonRequestBehavior() {
        return JsonRequestBehavior;
    }

    public void setJsonRequestBehavior(int JsonRequestBehavior) {
        this.JsonRequestBehavior = JsonRequestBehavior;
    }

    public Object getMaxJsonLength() {
        return MaxJsonLength;
    }

    public void setMaxJsonLength(Object MaxJsonLength) {
        this.MaxJsonLength = MaxJsonLength;
    }

    public Object getRecursionLimit() {
        return RecursionLimit;
    }

    public void setRecursionLimit(Object RecursionLimit) {
        this.RecursionLimit = RecursionLimit;
    }

    public static class DataBean {
        /**
         * ValidCode : 33960
         * SessionId : 02xfe33ynq2ibqdzulmizrgl
         */

        private String ValidCode;
        private String SessionId;

        public String getValidCode() {
            return ValidCode;
        }

        public void setValidCode(String ValidCode) {
            this.ValidCode = ValidCode;
        }

        public String getSessionId() {
            return SessionId;
        }

        public void setSessionId(String SessionId) {
            this.SessionId = SessionId;
        }
    }
}
