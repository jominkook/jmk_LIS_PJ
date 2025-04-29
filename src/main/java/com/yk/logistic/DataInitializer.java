//package com.yk.logistic;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.yk.logistic.domain.address.Address;
//import com.yk.logistic.domain.category.Category;
//import com.yk.logistic.domain.member.Member;
//import com.yk.logistic.domain.member.MemberRole;
//import com.yk.logistic.repository.category.CategoryRepository;
//import com.yk.logistic.repository.member.MemberRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final MemberRepository memberRepository;
//    private final CategoryRepository categoryRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 샘플 멤버 데이터 생성
//        Member producer = Member.builder()
//                .email("jominkook@example.com")
//                .password(bCryptPasswordEncoder.encode("password1234"))
//                .name("조민국")
//                .role(MemberRole.SELLER)
//                .address(new Address("서울특별시 종로구 세종대로 110", "서울특별시", "03172"))
//                .build();
//        memberRepository.save(producer);
//
//        Member seller = Member.builder()
//                .email("jominsoo@example.com")
//                .password(bCryptPasswordEncoder.encode("password1234"))
//                .name("조민수")
//                .role(MemberRole.SELLER)
//                .address(new Address("서울특별시 강남구 테헤란로 212", "서울특별시", "06221"))
//                .build();
//        memberRepository.save(seller);
//
//        // 샘플 카테고리 데이터 생성
//        Category electronics = Category.builder()
//                .name("전자제품")
//                .build();
//        categoryRepository.save(electronics);
//
//        Category computers = Category.builder()
//                .name("컴퓨터")
//                .parent(electronics) // 부모 카테고리 설정
//                .build();
//        categoryRepository.save(computers);
//
//        Category smartphones = Category.builder()
//                .name("스마트폰")
//                .parent(electronics) // 부모 카테고리 설정
//                .build();
//        categoryRepository.save(smartphones);
//
//        Category furniture = Category.builder()
//                .name("가구")
//                .build();
//        categoryRepository.save(furniture);
//
//        Category chairs = Category.builder()
//                .name("의자")
//                .parent(furniture) // 부모 카테고리 설정
//                .build();
//        categoryRepository.save(chairs);
//
//        Category tables = Category.builder()
//                .name("테이블")
//                .parent(furniture) // 부모 카테고리 설정
//                .build();
//        categoryRepository.save(tables);
//
//        System.out.println("샘플 데이터가 성공적으로 저장되었습니다.");
//    }
//}
