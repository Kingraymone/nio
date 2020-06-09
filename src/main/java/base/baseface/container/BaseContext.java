package base.baseface.container;

import base.face.container.Context;

public class BaseContext extends BaseContainer implements Context {

    @Override
    public void init() {
        logger.debug("init()执行--Context");
    }

    @Override
    public void start() {
        logger.debug("start()执行--Context！");
    }

    @Override
    public void stop() {
        logger.debug("stop()执行--Context！");
    }

}
