package rugal.sample.core.vo;

import java.io.Serializable;

/**
 * 公共请求类
 */
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 1065143904924630722L;
    /**
     * 页数
     */
    public int pageNo;

    /**
     * 每页显示的条数
     */
    public int pageSize = 15;

    private String from;

    private String operator;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
