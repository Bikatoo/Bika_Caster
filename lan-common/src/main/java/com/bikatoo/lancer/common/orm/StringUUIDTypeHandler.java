package com.bikatoo.lancer.common.orm;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class StringUUIDTypeHandler extends BaseTypeHandler<UUID> {

    public static String uuidToString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

    public static UUID stringToUUID(String uuidStr) {
        return uuidStr == null ? null : UUID.fromString(uuidStr);
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UUID uuid, JdbcType jdbcType)
            throws SQLException {
        preparedStatement.setString(i, uuidToString(uuid));
    }

    @Override
    public UUID getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return stringToUUID(resultSet.getString(s));
    }

    @Override
    public UUID getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return stringToUUID(resultSet.getString(i));
    }

    @Override
    public UUID getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return stringToUUID(callableStatement.getString(i));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, uuidToString(parameter));
    }

    @Override
    public UUID getResult(ResultSet rs, String columnName) throws SQLException {
        return stringToUUID(rs.getString(columnName));
    }

    @Override
    public UUID getResult(ResultSet rs, int columnIndex) throws SQLException {
        return stringToUUID(rs.getString(columnIndex));
    }

    @Override
    public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return stringToUUID(cs.getString(columnIndex));
    }

}
