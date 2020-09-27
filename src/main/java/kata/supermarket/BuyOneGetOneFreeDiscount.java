package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BuyOneGetOneFreeDiscount implements Discount{
    private String productCode;

    public BuyOneGetOneFreeDiscount(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        List<BigDecimal> discountedList = filterDiscountedItemPrice(items);
        return discountedList.stream()
                .findFirst()
                .orElse(BigDecimal.valueOf(0.00))
                .multiply(BigDecimal.valueOf(discountedList.size()/2))
                .setScale(2, BigDecimal.ROUND_HALF_UP);

    }
    private List<BigDecimal> filterDiscountedItemPrice(List<Item> items){
        return items.stream()
                .filter(item -> item.code().equals(productCode))
                .map(Item::price)
                .collect(Collectors.toList());
    }
}
