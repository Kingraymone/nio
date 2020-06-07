package base.baseface.container;

import base.baseface.BaseLifecycle;
import base.face.container.Pipeline;
import base.face.container.Valve;

public class BasePipeline extends BaseLifecycle implements Pipeline {
    @Override
    public Valve getBasic() {
        return null;
    }

    @Override
    public void setBasic(Valve valve) {

    }

    @Override
    public void addValve() {

    }

    @Override
    public Valve[] getValves() {
        return new Valve[0];
    }

    @Override
    public void removeValve() {

    }

    @Override
    public Valve getFirst() {
        return null;
    }
}
