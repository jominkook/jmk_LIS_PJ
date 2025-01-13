package com.yk.logistic;

import com.yk.logistic.domain.*;
import com.yk.logistic.repository.JpaItemRepository;
import com.yk.logistic.repository.JpaMemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JpaItemRepository itemRepository;
    private final JpaMemberRepository memberRepository;

    public DataInitializer(JpaItemRepository itemRepository, JpaMemberRepository memberRepository) {
        this.itemRepository = itemRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Member 데이터 삽입
        Member member1 = Member.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .role(MemberRole.Manager)
                .build();
        Member member2 = Member.builder()
                .name("Jane Smith")
                .email("jane@example.com")
                .password("password123")
                .role(MemberRole.General)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // Address 데이터 삽입
        Address address1 = new Address("123 Main St", "Springfield", "62701");
        Address address2 = new Address("456 Elm St", "Shelbyville", "62702");

        // Item 데이터 삽입
        Item item1 = Item.builder()
                .name("Sample Item 1")
                .price(100)
                .stockQuantity(50)
                .origin(address1)
                .supplier(member1)
                .build();
        Item item2 = Item.builder()
                .name("Sample Item 2")
                .price(200)
                .stockQuantity(30)
                .origin(address2)
                .supplier(member2)
                .build();
        itemRepository.save(item1);
        itemRepository.save(item2);
    }
}