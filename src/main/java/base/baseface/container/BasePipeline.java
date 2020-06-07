package base.baseface.container;

import base.baseface.BaseLifecycle;
import base.face.container.Pipeline;
import base.face.container.Valve;

import java.util.ArrayList;
import java.util.List;

public class BasePipeline extends BaseLifecycle implements Pipeline {
    private Valve basic = null;
    private Valve first = null;

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
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
