package app.isparks.core.dao.dialect;

public interface DMLAction {

    /**
     * 执行更新语句
     * @param sql
     */
    void update(String sql);

}
