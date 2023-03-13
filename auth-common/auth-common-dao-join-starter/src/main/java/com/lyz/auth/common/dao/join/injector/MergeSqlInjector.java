package com.lyz.auth.common.dao.join.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.yulichang.base.mapper.MPJDeepMapper;
import com.github.yulichang.base.mapper.MPJJoinMapper;
import com.github.yulichang.base.mapper.MPJRelationMapper;
import com.github.yulichang.injector.MPJSqlInjector;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 9:30
 */
public class MergeSqlInjector extends DefaultSqlInjector {

    private final List<AbstractSqlInjector> defaultList;

    public MergeSqlInjector(List<AbstractSqlInjector> defaultList) {
        this.defaultList = defaultList;
    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        if (CollectionUtils.isEmpty(defaultList)) {
            return super.getMethodList(mapperClass, tableInfo);
        }
        List<AbstractMethod> result = new ArrayList<>();
        for (AbstractSqlInjector injector : defaultList) {
            if (injector instanceof MPJSqlInjector && (MPJDeepMapper.class.isAssignableFrom(mapperClass)
                    || MPJRelationMapper.class.isAssignableFrom(mapperClass)
                    || MPJJoinMapper.class.isAssignableFrom(mapperClass))) {
                result.addAll(injector.getMethodList(mapperClass, tableInfo));
                continue;
            }
            result.addAll(super.getMethodList(mapperClass, tableInfo));
        }
        return result;
    }
}
