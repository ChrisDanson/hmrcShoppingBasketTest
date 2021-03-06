package uk.gov.hmrc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static uk.gov.hmrc.ProductId.APPLE;
import static uk.gov.hmrc.ProductId.ORANGE;

public class BasketTest {

    private final BigDecimal APPLE_PRICE = new BigDecimal("0.60");
    private final BigDecimal ORANGE_PRICE = new BigDecimal("0.25");

    private Map<ProductId, BigDecimal> unitPrices = new HashMap<>();
    private Map<ProductId, PriceRetriever> priceRetrievers = new HashMap<>();

    private Basket basket = new Basket(priceRetrievers);


    @Before
    public void setup() {
        setupUnitPrices();
        setupPriceRetrievers();
    }

    @Test
    public void anEmptyBasketHasNoValue(){

        assertEquals(0, basket.getTotal().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void anEmptyBasketHasNoQuantities(){

        assertThat(0L, is(basket.getCount(APPLE)));
        assertThat(0L, is(basket.getCount(ORANGE)));
    }

    @Test
    public void addOneAppleToBasket_checkValue(){

        addToBasket(APPLE);

        assertEquals(0, basket.getTotal().compareTo(unitPrices.get(APPLE)));
    }

    @Test
    public void addOneAppleToBasket_checkQuantity(){

        addToBasket(APPLE);

        assertThat(1L, is(basket.getCount(APPLE)));
    }

    @Test
    public void addOneAppleAndOneOrangeToBasket_checkValue(){

        addToBasket(APPLE, ORANGE);

        assertEquals(0, basket.getTotal().compareTo(unitPrices.get(APPLE).add(unitPrices.get(ORANGE))));
    }

    @Test
    public void addOneAppleAndOneOrangeToBasket_checkQuantity(){

        addToBasket(APPLE, ORANGE);

        assertThat(1L, is(basket.getCount(APPLE)));
        assertThat(1L, is(basket.getCount(ORANGE)));
    }

    @Test
    public void addTwoApplesToBasket_checkValue(){

        addToBasket(APPLE, APPLE);

        assertEquals(0, basket.getTotal().compareTo(unitPrices.get(APPLE)));
    }

    @Test
    public void addTwoApplesToBasket_checkQuantity(){

        addToBasket(APPLE, APPLE);

        assertThat(2L, is(basket.getCount(APPLE)));
    }

    @Test
    public void addTwoApplesAndOneOrangeToBasket_checkValue(){

        addToBasket(APPLE, APPLE, ORANGE);

        assertEquals(0, basket.getTotal().compareTo(unitPrices.get(APPLE).add(unitPrices.get(ORANGE))));
    }

    @Test
    public void addTwoApplesAndOneOrangeToBasket_checkQuantity(){

        addToBasket(APPLE, APPLE, ORANGE);

        assertThat(2L, is(basket.getCount(APPLE)));
        assertThat(1L, is(basket.getCount(ORANGE)));
    }

    @Test
    public void addTwoApplesAndThreeOrangesToBasket_checkValue(){

        addToBasket(APPLE, APPLE, ORANGE, ORANGE, ORANGE);

        assertEquals(0, basket.getTotal().compareTo(unmodifiedPriceFor(APPLE, 1).add(unmodifiedPriceFor(ORANGE, 2))));
    }

    @Test
    public void addThreeApplesAndFourOrangesToBasket_checkValue(){

        addToBasket(APPLE, APPLE, APPLE, ORANGE, ORANGE, ORANGE, ORANGE);

        assertEquals(0, basket.getTotal().compareTo(unmodifiedPriceFor(APPLE, 2).add(unmodifiedPriceFor(ORANGE, 3))));
    }


    private void addToBasket(ProductId... productIds)  {
        stream(productIds).forEach(productId -> basket.add(new Product(productId, unitPrices.get(productId))));
        basket.complete();
    }

    private BigDecimal unmodifiedPriceFor(ProductId productId, int quantity) {
        return unitPrices.get(productId).multiply(new BigDecimal(quantity));
    }

    private void setupPriceRetrievers() {
        priceRetrievers.put(APPLE, new BuyOneGetOneFree());
        priceRetrievers.put(ORANGE, new ThreeForTwo());
    }

    private void setupUnitPrices() {
        unitPrices.put(APPLE, APPLE_PRICE);
        unitPrices.put(ORANGE, ORANGE_PRICE);
    }
}
