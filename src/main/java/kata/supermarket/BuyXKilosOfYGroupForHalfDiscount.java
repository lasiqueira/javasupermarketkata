package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class BuyXKilosOfYGroupForHalfDiscount implements Discount{
    private String productGroup;
    private BigDecimal weightInKilos;

    public BuyXKilosOfYGroupForHalfDiscount(String productGroup, BigDecimal weightInKilos) {
        this.productGroup = productGroup;
        this.weightInKilos = weightInKilos;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        List<BigDecimal> discountedList = filterDiscountedItemPrice(items);
        //TODO implement a way to access the weight of the item in order to calculate the discount
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    }
    private List<BigDecimal> filterDiscountedItemPrice(List<Item> items){
        return items.stream()
                .filter(item -> item.code().startsWith(productGroup))
                .map(Item::price)
                .collect(Collectors.toList());
    }
}
