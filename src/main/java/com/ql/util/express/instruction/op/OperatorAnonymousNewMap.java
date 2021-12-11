package com.ql.util.express.instruction.op;

import java.util.HashMap;
import java.util.Map;

import com.ql.util.express.ArraySwap;
import com.ql.util.express.InstructionSetContext;
import com.ql.util.express.OperateData;
import com.ql.util.express.instruction.OperateDataCacheManager;
import com.ql.util.express.instruction.opdata.OperateDataKeyValue;

public class OperatorAnonymousNewMap extends OperatorBase {
    public OperatorAnonymousNewMap(String aName) {
        this.name = aName;
    }

    public OperatorAnonymousNewMap(String aAliasName, String aName, String aErrorInfo) {
        this.name = aName;
        this.aliasName = aAliasName;
        this.errorInfo = aErrorInfo;
    }

    @Override
    public OperateData executeInner(InstructionSetContext context, ArraySwap list) throws Exception {
        Map<Object, Object> result = new HashMap<>();
        for (int i = 0; i < list.length; i++) {
            Object key = ((OperateDataKeyValue)list.get(i)).getKey().getObject(context);
            Object value = ((OperateDataKeyValue)list.get(i)).getValue().getObject(context);
            result.put(key, value);
        }
        return OperateDataCacheManager.fetchOperateData(result, HashMap.class);
    }
}
