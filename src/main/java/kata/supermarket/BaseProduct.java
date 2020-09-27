package kata.supermarket;

public abstract class BaseProduct {
    private final String code;
    private final String name;

    public BaseProduct(String code, String name) {
        this.code = code;
        this.name = name;
    }

    String code(){return code;}
    String name(){return name;}
}
