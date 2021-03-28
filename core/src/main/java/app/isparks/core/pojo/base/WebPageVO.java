package app.isparks.core.pojo.base;

import app.isparks.core.pojo.page.PageData;

import java.util.List;
import java.util.function.Consumer;

/**
 * Web页面显示数据
 *
 * @author： chenghd
 * @date： 2021/3/27
 */
public class WebPageVO<VO extends BaseDTO> extends BaseProperty {

    /**
     * 当前页(1-n)
     */
    private int page;

    /**
     * 每页大小(0-n)
     */
    private int size;

    /**
     * 数据
     */
    private List<VO> data;

    /**
     * 共多少页
     */
    private int totalPage;

    /**
     * 共多少数据
     */
    private long totalData;

    /**
     * 设置页面属性
     */
    public void setPageData(PageData<VO> pd){
        this.data = pd.getData();
        this.page = pd.getPage();
        this.size = pd.getSize();
        this.totalPage = pd.getTotalPage();
        this.totalData = pd.getTotalData();
    }

    /**
     * 更新date数据
     * @param func
     */
    public void update(Consumer<VO> func){
        getData().stream().forEach(func);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<VO> getData() {
        return data;
    }

    public void setData(List<VO> data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalData() {
        return totalData;
    }

    public void setTotalData(long totalData) {
        this.totalData = totalData;
    }


}
