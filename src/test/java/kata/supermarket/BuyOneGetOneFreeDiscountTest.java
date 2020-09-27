package kata.supermarket;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyOneGetOneFreeDiscountTest {

    @DisplayName("Buy One Get One Free Discount provides discounted value...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void discountSchemeProvidesDiscountedValue(String description, String expectedTotal, Iterable<Item> items) {
        Discount discount = new BuyOneGetOneFreeDiscount("001");
        List<Item> itemList = new ArrayList<>();
        items.forEach(itemList::add);
        assertEquals(new BigDecimal(expectedTotal), discount.getDiscount(itemList));
    }

    static Stream<Arguments> discountSchemeProvidesDiscountedValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                threeItemsWithBuyOneGetOneFreeDiscount(),
                fourItemsWithBuyOneGetOneFreeDiscount()
        );
    }



    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "0.00",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.00", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments threeItemsWithBuyOneGetOneFreeDiscount() {
        return Arguments.of("four items with buy one get one free discount", "0.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk()));
    }

    private static Arguments fourItemsWithBuyOneGetOneFreeDiscount() {
        return Arguments.of("four items with buy one get one free discount", "0.98",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPintOfMilk()));
    }

    private static Arguments fourItemsWithBuyTwoItemsForOnePoundDiscount() {
        return Arguments.of("four items with buy two items for pne pound discount", "3.04",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments fourItemsWithThreeForThePriceOfTwoDiscount() {
        return Arguments.of("four items with three for the price of two discount", "3.59",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()));
    }


    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product("001", "Milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product("002", "Digestives", new BigDecimal("1.55")).oneOf();
    }
}
