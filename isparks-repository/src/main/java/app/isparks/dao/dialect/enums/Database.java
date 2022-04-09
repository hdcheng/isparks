package app.isparks.dao.dialect.enums;

import app.isparks.core.config.DBConfig;
import app.isparks.core.dao.dialect.DBAction;
import app.isparks.core.dao.dialect.DialectInfo;
import app.isparks.core.dao.dialect.IDatabaseEnum;
import app.isparks.core.exception.InvalidValueException;
import app.isparks.core.exception.RepositoryException;
import app.isparks.core.util.StringUtils;
import app.isparks.core.dao.dialect.DefaultDialectInfo;
import app.isparks.dao.dialect.H2DatabaseAction;
import app.isparks.dao.dialect.PostgresqlAction;

public enum Database implements IDatabaseEnum {

    H2{
        @Override
        public Class<? extends DBAction> getDialectType() {
            return H2DatabaseAction.class;
        }

        @Override
        public DBAction getDBAction(){
            DBAction action = Database.getDialectInstance(this);

            if (DBConfig.isComplete()){
                throw new RepositoryException("数据库信息不全");
            }

            DialectInfo info = new DefaultDialectInfo(driverClass(), DBConfig.getIp(), DBConfig.getPort(), DBConfig.getDBName(), DBConfig.getUserName(),null);

            action.prepare(info);

            return action;
        }

        @Override
        public String driverClass() {
            return "org.h2.Driver";
        }

        @Override
        public DBAction resolveDialect(DialectInfo info) {
            DBAction action = Database.getDialectInstance(this);
            if(info == null){
                info = new DefaultDialectInfo(driverClass(),"127.0.0.1","8082","isparks","sa",null);
            }
            action.prepare(info);
            return action;
        }
    },
    POSTGRESQL{
        @Override
        public Class<? extends DBAction> getDialectType() {
            return PostgresqlAction.class;
        }

        @Override
        public DBAction getDBAction() {
            DBAction action = Database.getDialectInstance(this);

            if (!DBConfig.isComplete()){
                throw new RepositoryException("数据库信息不全");
            }

            DialectInfo info = new DefaultDialectInfo(driverClass(), DBConfig.getIp(), DBConfig.getPort(), DBConfig.getDBName(), DBConfig.getUserName(), DBConfig.getPassword());

            action.prepare(info);

            return action;
        }

        @Override
        public String driverClass() {
            return "org.postgresql.Driver";
        }

        @Override
        public DBAction resolveDialect(DialectInfo info) {
            DBAction action = Database.getDialectInstance(this);
            if(info == null){
                throw new InvalidValueException("resolve info must not be null.");
            }

            info.setDriver(driverClass());

            if (StringUtils.isEmpty(info.getIp())){
                info.setIp("127.0.0.1");
            }
            if (StringUtils.isEmpty(info.getPort())){
                info.setPort("5432");
            }
            if (StringUtils.isEmpty(info.getUserName())){
                info.setUserName("postgres");
            }
            if (StringUtils.isEmpty(info.getPassword())){
                throw new InvalidValueException("database password must not be empty.");
            }
            if(StringUtils.isEmpty(info.getDbName())){
                info.setDbName("isparks");
            }

            action.prepare(info);
            return action;
        }
    };

    private static DBAction getDialectInstance(IDatabaseEnum database){
        try {
            return database.getDialectType().newInstance();
        }catch (InstantiationException | IllegalAccessException e){
            throw new RepositoryException(e);
        }
    }
}
