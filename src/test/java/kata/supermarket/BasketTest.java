package kata.supermarket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items, Iterable<Discount> discounts) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        discounts.forEach(basket::addDiscountScheme);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                fourItemsWithBuyOneGetOneFreeDiscount(),
                fourItemsWithBuyTwoItemsForOnePoundDiscount(),
                fourItemsWithThreeForThePriceOfTwoDiscount(),
                multipleItemsPricedByWeightWithOneKiloOfCarrotsForHalfPrice(),
                fiveItemsWithMultipleDiscountSchemes()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()), Collections.emptyList());
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix()),
                Collections.emptyList()
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()), Collections.emptyList());
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()), Collections.emptyList());
    }

    private static Arguments fourItemsWithBuyOneGetOneFreeDiscount() {
        return Arguments.of("four items with buy one get one free discount", "2.53",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(), aPintOfMilk(), aPackOfDigestives()),
                Arrays.asList(buyOneGetOneFreeDiscount()));
    }

    private static Arguments fourItemsWithBuyTwoItemsForOnePoundDiscount() {
        return Arguments.of("four items with buy two items for one pound discount", "3.04",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()),
                Arrays.asList(buyTwoForOnePoundDiscount()));
    }

    private static Arguments fiveItemsWithMultipleDiscountSchemes() {
        return Arguments.of("five items with multiple discount schemes", "3.04",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk(), aPintOfMilk()),
                Arrays.asList( buyOneGetOneFreeDiscount(), buyTwoForOnePoundDiscount()));
        //Collections.emptyList());
    }


    private static Arguments fourItemsWithThreeForThePriceOfTwoDiscount() {
        return Arguments.of("four items with three for the price of two discount", "3.59",
                Arrays.asList(aPackOfDigestives(), aPackOfDigestives(), aPackOfDigestives(), aPintOfMilk()),
                Arrays.asList( buyTwoGetOneFreeDiscount()));
    }
    private static Arguments multipleItemsPricedByWeightWithOneKiloOfCarrotsForHalfPrice() {
        return Arguments.of("multiple items priced by weight with one kilo of vegetables for half price", "1.50",
                Arrays.asList(
                        twoFiftyGramsOfCarrots(),
                        twoFiftyGramsOfOnions(),
                        twoFiftyGramsOfCarrots(),
                        twoFiftyGramsOfOnions(),
                        twoFiftyGramsOfCarrots(),
                        twoFiftyGramsOfCarrots()
                        ),
                Arrays.asList(BuyOneKiloOfVegetableGroupForHalfDiscount())
        );
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product("001", "Milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product("002", "Digestives", new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct("003", "American Sweets", new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct("004", "Pick and Mix", new BigDecimal("2.99"));
    }

    private static WeighedProduct aKiloOfCarrots() {
        return new WeighedProduct("105", "Carrot", new BigDecimal("1.00"));
    }

    private static WeighedProduct aKiloOfOnions() {
        return new WeighedProduct("106", "Onion", new BigDecimal("2.00"));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }


    private static Item twoFiftyGramsOfCarrots() {
        return aKiloOfCarrots().weighing(new BigDecimal(".25"));
    }
    private static Item twoFiftyGramsOfOnions() {
        return aKiloOfOnions().weighing(new BigDecimal(".25"));
    }

    //Discount schemes
    private static Discount buyOneGetOneFreeDiscount(){ return  new BuyXGetOneFreeDiscount("001", 2);}
    private static Discount buyTwoForOnePoundDiscount(){ return  new BuyXForYPoundDiscount("002", 2, BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));}
    private static Discount buyTwoGetOneFreeDiscount(){ return  new BuyXGetOneFreeDiscount("002", 3);}
    private static Discount BuyOneKiloOfVegetableGroupForHalfDiscount(){ return  new BuyXKilosOfYGroupForHalfDiscount("105",  BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP));}
}