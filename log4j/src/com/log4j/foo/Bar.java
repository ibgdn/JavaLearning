package com.log4j.foo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Administrator on 2017/7/14.
 */
public class Bar {
    static final Logger logger = LogManager.getLogger(Bar.class.getName());

    public boolean doIt(){
        logger.entry();
        logger.error("Did it again!");
        return logger.exit(false);
    }
}
