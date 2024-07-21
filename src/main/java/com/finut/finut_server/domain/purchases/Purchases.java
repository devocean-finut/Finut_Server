package com.finut.finut_server.domain.purchases;

import com.finut.finut_server.domain.product.Product;
import com.finut.finut_server.domain.user.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
