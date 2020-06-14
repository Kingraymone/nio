package base.baseface.container.valve;

import base.baseface.BaseLifecycle;
import base.face.container.Container;
import base.face.container.Pipeline;
import base.face.container.Valve;

import java.util.ArrayList;
import java.util.List;

public class BasePipeline extends BaseLifecycle implements Pipeline {
    private Valve basic = null;
    private Valve first = null;
    private Container container;

    public BasePipeline(Container container){
        this.container=container;
    }
    @Override
    public void setContainer(Container container) {
        this.container=container;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
        valve.setContainer(container);
    }

    @Override
    public void addValve(Valve valve) {
        valve.setContainer(container);
        if (first == null) {
            first = valve;
            valve.setNext(basic);
        } else {
            Valve current = first;
            while (current != null) {
                if (current.getNext() == basic) {
                    current.setNext(valve);
                    valve.setNext(basic);
                    break;
                }
                current = current.getNext();
            }
        }
    }

    @Override
    public Valve[] getValves() {
        List<Valve> valveList = new ArrayList<>();
        Valve current = first;
        if (current == null) {
            return new Valve[]{basic};
        } else {
            while (current != null) {
                valveList.add(current);
                current = current.getNext();
            }
            return valveList.toArray(new Valve[0]);
        }
    }

    @Override
    public void removeValve() {

    }

    @Override
    public Valve getFirst() {
        return first;
    }
}
