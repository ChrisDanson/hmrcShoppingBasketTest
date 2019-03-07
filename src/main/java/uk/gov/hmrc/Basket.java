package uk.gov.hmrc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import static java.util.stream.Collectors.reducing;

class Basket {

    private BigDecimal total = BigDecimal.ZERO;
    private List<Product> products = new ArrayList<>();

    BigDecimal getTotal() {
        return total;
    }

    void add(Product product) {
        products.add(product);
    }

    void complete() {
        total = products.stream().map(product -> product.getUnitPrice()).collect(sumBigDecimal());
    }

    Long getCount(ProductId productId) {
        return products.stream().filter(product -> product.getProductId().equals(productId)).count();
    }

    private Collector<BigDecimal, ?, BigDecimal> sumBigDecimal() {
        return reducing(BigDecimal.ZERO, BigDecimal::add);
    }

}
