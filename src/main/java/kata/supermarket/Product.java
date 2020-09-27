package kata.supermarket;

import java.math.BigDecimal;

public class Product extends BaseProduct{

    private final BigDecimal pricePerUnit;

    public Product(final String code, final String name, final BigDecimal pricePerUnit) {
        super(code, name);
        this.pricePerUnit = pricePerUnit;
    }

    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }
}
