package com.yk.logistic.service.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.yk.logistic.domain.categoryItem.CategoryItem;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.domain.member.MemberRole;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
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
    private final ValidationCheck validationCheck;
    
    @Override
    public ItemResDto registerItem(SaveItemReqDto reqDto, Long memberId) {
        Member producer = validationCheck.getMember(memberRepository.findById(memberId));
        
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
        
        for (CategoryItem category : reqDto.getCategories()) {
            item.getCategories().add(category);
        }
        
        // Repository로 값 넘김
        Long savedItemId = itemRepository.save(item).getId();
        
        // DB에 저장된 item 엔티티 -> 저장된 정보를 View에 전달하기 위한 정보
        Item savedItem = validationCheck.getItem(itemRepository.findById(savedItemId));
        
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
    
    @Override
    public List<ItemResDto> findItemList(Long memberId){
    	return itemRepository.findBySellerId(memberId).stream()
                .map(item -> new ItemResDto(
                        item.getId(),
                        item.getName(),
                        item.getOrigin(),
                        item.getPrice(),
                        item.getStockQuantity(),
                        item.getCategories() 
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
                savedItem.getCategories()
        );
    }

}
