package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ItemByWeight implements Item {

    private final WeighedProduct product;
    private final BigDecimal weightInKilos;

    ItemByWeight(final WeighedProduct product, final BigDecimal weightInKilos) {
        this.product = product;
        this.weightInKilos = weightInKilos;
    }

    public BigDecimal price() {
        return product.pricePerKilo().multiply(weightInKilos).setScale(2, RoundingMode.HALF_UP);
    }

    public String code(){return product.code();}
    public String name(){return product.name();}

    public BigDecimal getWeightInKilos(){
        return weightInKilos;
    }
}
