package base.baseface;

import base.face.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseLifecycle implements Lifecycle {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public void init() {
        logger.debug("init()执行！");
    }

    public void start() {
        logger.debug("start()执行！");
    }

    public void stop() {
        logger.debug("stop()执行！");
    }

    public void destroy() {
        logger.debug("destroy()执行！");
    }
}
