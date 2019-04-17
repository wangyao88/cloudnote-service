package com.sxkl.project.cloudnote.etl.service;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.service.extract.CursorExtract;
import com.sxkl.project.cloudnote.etl.service.extract.Extract;
import com.sxkl.project.cloudnote.etl.service.load.CursorLoad;
import com.sxkl.project.cloudnote.etl.service.load.Load;
import com.sxkl.project.cloudnote.etl.service.transform.CursorTransform;
import com.sxkl.project.cloudnote.etl.service.transform.Transform;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EtlFactory {

    private static final String ENTITY_PACKAGE_PATH = "com.sxkl.project.cloudnote.etl.entity";

    public static EtlGroup getEtlGroup() {
        EtlGroup etlGroup = EtlGroup.init();
        etlGroup.batchAddEtl(batchGetBaseEtl(etlGroup));
        return etlGroup;
    }

    private static List<BaseEtl> batchGetBaseEtl(EtlGroup etlGroup) {
        List<String> entityNames = getEntityNames();
        return entityNames.stream().map(name -> {
            EtlInfo etlInfo = EtlInfo.init(etlGroup, name);
            if(Constant.CURSOR_SELECT_ENTITY_NAME.contains(name)) {
                CursorLoad cursorLoad = new CursorLoad(etlInfo);
                CursorTransform cursorTransform = new CursorTransform(cursorLoad, etlInfo);
                CursorExtract cursorExtract = new CursorExtract(cursorTransform, etlInfo);
                return new CursorEtl(cursorExtract, cursorTransform, cursorLoad, etlInfo);
            }
            return new BaseEtl(new Extract(etlInfo), new Transform(etlInfo), new Load(etlInfo), etlInfo);
        }).collect(Collectors.toList());
    }

    private static List<String> getEntityNames() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage(ENTITY_PACKAGE_PATH))
            .setScanners(new SubTypesScanner());
        Reflections reflections = new Reflections(configurationBuilder);
        Set<Class<? extends BaseEntity>> types = reflections.getSubTypesOf(BaseEntity.class);
        return types.stream().map(type -> type.getSimpleName()).collect(Collectors.toList());
    }
}
