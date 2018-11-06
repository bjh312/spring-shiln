package com.shiln.user.config;

/**
 * Created by baojunhu on 2017/3/28.
 */
public class BaseItem {
    public static final String ACTION_COLOMN_NAME = "action";
    public static final String ACTIONTIME_COLOMN_NAME = "actionTime";
    public static final String CREATETIME_COLOMN_NAME = "createTime";
    protected Integer action;
    protected Long actionTime;
    protected Long createTime;

    public BaseItem() {
    }

    public Integer getAction() {
        return this.action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Long getActionTime() {
        return this.actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public static enum Action {
        INSERT(Integer.valueOf(0)),
        UPDATE(Integer.valueOf(1)),
        DELETE(Integer.valueOf(2));

        private Integer value;

        private Action(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}