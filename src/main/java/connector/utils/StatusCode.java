package connector.utils;


/**
 * @Description
 * @Package connector.utils
 * @author wangqi26959
 * @date 2020-04-10
 */
public enum StatusCode {
    /** */
    OK(200),
    /** */
    NOT_FOUND(404);
    private int code;

     StatusCode(int i) {
        code = i;
    }

    @Override
    public String toString() {
        return this.code + " " + this.name().replaceAll("_", " ");
    }
}
