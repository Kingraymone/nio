package base.face.container;

/***
 * servlet容器父类
 * 可以包含多个子容器，通过管道流执行
 */
public interface Container {
    // 管道流
    Pipeline getPipeline();

    // 容器间关系
    Container getParent();

    void setParent(Container container);

    void addChild(Container container);

    void findChild(Container container);

    Container[] findChildren();

    void removeChild();

}
