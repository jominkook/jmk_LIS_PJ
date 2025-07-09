package com.yk.logistic.service.item;

import java.time.LocalDateTime;
import java.util.List;

import com.yk.logistic.domain.auction.Auction;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.repository.auction.AuctionRepository;
import org.springframework.stereotype.Service;
import com.yk.logistic.domain.category.Category;
import com.yk.logistic.domain.item.Item;
import com.yk.logistic.domain.item.ItemImage;
import com.yk.logistic.dto.item.request.SaveItemReqDto;
import com.yk.logistic.dto.item.response.ItemResDto;
import com.yk.logistic.repository.category.CategoryRepository;
import com.yk.logistic.repository.item.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ItemResDto registerItem(SaveItemReqDto reqDto) {
        Category category = categoryRepository.findById(reqDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID: " + reqDto.getCategoryId()));
        Member seller = reqDto.getSeller();

        Item item = Item.builder()
                .title(reqDto.getTitle())
                .origin(reqDto.getOrigin())
                .description(reqDto.getDescription())
                .price(reqDto.getPrice())
                .seller(seller)
                .category(category)
                .latitude(reqDto.getLatitude())
                .longitude(reqDto.getLongitude())
                .build();
        itemRepository.save(item);

        return transformDomain(item);
    }

    @Override
    public ItemResDto findItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));
        return transformDomain(item);
    }

    @Override
    public List<ItemResDto> findAllItems() {
        return itemRepository.findAll().stream()
                .map(this::transformDomain)
                .toList();
    }

    @Override
    public List<ItemResDto> findAuctionItems() {
        return itemRepository.findByAuctionIsNotNull().stream()
                .map(this::transformDomain)
                .toList();
    }

    @Override
    public void updateItem(Long itemId, SaveItemReqDto reqDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));

        Category category = categoryRepository.findById(reqDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID: " + reqDto.getCategoryId()));

        item.updateItem(
                reqDto.getTitle(),
                reqDto.getOrigin(),
                reqDto.getDescription(),
                reqDto.getPrice(),
                category,
                reqDto.getLatitude(),
                reqDto.getLongitude()
        );
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));
        itemRepository.delete(item);
    }

    private ItemResDto transformDomain(Item item) {
        String parentCategoryName = item.getCategory() != null && item.getCategory().getParent() != null
                ? item.getCategory().getParent().getName()
                : "없음";

        String imageUrl = item.getImages().isEmpty() ? null : item.getImages().get(0).getImageUrl();

        return new ItemResDto(
                item.getId(),
                item.getTitle(),
                item.getOrigin(),
                item.getPrice(),
                item.getSeller() != null ? item.getSeller().getId() : null,
                item.getSeller() != null ? item.getSeller().getName() : "Unknown Seller",
                item.getSeller() != null ? item.getSeller().getEmail() : "Unknown Email",
                item.getCategory() != null ? item.getCategory().getName() : "Unknown Category",
                parentCategoryName,
                imageUrl,
                item.getAuction() != null ? item.getAuction().getStartPrice() : 0,
                item.getAuction() != null ? item.getAuction().getEndTime().toString() : "N/A",
                item.getDescription(),
                item.getCategory() != null ? item.getCategory().getName() : "Unknown Category",
                item.getLatitude(),
                item.getLongitude()
        );
    }
}
