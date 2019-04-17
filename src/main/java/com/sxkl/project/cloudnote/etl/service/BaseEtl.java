package com.sxkl.project.cloudnote.etl.service;

import com.sxkl.project.cloudnote.etl.service.extract.Extract;
import com.sxkl.project.cloudnote.etl.service.load.Load;
import com.sxkl.project.cloudnote.etl.service.transform.Transform;
import lombok.Data;

import java.util.concurrent.Callable;


@Data
public class BaseEtl implements Callable<EtlInfo> {

    protected Extract extract;
    protected Transform transform;
    protected Load load;
    protected EtlInfo etlInfo;

    public BaseEtl(Extract extract, Transform transform, Load load, EtlInfo etlInfo) {
        this.extract = extract;
        this.transform = transform;
        this.load = load;
        this.etlInfo = etlInfo;
    }

    @Override
    public EtlInfo call() throws Exception {
        return load.load(transform.transform(extract.extract()));
    }
}
