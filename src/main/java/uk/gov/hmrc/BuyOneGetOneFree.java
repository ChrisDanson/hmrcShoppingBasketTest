package uk.gov.hmrc;

import java.math.BigDecimal;
import java.util.List;

public class BuyOneGetOneFree implements PriceRetriever {

    @Override
    public BigDecimal getPrice(List<Product> productsForId) {

        BigDecimal unitPrice = productsForId.get(0).getUnitPrice();
        BigDecimal offerPriceForQuantity = unitPrice.multiply(new BigDecimal(productsForId.size() / 2));

        if(productsForId.size() % 2 == 0) {
            return offerPriceForQuantity;
        }
        return offerPriceForQuantity.add(unitPrice);
    }
}
