package com.finut.finut_server.domain.purchases;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesRepository extends JpaRepository<Purchases, Purchases.PurchasesId> {
}