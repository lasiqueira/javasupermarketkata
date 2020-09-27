package kata.supermarket;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyXGetOneFreeDiscountTest {

    @DisplayName("Buy One Get One Free Discount provides discounted value...")
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
                threeItemsWithBuyOneGetOneFreeDiscount(),
                fourItemsWithBuyOneGetOneFreeDiscount(),
                threeItemsWithBuyTwoGetOneFreeDiscount(),
                fourItemsWithBuyTwoGetOneFreeDiscount()
        );
    }



    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "0.00",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()),
                buyOneGetOneFreeDiscount());
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.00", Collections.singleton(aPintOfMilk()), buyOneGetOneFreeDiscount());
    }

    private static Arguments threeItemsWithBuyOneGetOneFreeDiscount() {
        return Arguments.of("three items with buy one get one free discount", "0.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk()), buyOneGetOneFreeDiscount());
    }

    private static Arguments fourItemsWithBuyOneGetOneFreeDiscount() {
        return Arguments.of("four items with buy one get one free discount", "0.98",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPintOfMilk()), buyOneGetOneFreeDiscount());
    }

    private static Arguments threeItemsWithBuyTwoGetOneFreeDiscount() {
        return Arguments.of("three items with buy two get one free discount", "0.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk()), buyTwoGetOneFreeDiscount());
    }

    private static Arguments fourItemsWithBuyTwoGetOneFreeDiscount() {
        return Arguments.of("four items with buy two get one free discount", "0.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPintOfMilk()), buyTwoGetOneFreeDiscount());
    }


    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList() , buyOneGetOneFreeDiscount());
    }

    private static Item aPintOfMilk() {
        return new Product("001", "Milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product("002", "Digestives", new BigDecimal("1.55")).oneOf();
    }

    private static Discount buyOneGetOneFreeDiscount(){ return  new BuyXGetOneFreeDiscount("001", 2);}

    private static Discount buyTwoGetOneFreeDiscount(){ return  new BuyXGetOneFreeDiscount("001", 3);}
}
