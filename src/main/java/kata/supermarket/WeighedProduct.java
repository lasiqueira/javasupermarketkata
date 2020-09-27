package kata.supermarket;

import java.math.BigDecimal;

public class WeighedProduct extends BaseProduct{

    private final BigDecimal pricePerKilo;

    public WeighedProduct(final String code, final String name, final BigDecimal pricePerKilo) {
        super(code, name);
        this.pricePerKilo = pricePerKilo;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }
}
