package connector.httpserver;


/**
 * @Description 门面模式
 * @Package connector.httpserver
 * @date 2020-04-14
 */
public class ResponseFacade {
    private Response response;

    public ResponseFacade(Response response) {
        this.response = response;
    }

    public void responseResult() {
        response.responseStatic();
    }
}
