package app.isparks.core.pojo.page;

/**
 * 分页信息
 *
 * @author chenghd
 * @date 2020/8/12
 */
public class PageInfo {

    public PageInfo() {

    }

    public PageInfo(int page, int size) {
        this.page = page;
        this.size = size;
    }

    /**
     * 每页大小
     */
    private int size;
    /**
     * 页数
     */
    private int page;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
