package utils;

public enum StatusCode {
    OK(200), NOT_FOUND(404);
    private int code;

    StatusCode(int i) {
        code = i;
    }

    @Override
    public String toString() {
        return this.code + " " + this.name().replaceAll("_", " ");
    }
}
