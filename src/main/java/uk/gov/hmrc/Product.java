package uk.gov.hmrc;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final ProductId productId;
    private final BigDecimal unitPrice;

    Product(ProductId productId, BigDecimal unitPrice) {
        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    BigDecimal getUnitPrice() {
        return unitPrice;
    }

    ProductId getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                Objects.equals(unitPrice, product.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, unitPrice);
    }
}
