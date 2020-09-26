package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments fourItemsWithBuyOneGetOneFreeDiscount() {
        return Arguments.of("four items with buy one get one free discount", "2.53",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPackOfDigestives()));
    }

    private static Arguments fourItemsWithBuyTwoItemsForOnePoundDiscount() {
        return Arguments.of("four items with buy two items for pne pound discount", "3.04",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments fourItemsWithThreeForThePriceOfTwoDiscount() {
        return Arguments.of("four items with three for the price of two discount", "3.59",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments multipleItemsPricedByWeightWithOneKiloOfVegetablesForHalfPrice() {
        return Arguments.of("multiple items priced by weight with one kilo of vegetables for half price", "2.60",
                Arrays.asList(
                        twoFiftyGramsOfAmericanSweets(),
                        twoHundredGramsOfPickAndMix(),
                        twoFiftyGramsGramsOfVegetables(),
                        twoFiftyGramsGramsOfVegetables(),
                        twoFiftyGramsGramsOfVegetables(),
                        twoFiftyGramsGramsOfVegetables(),
                        twoFiftyGramsGramsOfVegetables())
        );
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product(new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product(new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }

    private static WeighedProduct aKiloOfVegetables() {
        return new WeighedProduct(new BigDecimal("1.00"));
    }

    private static Item twoFiftyGramsGramsOfVegetables() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".25"));
    }
}