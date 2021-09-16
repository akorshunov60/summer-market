package ru.geekbrains.summer.beans;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.geekbrains.summer.dto.OrderItemDto;
import ru.geekbrains.summer.model.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Data
public class Cart {

    private List<OrderItemDto> items;
    private BigDecimal price;

    public Cart() {
        items = new ArrayList<>();
        price = BigDecimal.ZERO;
    }

    public boolean add(Long productId) {
        for (OrderItemDto o : items) {
            if (o.getProductId().equals(productId)) {
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void add(ProductEntity productEntity) {
        items.add(new OrderItemDto(productEntity));
        recalculate();
    }

    public void clear() {
        items.clear();
        price = BigDecimal.ZERO;
    }

    private void recalculate() {
        price = BigDecimal.ZERO;
        for (OrderItemDto oid : items) {
            price = price.add(oid.getPrice());
        }
    }

    public void remove(Long productId) {
        items.removeIf(oi -> oi.getProductId().equals(productId));
        recalculate();
    }

    public void changeQuantity(Long productId) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto o = iter.next();
            if (o.getProductId().equals(productId)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
