package com.aphrodite.insurance.common.handler;

import com.ethan.step.utils.OraDecimal;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(OraDecimal.class)
@MappedJdbcTypes(JdbcType.DECIMAL)
public class OraDecimalTypeHandler extends BaseTypeHandler<OraDecimal> {

    /**
     * Java -> Database
     * 将 OraDecimal 设置到 PreparedStatement 中
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OraDecimal parameter, JdbcType jdbcType) throws SQLException {
        // 取出内部的 BigDecimal 值传给数据库
        ps.setBigDecimal(i, parameter.getDecimal());
    }

    /**
     * Database -> Java (根据列名)
     * 从 ResultSet 获取数据并转为 OraDecimal
     */
    @Override
    public OraDecimal getNullableResult(ResultSet rs, String columnName) throws SQLException {
        BigDecimal result = rs.getBigDecimal(columnName);
        return result == null ? null : new OraDecimal(result);
    }

    /**
     * Database -> Java (根据下标)
     */
    @Override
    public OraDecimal getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        BigDecimal result = rs.getBigDecimal(columnIndex);
        return result == null ? null : new OraDecimal(result);
    }

    /**
     * Database -> Java (存储过程调用)
     */
    @Override
    public OraDecimal getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        BigDecimal result = cs.getBigDecimal(columnIndex);
        return result == null ? null : new OraDecimal(result);
    }
}
