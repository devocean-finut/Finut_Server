package com.finut.finut_server.domain.purchases;


import com.finut.finut_server.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasesRepository extends JpaRepository<Purchases, Purchases.PurchasesId> {
    List<Purchases> findByUser(Users user);

    boolean existsByUserIdAndProductId(Long userId, Long productId);
}