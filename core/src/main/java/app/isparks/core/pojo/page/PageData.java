package app.isparks.core.pojo.page;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author chenghd
 * @date 2020/8/12
 */
public class PageData<D> {

    public PageData() {

    }

    public PageData(int page, int size) {
        this.page = page;
        this.size = size;
    }


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
    private List<D> data;

    /**
     * 共多少页
     */
    private int totalPage;

    /**
     * 共多少数据
     */
    private long totalData;

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

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
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

    public PageData withData(List<D> data) {
        setData(data);
        return this;
    }

    public PageData withPageInfo(PageData pageData) {
        this.page = pageData.getPage();
        this.size = pageData.getSize();
        this.totalPage = pageData.getTotalPage();
        this.totalData = pageData.getTotalData();
        return this;
    }

    public PageData withPageInfo(PageInfo pageInfo) {
        this.page = pageInfo.getPage();
        this.size = pageInfo.getSize();
        return this;
    }

    public <N> PageData<N> getNew(List<N> data) {
        PageData<N> pageData = new PageData<>();
        pageData.setData(data);
        pageData.setPage(page);
        pageData.setSize(size);
        pageData.setTotalData(totalData);
        pageData.setTotalPage(totalPage);
        return pageData;
    }

    /**
     * 创建新的 PageData
     * @param convertFunc
     * @param <N>
     * @return
     */
    public <N> PageData<N> convertData(Function<D,N> convertFunc){
        PageData<N> pageData = new PageData<>();
        pageData.setPage(page);
        pageData.setSize(size);
        pageData.setTotalData(totalData);
        pageData.setTotalPage(totalPage);
        List<D> oldD = getData();
        List<N> newD = new ArrayList<>(oldD.size());

        oldD.forEach((d)->newD.add(convertFunc.apply(d)));

        pageData.setData(newD);
        return pageData;
    }

    /**
     * 更新date数据
     * @param func
     */
    public void update(Consumer<D> func){
        getData().stream().forEach(func);
    }

}
