package uk.gov.hmrc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static uk.gov.hmrc.ProductId.APPLE;
import static uk.gov.hmrc.ProductId.ORANGE;

public class BasketTest {

    private final BigDecimal APPLE_PRICE = new BigDecimal("0.60");
    private final BigDecimal ORANGE_PRICE = new BigDecimal("0.25");

    private Map<ProductId, BigDecimal> prices = new HashMap<>();

    Basket basket = new Basket();


    @Before
    public void setup() {
        prices.put(APPLE, APPLE_PRICE);
        prices.put(ORANGE, ORANGE_PRICE);
    }

    @Test
    public void anEmptyBasketHasNoValue(){

        assertEquals(0, basket.getTotal().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void addOneAppleToBasket_checkValue(){

        addToBasket(APPLE);

        assertEquals(0, basket.getTotal().compareTo(prices.get(APPLE)));
    }

    @Test
    public void addOneAppleToBasket_checkQuantity(){

        addToBasket(APPLE);

        assertThat(1L, is(basket.getCount(APPLE)));
    }

    @Test
    public void addOneAppleAndOneOrangeToBasket_checkValue(){

        addToBasket(APPLE, ORANGE);

        assertEquals(0, basket.getTotal().compareTo(prices.get(APPLE).add(prices.get(ORANGE))));
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

        assertEquals(0, basket.getTotal().compareTo(prices.get(APPLE).add(prices.get(APPLE))));
    }

    @Test
    public void addTwoApplesToBasket_checkQuantity(){

        addToBasket(APPLE, APPLE);

        assertThat(2L, is(basket.getCount(APPLE)));
    }

    @Test
    public void addTwoApplesAndOneOrangeToBasket_checkValue(){

        addToBasket(APPLE, APPLE, ORANGE);

        assertEquals(0, basket.getTotal().compareTo(prices.get(APPLE).add(prices.get(APPLE).add(prices.get(ORANGE)))));
    }

    @Test
    public void addTwoApplesAndOneOrangeToBasket_checkQuantity(){

        addToBasket(APPLE, APPLE, ORANGE);

        assertThat(2L, is(basket.getCount(APPLE)));
        assertThat(1L, is(basket.getCount(ORANGE)));
    }

    private void addToBasket(ProductId... productIds)  {

        stream(productIds).forEach(productId -> basket.add(new Product(productId, prices.get(productId))));
        basket.complete();
    }
}
