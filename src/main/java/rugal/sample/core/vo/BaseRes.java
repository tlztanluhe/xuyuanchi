package rugal.sample.core.vo;

import java.io.Serializable;

/**
 * 响应公共类
 */
public class BaseRes implements Serializable {
    private static final long serialVersionUID = 4879712633020685907L;
    /**
     *
     */
    private int code;
    /**
     * 内容
     */
    private String content;


    public String getContent() {
        return content;
    }

    public BaseRes setContent(String content) {
        this.content = content;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseRes setCode(int code) {
        this.code = code;
        return this;
    }

    //分页有关信息
    private long totalRow;
    private int totalPage;


	public long getTotalRow() {
		return totalRow;
	}

	public BaseRes setTotalRow(long totalRow) {
		this.totalRow = totalRow;
		return this;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public BaseRes setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		return this;
	}
    
    


    

}
