package com.log4j;


import com.log4j.foo.Bar;
import org.apache.log4j.*;

/**
 * Created by Administrator on 2017/7/7.
 *  The test class for Log4j2.
 */
public class MyApp {
    // Define a static logger variable so that it references the
    // Logger instance named "MyApp".
    private static final Logger logger = LogManager.getLogger(MyApp.class);
    public static void main(String[] args) {
//        simple();
        simple1();
    }

    private static void simple1() {
        // Set up a simple configuration that logs on the console.
        logger.trace("Entering application.");
        Bar bar = new Bar();
        if(!bar.doIt()){
            logger.error("Didn't do it.");
        }
        logger.trace("Exiting application.");
    }

    /*  使用Log4j来进行日志输出。 采用如下代码，执行雷同的输出。 可以看到输出结果有几个改观：
            1. 知道是log4j.TestLog4j这个类里的日志
            2. 是在[main]线程里的日志
            3. 日志级别可观察，一共有6个级别 TRACE DEBUG INFO WARN ERROR FATAL
            4. 日志输出级别范围可控制， 如代码所示，只输出高于DEBUG级别的，那么TRACE级别的日志自动不输出
            5. 每句日志消耗的毫秒数(最前面的数字)，可观察，这样就可以进行性能计算*/
    public static void simple() {
        // 进行默认配置
        BasicConfigurator.configure();

        // 设置日志输出级别
        logger.setLevel(Level.TRACE);

        // 进行不同级别的日志输出
        logger.trace("跟踪输出");
        logger.debug("调试输出");
        logger.info("信息输出");

        try {
            // 为了便于观察前后日志输出的时间差
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.warn("警告输出");
        logger.error("错误输出");
        logger.fatal("致命输出");
    }
}
