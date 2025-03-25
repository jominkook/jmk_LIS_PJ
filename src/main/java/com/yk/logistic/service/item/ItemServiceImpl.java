package com.yk.logistic.service.item;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.item.ItemStatus;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.member.MemberRole;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.repository.category.CategoryRepository;
import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.validation.ValidationCheck;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final ValidationCheck validationCheck;

    @Override
    public ItemResDto registerItem(SaveItemReqDto reqDto) {
        // 인증된 사용자 정보 가져오기
        Member seller = getAuthenticatedMember();

        // 판매자인지 검증
        if (!seller.getRole().equals(MemberRole.SELLER)) {
            throw new IllegalArgumentException("판매자 계정만이 물건을 등록할 수 있습니다.");
        }

        // 카테고리 조회
        Category category = categoryRepository.findById(reqDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID: " + reqDto.getCategoryId()));

        // DTO -> Entity 변환
        Item item = Item.builder()
                .title(reqDto.getName())
                .origin(reqDto.getOrigin()) // Address 객체로 처리
                .price(reqDto.getPrice())
                .status(ItemStatus.AVAILABLE) // 기본 상태: 판매 중
                .seller(seller)
                .category(category)
                .build();

        // 아이템 정보 저장
        itemRepository.save(item);

        // 저장된 Item -> DTO 변환
        return transformDomain(item);
    }

    @Override
    public ItemResDto findItem(Long itemId) {
        // 아이템 조회 및 검증
        Item findItem = validationCheck.getItem(itemRepository.findById(itemId));

        // Entity -> DTO 변환
        return transformDomain(findItem);
    }

    @Override
    public List<ItemResDto> findAllItems() {
        // 모든 아이템 조회
        return itemRepository.findAll().stream()
                .map(this::transformDomain) // Entity -> DTO 변환
                .toList();
    }

    @Override
    public void updateItem(Long itemId, SaveItemReqDto reqDto) {
        // 인증된 사용자 정보 가져오기
        Member seller = getAuthenticatedMember();

        // 아이템 조회 및 검증
        Item findItem = validationCheck.getItem(itemRepository.findById(itemId));

        // 수정 권한 확인
        if (!findItem.getSeller().getId().equals(seller.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 카테고리 조회
        Category category = categoryRepository.findById(reqDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID: " + reqDto.getCategoryId()));

        // 아이템 정보 업데이트
        findItem.updateItem(
                reqDto.getName(),
                reqDto.getOrigin(), // Address 객체로 처리
                reqDto.getPrice(),
                category
        );
    }

    @Override
    public void deleteItem(Long itemId) {
        // 인증된 사용자 정보 가져오기
        Member seller = getAuthenticatedMember();

        // 아이템 조회 및 검증
        Item findItem = validationCheck.getItem(itemRepository.findById(itemId));

        // 삭제 권한 확인
        if (!findItem.getSeller().getId().equals(seller.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        // 아이템 삭제
        itemRepository.delete(findItem);
    }

    private ItemResDto transformDomain(Item item) {
        String parentCategoryName = item.getCategory().getParent() != null
                ? item.getCategory().getParent().getName()
                : "없음"; // 부모 카테고리가 없을 경우 "없음"으로 설정

        return new ItemResDto(
                item.getId(),
                item.getTitle(),
                item.getOrigin(),
                item.getPrice(),
                item.getStatus().name(),
                item.getSeller().getName(),
                item.getCategory().getName(),
                parentCategoryName // 부모 카테고리 이름 추가
        );
    }

    // 인증된 사용자 정보를 가져오는 메서드
    private Member getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 인증된 사용자의 이메일
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("인증된 사용자를 찾을 수 없습니다."));
    }
}