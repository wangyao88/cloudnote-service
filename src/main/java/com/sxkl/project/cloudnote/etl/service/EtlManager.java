package com.sxkl.project.cloudnote.etl.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;


@Slf4j
@Service
public class EtlManager implements CommandLineRunner {

    private ExecutorService pool;

    @PostConstruct
    private void initPool() {
        int cpuCore = Runtime.getRuntime().availableProcessors();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("EtlManager-pool-%d").build();
        pool = new ThreadPoolExecutor(cpuCore,
                      cpuCore*2,
                         0L,
                                      TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>(20000),
                                      threadFactory,
                                      new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public String doEtlGroup() {
        EtlGroup etlGroup = EtlFactory.getEtlGroup();
        etlGroup.getEtls().forEach(etl -> {
            CompletableFuture.supplyAsync(() -> {
                EtlInfo etlInfo = etl.getEtlInfo();
                try {
                    etl.call();
                } catch (Exception e) {
                    etlInfo.setErrorInfo(e);
                    etlInfo.setSuccess(false);
                }
                return etlInfo;
            }, pool).whenCompleteAsync((etlInfo,throwable)->{
                if(ObjectUtils.isNotNull(throwable)) {
                    etlInfo.setErrorInfo(throwable);
                    etlInfo.setSuccess(false);
                }
                System.out.println(etlInfo);
            },pool);
        });
        return etlGroup.getEtlGroupNo();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("***************启动成功！**************");
//        doEtlGroup();
    }
}
