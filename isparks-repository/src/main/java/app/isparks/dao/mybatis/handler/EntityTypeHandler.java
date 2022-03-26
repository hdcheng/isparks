package app.isparks.dao.mybatis.handler;

import app.isparks.core.pojo.enums.EntityType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityTypeHandler extends BaseTypeHandler<EntityType> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EntityType parameter, JdbcType jdbcType) throws SQLException {
    }

    @Override
    public EntityType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if(!rs.wasNull()){
            String i = rs.getString(columnName);
            for(EntityType type : EntityType.values()){
                if(type.name().equals(i)){
                    return type;
                }
            }
        }
        return null;
    }

    @Override
    public EntityType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if(!rs.wasNull()){
            String i = rs.getString(columnIndex);
            for(EntityType type : EntityType.values()){
                if(type.name().equals(i)){
                    return type;
                }
            }
        }
        return null;
    }

    @Override
    public EntityType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
