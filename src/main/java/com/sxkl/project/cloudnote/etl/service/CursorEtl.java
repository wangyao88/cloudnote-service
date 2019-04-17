package com.sxkl.project.cloudnote.etl.service;

import com.sxkl.project.cloudnote.etl.service.extract.Extract;
import com.sxkl.project.cloudnote.etl.service.load.Load;
import com.sxkl.project.cloudnote.etl.service.transform.Transform;

public class CursorEtl extends BaseEtl {

    public CursorEtl(Extract extract, Transform transform, Load load, EtlInfo etlInfo) {
        super(extract, transform, load, etlInfo);
    }

    @Override
    public EtlInfo call() throws Exception {
        extract.cursorExtract();
        return etlInfo;
    }
}
