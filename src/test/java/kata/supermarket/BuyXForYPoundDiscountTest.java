package kata.supermarket;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyXForYPoundDiscountTest {

    @DisplayName("Buy X for Y Pounds Discount provides discounted value...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void discountSchemeProvidesDiscountedValue(String description, String expectedTotal, Iterable<Item> items, Discount discount) {
        List<Item> itemList = new ArrayList<>();
        items.forEach(itemList::add);
        assertEquals(new BigDecimal(expectedTotal), discount.getDiscount(itemList));
    }

    static Stream<Arguments> discountSchemeProvidesDiscountedValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                threeItemsWithBuyTwoItemsForOnePoundDiscount(),
                threeItemsWithBuyTwoItemsForOnePoundDiscountFail()
        );
    }



    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "0.00",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()),
                buyTwoForOnePoundDiscountDigestive());
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.00", Collections.singleton(aPintOfMilk()), buyTwoForOnePoundDiscountDigestive());
    }


    private static Arguments threeItemsWithBuyTwoItemsForOnePoundDiscount() {
        return Arguments.of("three items with buy two for one pound discount", "2.10",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives()),
                buyTwoForOnePoundDiscountDigestive());
    }

    private static Arguments threeItemsWithBuyTwoItemsForOnePoundDiscountFail() {
        return Arguments.of("three items with buy two for one pound discount fail", "0.00",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk()),
        buyTwoForOnePoundDiscountMilk());
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), buyTwoForOnePoundDiscountDigestive());
    }

    private static Item aPintOfMilk() {
        return new Product("001", "Milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product("002", "Digestives", new BigDecimal("1.55")).oneOf();
    }

    private static Discount buyTwoForOnePoundDiscountDigestive(){ return  new BuyXForYPoundDiscount("002", 2, BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));}
    private static Discount buyTwoForOnePoundDiscountMilk(){ return  new BuyXForYPoundDiscount("002", 1, BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));}


}
