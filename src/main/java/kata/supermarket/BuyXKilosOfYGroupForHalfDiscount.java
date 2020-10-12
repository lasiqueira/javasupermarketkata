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
        BigDecimal discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        List<ItemByWeight> weightedItemList = filterDiscountedItem(items);
        BigDecimal totalWeight = weightedItemList.stream()
                .map(ItemByWeight::getWeightInKilos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalWeight.compareTo(weightInKilos) >= 0){
            discount = weightedItemList.stream()
                    .map(ItemByWeight::price)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP);
        }
        return discount;

    }
    private List<ItemByWeight> filterDiscountedItem(List<Item> items){
        return items.stream()
                .filter(item -> item.code().equals(productGroup))
                .map(item -> (ItemByWeight) item)
                .collect(Collectors.toList());
    }
}
