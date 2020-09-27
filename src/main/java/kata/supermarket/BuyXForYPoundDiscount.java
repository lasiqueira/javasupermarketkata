package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class BuyXForYPoundDiscount implements Discount{
    private String productCode;
    private BigDecimal discountedPrice;
    private int numberOfProducts;

    public BuyXForYPoundDiscount(String productCode, int numberOfProducts, BigDecimal discountedPrice) {
        this.productCode = productCode;
        this.discountedPrice = discountedPrice;
        this.numberOfProducts = numberOfProducts;
    }

    @Override
    public BigDecimal getDiscount(List<Item> items) {
        List<BigDecimal> discountedList = filterDiscountedItemPrice(items);
        BigDecimal discount = BigDecimal.ZERO;
        if(discountedList.size()>=numberOfProducts) {
            discount = discountedList.stream()
                    .limit(numberOfProducts)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .subtract(discountedPrice)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return discount.compareTo(BigDecimal.ZERO) ==1? discount: BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);


    }
    private List<BigDecimal> filterDiscountedItemPrice(List<Item> items){
        return items.stream()
                .filter(item -> item.code().equals(productCode))
                .map(Item::price)
                .collect(Collectors.toList());
    }
}
