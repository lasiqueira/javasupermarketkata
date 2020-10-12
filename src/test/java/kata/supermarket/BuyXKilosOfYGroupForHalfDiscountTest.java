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

public class BuyXKilosOfYGroupForHalfDiscountTest {

    @DisplayName("Buy X Kilos of Y Group for half Discount provides discounted value...")
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
                aKiloOfCarrotsItem()

        );
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), BuyOneKiloOfVegetableGroupForHalfDiscount());
    }

    private static Arguments aKiloOfCarrotsItem() {
        return Arguments.of("A kilo of carrots", "0.50", Arrays.asList(oneKiloOfCarrots()), BuyOneKiloOfVegetableGroupForHalfDiscount());
    }
    private static Item aPintOfMilk() {
        return new Product("001", "Milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product("002", "Digestives", new BigDecimal("1.55")).oneOf();
    }


    private static Discount BuyOneKiloOfVegetableGroupForHalfDiscount(){ return  new BuyXKilosOfYGroupForHalfDiscount("105",  BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));}

    private static WeighedProduct aKiloOfCarrots() {
        return new WeighedProduct("105", "Carrot", new BigDecimal("1.00"));
    }

    private static Item oneKiloOfCarrots() {
        return aKiloOfCarrots().weighing(new BigDecimal("1.00"));
    }

}
