package com.key.customannotation.service;

import com.key.customannotation.entity.Syslog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SysLogService {
    Logger logger = LoggerFactory.getLogger(SysLogService.class);

    public boolean save(Syslog sysLogBO){
        // 这里就不做具体实现了
        logger.info(sysLogBO.getParams());
        return true;
    }
}
