package com.yk.logistic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.categoryItem.CategoryItem;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.member.MemberRole;
import com.yk.logistic.repository.category.CategoryRepository;
import com.yk.logistic.repository.categoryItem.CategoryItemRepository;
import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;
    //private final StockRepository stockRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 샘플 멤버 데이터 생성
        Member producer = Member.builder()
                .email("jominkook@example.com")
                .password(bCryptPasswordEncoder.encode("password1234"))
                .name("조민국")
                .role(MemberRole.PRODUCER)
                .address(new Address("서울특별시 종로구 세종대로 110", "서울특별시", "03172"))
                .build();
        memberRepository.save(producer);

        Member seller = Member.builder()
                .email("jominsoo@example.com")
                .password(bCryptPasswordEncoder.encode("password1234"))
                .name("조민수")
                .role(MemberRole.SELLER)
                .address(new Address("서울특별시 강남구 테헤란로 212", "서울특별시", "06221"))
                .build();
        memberRepository.save(seller);

        // 샘플 카테고리 데이터 생성
        Category electronics = new Category("전자제품", null);
        categoryRepository.save(electronics);

        Category computers = new Category("컴퓨터", electronics);
        categoryRepository.save(computers);

    }
}