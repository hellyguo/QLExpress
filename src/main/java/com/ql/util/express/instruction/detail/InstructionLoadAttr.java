package com.ql.util.express.instruction.detail;

import java.util.List;

import com.ql.util.express.InstructionSet;
import com.ql.util.express.RunEnvironment;
import com.ql.util.express.instruction.opdata.OperateDataAttr;

public class InstructionLoadAttr extends Instruction {
    private final String attrName;

    public InstructionLoadAttr(String name) {
        this.attrName = name;
    }

    public String getAttrName() {
        return this.attrName;
    }

    @Override
    public void execute(RunEnvironment environment, List<String> errorList) throws Exception {
        Object o = environment.getContext().getSymbol(this.attrName);
        //是函数，则执行
        if (o instanceof InstructionSet) {
            if (environment.isTrace() && log.isDebugEnabled()) {
                log.debug("指令转换： LoadAttr -- >CallMacro ");
            }
            InstructionCallMacro macro = new InstructionCallMacro(this.attrName);
            macro.setLog(log);
            macro.execute(environment, errorList);
            //注意，此处不能在增加指令，因为在InstructionCallMacro已经调用 environment.programPointAddOne();
        } else {
            if (environment.isTrace() && log.isDebugEnabled()) {
                log.debug(this + ":" + ((OperateDataAttr)o).getObject(environment.getContext()));
            }
            environment.push((OperateDataAttr)o);
            environment.programPointAddOne();
        }
    }

    @Override
    public String toString() {
        return "LoadAttr:" + this.attrName;
    }
}
