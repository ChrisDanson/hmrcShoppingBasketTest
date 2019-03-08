package uk.gov.hmrc;

import java.math.BigDecimal;
import java.util.List;

interface PriceRetriever {

    BigDecimal getPrice(List<Product> productsForId);
}
