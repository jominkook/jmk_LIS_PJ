package com.yk.logistic.service.item;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.categoryItem.CategoryItem;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.member.MemberRole;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.repository.category.CategoryRepository;
import com.yk.logistic.repository.categoryItem.CategoryItemRepository;
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
    private final CategoryItemRepository categoryItemRepository;
    private final CategoryRepository categoryRepository;
    private final ValidationCheck validationCheck;
    
    
    @Override
    public ItemResDto registerItem(SaveItemReqDto reqDto) {
    	// 인증된 사용자 정보 가져오기
        Member producer = getAuthenticatedMember();
        
        // 관리자인지 검증
        if (!producer.getRole().equals(MemberRole.PRODUCER)) {
            throw new IllegalArgumentException("생산자 계정만이 물건을 등록가능.");
        }
        
        // dto -> entity 변환
        Item item = Item.builder()
                .name(reqDto.getName())
                .price(reqDto.getPrice())
                .stockQuantity(reqDto.getStockQuantity())
                .origin(reqDto.getOrigin())
                .seller(producer)
                .build();
        
        //아이템정보 먼저 저장 item id 필요하니
        itemRepository.save(item);
        
        // CategoryItem 객체의 ID를 올바르게 설정
        for (Long categoryId : reqDto.getCategories()) {
            if (categoryId == null) {
                throw new IllegalArgumentException("CategoryItem ID must not be null");
            }
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID: " + categoryId));
            CategoryItem categoryItem = new CategoryItem(category, item);
            item.getCategories().add(categoryItem);
        }

        // CategoryItem을 저장
        categoryItemRepository.saveAll(item.getCategories());

        // DB에 저장된 item 엔티티 -> 저장된 정보를 View에 전달하기 위한 정보
        Item savedItem = validationCheck.getItem(itemRepository.findById(item.getId()));

        // domain -> dto 변환
        ItemResDto resDto = transformDomain(savedItem);

        return resDto;
    }
    
    @Override
    public ItemResDto findItem(Long itemId) {
    	Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
    	
    	//domain -> dto 변환
    	ItemResDto resDto = transformDomain(findItem);
    	
    	return resDto;
    }
    
   /* //등록한 상품 -> 추후 구현 현재는 필요없음
    @Override
    public List<ItemResDto> findItemList(Long memberId){
    	return itemRepository.findBySellerId(memberId).stream()
                .map(item -> new ItemResDto(
                        item.getId(),
                        item.getName(),
                        item.getOrigin(),
                        item.getPrice(),
                        item.getStockQuantity(),
                        item.getCategories().stream().toList(),
                        item.getSeller().getName()
                ))
                .toList(); 
                
    	    			
    }*/
    
    //판매자 물건리스트 조회
    @Override
    public List<ItemResDto> findAllItems() {
        return itemRepository.findAll().stream()
                .map(item -> new ItemResDto(
                        item.getId(),
                        item.getName(),
                        item.getOrigin(),
                        item.getPrice(),
                        item.getStockQuantity(),
                        item.getCategories().stream().toList(),
                        item.getSeller().getName() // 판매자 이름 추가
                ))
                .toList();
    }
   
    
    @Override
    public void updateItem(Long itemId, Long memberId,SaveItemReqDto reqDto) {
    	Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
    	
    	if(!findItem.getSeller().getId().equals(memberId)) {
    		throw new IllegalArgumentException("수정이 허용되지 않는 멤버.");
    	}
    	findItem.updateItem(reqDto.getName(), reqDto.getOrigin(), reqDto.getPrice(), reqDto.getStockQuantity());
    }
    
    @Override
    public void deleteItem(Long itemId, Long memberId) {
    	Item findItem = validationCheck.getItem(itemRepository.findById(itemId));
    	
    	if(!findItem.getSeller().getId().equals(memberId)) {
    		throw new IllegalArgumentException("삭제가 허용되지 않는 멤버.");
    	}
    }
    //도메인을 DTO로 변환시키는 메소드
    private ItemResDto transformDomain(Item savedItem) {
    	return new ItemResDto(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getOrigin(),
                savedItem.getPrice(),
                savedItem.getStockQuantity(),
                savedItem.getCategories().stream().toList(),
                savedItem.getSeller().getName()
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