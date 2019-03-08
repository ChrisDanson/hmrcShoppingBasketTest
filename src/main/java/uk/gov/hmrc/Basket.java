package uk.gov.hmrc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class Basket {

    private BigDecimal total = BigDecimal.ZERO;
    private List<Product> products = new ArrayList<>();
    private Map<ProductId, PriceRetriever> priceRetrievers;

    Basket(Map<ProductId, PriceRetriever> priceRetrievers) {

        this.priceRetrievers = priceRetrievers;
    }

    BigDecimal getTotal() {
        return total;
    }

    void add(Product product) {
        products.add(product);
    }

    void complete() {
        total = getProductIds().stream().map(this::getPriceForProductsWithId).collect(sumBigDecimal());
    }

    Long getCount(ProductId productId) {
        return products.stream().filter(product -> product.getProductId().equals(productId)).count();
    }

    private Collector<BigDecimal, ?, BigDecimal> sumBigDecimal() {
        return reducing(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<ProductId> getProductIds() {
        return products.stream().map(Product::getProductId).collect(toSet());
    }

    private BigDecimal getPriceForProductsWithId(ProductId productId) {

        List<Product> productsForId = getProductsForId(productId).collect(toList());
        return priceRetrievers.get(productId).getPrice(productsForId);
    }

    private Stream<Product> getProductsForId(ProductId productId) {
        return products.stream().filter(product -> product.getProductId().equals(productId));
    }

}
