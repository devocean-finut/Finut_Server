package com.finut.finut_server.domain.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String name; // 상품 이름

    @Column(nullable = false)
    private Long price; // 상품 가격

    @Column(nullable = false)
    private Category category; // CLOTHES(0), GOODS(1), PLANT(2), ETC(3)

    @Column
    private String image; //이미지 url

}
