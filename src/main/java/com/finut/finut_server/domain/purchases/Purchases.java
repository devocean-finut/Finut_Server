package com.finut.finut_server.domain.purchases;

import com.finut.finut_server.domain.product.Product;
import com.finut.finut_server.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(Purchases.PurchasesId.class)
public class Purchases {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    public static class PurchasesId implements Serializable {
        private Long user;
        private Long product;
    }
}
