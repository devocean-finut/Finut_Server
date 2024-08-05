package com.finut.finut_server.service;

import com.finut.finut_server.apiPayload.ApiResponse;
import com.finut.finut_server.domain.product.Product;
import com.finut.finut_server.domain.product.ProductRepository;
import com.finut.finut_server.domain.purchases.Purchases;
import com.finut.finut_server.domain.purchases.PurchasesRepository;
import com.finut.finut_server.domain.user.Users;
import com.finut.finut_server.domain.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchasesService {

    private static PurchasesRepository purchasesRepository;
    private static UsersRepository usersRepository;

    private static ProductRepository productRepository;

    @Autowired
    public PurchasesService(PurchasesRepository purchasesRepository, UsersRepository usersRepository, ProductRepository productRepository)
    {
        this.purchasesRepository = purchasesRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
    }

    public static List<Product> getMyProducts(Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Purchases> purchases = purchasesRepository.findByUser(user);
        return purchases.stream().map(Purchases::getProduct).collect(Collectors.toList());
    }

    public static Purchases buyProduct(Long userId, Long productId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        boolean alreadyPurchased = purchasesRepository.existsByUserIdAndProductId(userId, productId);
        if (alreadyPurchased) { // 이미 구매 한 제품 일 경우
            return null;
        }

        if(user.getMoney() - product.getPrice() < 0) // 계좌에 돈이 없을 경우
        {
            return null;
        }
        user.setMoney(user.getMoney() - product.getPrice());
        Purchases purchases = Purchases.builder().user(user).product(product).build();
        purchasesRepository.save(purchases);

        return purchases;


    }
}
