package com.aphrodite.insurance.component;

import com.aphrodite.common.constants.ErrCodeEnum;
import com.aphrodite.common.exception.AphroditeException;
import com.aphrodite.common.utils.AphUtils;
import com.aphrodite.common.web.intf.InitInterface;
import com.aphrodite.insurance.common.datasource.DataSourceContextHolder;
import com.aphrodite.insurance.common.datasource.DataSourceEnum;
import com.aphrodite.insurance.dao.PlanCorrelationTblMapper;
import com.aphrodite.insurance.entity.PlanCorrelationTblEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 险种基数相关性管理器
 * <p>用于在系统启动时加载 plan_correlation_tbl 表数据到内存缓存中，并提供查询接口。
 */
@Component
public class PlanCorrelationManager implements InitInterface {

    /*
     * 险种基数相关性缓存
     * 外层 Key：planCode (险种代码)
     * 内层 Key：tableName (表名)
     * Value：correlation (相关性)
     */
    private final Map<String, Map<String, List<String>>> planCorrelationMap = new ConcurrentHashMap<>();

    @Autowired
    private PlanCorrelationTblMapper planCorrelationTblMapper;

    @Override
    public void init() {
        /* 切换数据源至 MySQL */
        DataSourceContextHolder.setDataSource(DataSourceEnum.MySQL.getDb());

        /* 查询所有相关性配置 */
        List<PlanCorrelationTblEntity> entities = planCorrelationTblMapper.selectList(null);

        /* 清理并重新填充缓存 */
        planCorrelationMap.clear();
        if (entities != null) {
            for (PlanCorrelationTblEntity entity : entities) {
                planCorrelationMap.computeIfAbsent(entity.getPlanCode(), k -> new HashMap<>())
                        .put(entity.getTableName(), new ArrayList<>(Arrays.asList(entity.getCorrelation().split(","))));
            }
        }
    }

    /**
     * 获取险种与指定表的相关性配置
     *
     * @param planCode  险种代码
     * @param tableName 表名
     * @return 相关性配置字符串，若不存在则报错
     */
    public List<String> getCorrelation(String planCode, String tableName) {
        Map<String, List<String>> tableMap = planCorrelationMap.get(planCode);
        if (AphUtils.isEmpty(tableMap) || tableMap.get(tableName) == null) {
            throw new AphroditeException(
                    ErrCodeEnum.PLAN_CORRELATION_ERR,
                    String.format("【%s】险种【%s】表相关性未配置", planCode, tableName)
            );
        }
        return tableMap.get(tableName);
    }
}

