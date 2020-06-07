package base.face.container;

/**
 * 管道流，责任链模式执行
 */
public interface Pipeline {
    Valve getBasic();
    void setBasic(Valve valve);
    void addValve();
    Valve[] getValves();
    void removeValve();
    Valve getFirst();
}
