package app.isparks.core.dao.dialect;

public interface DDLAction {

    /**
     * 执行创建语句
     * @param sql
     */
    void createTables(String sql);


}
