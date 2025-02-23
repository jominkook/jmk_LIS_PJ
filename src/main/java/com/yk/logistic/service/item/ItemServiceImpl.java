package com.yk.logistic.service.item;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yk.logistic.domain.category.CategoryItem;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.member.MemberRole;
import com.yk.logistic.dto.item.ItemResponse;
import com.yk.logistic.dto.item.SaveItemRequest;
import com.yk.logistic.repository.item.ItemRepository;
import com.yk.logistic.repository.member.MemberRepository;
import com.yk.logistic.service.validation.ValidationCheck;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ValidationCheck validationCheck;

    @Override
    public ItemResponse register(SaveItemRequest request, Long memberId) {
        Member member = validationCheck.getMember(memberRepository.findById(memberId));
        
        // 회원인지 검증
        if (!member.getRole().equals(MemberRole.Manager)) {
            throw new IllegalArgumentException("관리자만 물건을 등록할 수 있습니다.");
        }
        
        // dto -> entity
        Item item = Item.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .origin(request.getOrigin())
                .supplier(member)
                .build();
        
        for (CategoryItem category : request.getCategories()) {
            item.getCategories().add(category);
        }
        
        // Repository로 전송
        Long savedItemId = itemRepository.save(item).getId();
        
        
        // DB에 저장된 item 엔터티 -> view로 뿌리기
        Item savedItem = validationCheck.getItem(itemRepository.findById(savedItemId));
        
        // domain -> dto
        ItemResponse response = transformDomain(savedItem);
        
        return response;
    }
    
    @Override
    public ItemResponse findItem(Long itemId) {
        // repository로 id 전송
        Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
        
        // domain -> dto
        ItemResponse response = transformDomain(findItem);
        
        return response;
    }
    
    @Override
    public void updateItem(Long itemId, Long memberId, SaveItemRequest request) {
        Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
        
        if (!findItem.getSupplier().getId().equals(memberId)) {
            throw new IllegalArgumentException("수정이 허용되지 않는 멤버입니다.");
        }
        
        findItem.updateItem(request.getName(), request.getOrigin(), request.getPrice(), request.getStockQuantity());
    }

    @Override
    public void deleteItem(Long itemId, Long memberId) {
        Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
        
        if (!findItem.getSupplier().getId().equals(memberId)) {
            throw new IllegalArgumentException("삭제가 허용되지 않는 멤버입니다.");
        }
        
        itemRepository.delete(findItem);
    }
    
    @Override
    public List<ItemResponse> findItemList(Long memberId) {
    	return null;

    }
    
    // 도메인을 DTO로
    private ItemResponse transformDomain(Item savedItem) {
        ItemResponse response = new ItemResponse();
        response.setId(savedItem.getId());
        response.setName(savedItem.getName());
        response.setPrice(savedItem.getPrice());
        response.setStockQuantity(savedItem.getStockQuantity());
        response.setOrigin(savedItem.getOrigin());
        response.setCategories(savedItem.getCategories());
        response.setMemberId(savedItem.getSupplier().getId()); // 추가된 부분
        return response;
    }
}