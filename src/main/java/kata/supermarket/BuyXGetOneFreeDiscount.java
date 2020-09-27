package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class BuyXGetOneFreeDiscount implements Discount{
    private String productCode;
    private int numberOfProducts;

    public BuyXGetOneFreeDiscount(String productCode, int numberOfProducts) {
        this.productCode = productCode;
        this.numberOfProducts = numberOfProducts;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        List<BigDecimal> discountedList = filterDiscountedItemPrice(items);
        return discountedList.stream()
                .findFirst()
                .orElse(BigDecimal.valueOf(0.00))
                .multiply(BigDecimal.valueOf(discountedList.size()/numberOfProducts))
                .setScale(2, RoundingMode.HALF_UP);

    }
    private List<BigDecimal> filterDiscountedItemPrice(List<Item> items){
        return items.stream()
                .filter(item -> item.code().equals(productCode))
                .map(Item::price)
                .collect(Collectors.toList());
    }
}
