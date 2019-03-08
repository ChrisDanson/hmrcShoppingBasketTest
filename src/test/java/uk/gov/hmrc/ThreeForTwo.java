package uk.gov.hmrc;

import java.math.BigDecimal;
import java.util.List;

public class ThreeForTwo implements PriceRetriever {

    @Override
    public BigDecimal getPrice(List<Product> productsForId) {
        BigDecimal unitPrice = productsForId.get(0).getUnitPrice();
        BigDecimal offerPriceForQuantity = unitPrice.multiply(new BigDecimal((productsForId.size() / 3) * 2));

        if(productsForId.size() % 3 == 0) {
            return offerPriceForQuantity;
        }
        return offerPriceForQuantity.add(unitPrice.multiply(new BigDecimal(productsForId.size() % 3)));
    }
}
