package cc.kevinlee.modernjava.e07_functionalinterface_examples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author Kevin Lee
 * @since 2015-08-08
 */
public class FunctionalInterfaceExamples {
    public static void main(String[] args) {
        Product product1 = new Product(1L, "A", new BigDecimal("10.00"));
        Product product2 = new Product(2L, "B", new BigDecimal("55.50"));
        Product product3 = new Product(3L, "C", new BigDecimal("17.45"));
        Product product4 = new Product(4L, "D", new BigDecimal("23.00"));
        Product product5 = new Product(5L, "E", new BigDecimal("110.99"));
        final List<Product> products = Arrays.asList(
                product1,
                product2,
                product3,
                product4,
                product5
        );
        final BigDecimal twenty = new BigDecimal("20");
        /* 가격이 $20 이상 되는 Product 찾기 */
//        List<Product> result = filter(products, product -> product.getPrice().compareTo(twenty) >= 0);

        System.out.println("product >= $20 : " + filter(products, product -> product.getPrice().compareTo(twenty) >= 0));
        /* 가격이 $10 이하 되는 Product 찾기 */
        System.out.println("product <= $10 : " + filter(products, product -> product.getPrice().compareTo(new BigDecimal("10")) <= 0));
        /* 가격이 $50 초과되는 Product 찾기 */
        final List<Product> expensiveProducts = filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);
        /*
         * 가격이 $50 초과되는 Product를 찾은후 (filter)
         * 50% 할인 가격 적용 (원래 가격 * 0.5)
         */
        final List<DiscountedProducts> discountedProducts = map(expensiveProducts, product -> new DiscountedProducts(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        System.out.println("expensive products : " + expensiveProducts);
        System.out.println("discounted products : " + discountedProducts);

        final Predicate<Product> lessThanOrEqualTo30 = product -> product.getPrice().compareTo(new BigDecimal("30")) <= 0;
        System.out.println("discounted products(<=30) : " + filter(discountedProducts, lessThanOrEqualTo30));
        System.out.println("          products(<=30) : " + filter(products, lessThanOrEqualTo30));

        final List<BigDecimal> prices = map(products, product -> product.getPrice());
        BigDecimal total = BigDecimal.ZERO;
        for (final BigDecimal price : prices) {
            total = total.add(price);
        }
        System.out.println("   total : " + total);

        final BigDecimal newTotal = total(products, product -> product.getPrice());
        System.out.println("newTotal : " + newTotal);

        final BigDecimal discountedTotal = total(discountedProducts, product -> product.getPrice());
        System.out.println("discountedTotal : " + discountedTotal);

        Order order = new Order(1L, "on-1234", Arrays.asList(
                new OrderedItem(1L, product1, 2),
                new OrderedItem(2L, product3, 1),
                new OrderedItem(3L, product4, 10)
        ));

        BigDecimal orderTotal = BigDecimal.ZERO;
        for(OrderedItem item : order.getItems()){
            orderTotal = orderTotal.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()))
            );
        }
        System.out.println("order total in old way: " + orderTotal);
        System.out.println("           order total: " + order.totalPrice());
    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T t : list) {
            /* Predicate.test()를 만족하는 경우만 저장 */
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            /* function을 이용해서 타입변환: T -> R */
            result.add(function.apply(t));
        }
        return result;
    }

    private static <T> BigDecimal total(List<T> list, Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;
        for (final T t : list) {
            /* mapper를 이용해서 T타입을 BigDecimal로 변환 */
            total = total.add(mapper.apply(t));
        }
        return total;
    }

    /* 이 아래에 있는 JavaBean들은 Lombok을 사용하셔야 합니다. 영상을 참조해 주세요. */

    @AllArgsConstructor
    @Data
    static class Product {
        private Long id;
        private String name;
        private BigDecimal price;

    }

    @ToString(callSuper = true)
    static class DiscountedProducts extends Product {
        public DiscountedProducts(Long id, String name, BigDecimal price) {
            super(id, name, price);
        }
    }

    @AllArgsConstructor
    @Data
    static class OrderedItem {
        private Long id;
        private Product product;
        private int quantity;

        public BigDecimal getItemTotal(){
            return product.getPrice().multiply(new BigDecimal(quantity));
        }
    }

    @AllArgsConstructor
    @Data
    static class Order {
        private Long id;
        private String orderNumber;
        private List<OrderedItem> items;

        public BigDecimal totalPrice() {
            return total(items, item -> item.getItemTotal());
        }
    }
}

