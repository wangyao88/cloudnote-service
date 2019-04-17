package com.sxkl.project.cloudnote.etl.service.transform;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.service.EtlInfo;
import com.sxkl.project.cloudnote.etl.service.exception.LoadException;
import com.sxkl.project.cloudnote.etl.service.exception.MapperNullException;
import com.sxkl.project.cloudnote.etl.service.load.CursorLoad;

public class CursorTransform extends Transform {

    private CursorLoad cursorLoad;

    public CursorTransform(CursorLoad cursorLoad, EtlInfo etlInfo) {
        super(etlInfo);
        this.cursorLoad = cursorLoad;
    }

    public BaseEntity cursorTransform(BaseEntity baseEntity) throws LoadException, MapperNullException {
        cursorLoad.cursorLoad(baseEntity);
        return baseEntity;
    }

    public void delete() throws MapperNullException {
        cursorLoad.delete();
    }
}
