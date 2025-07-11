package com.yk.logistic.service.item;

import java.time.LocalDateTime;
import java.util.List;

import com.yk.logistic.domain.address.Address;
import com.yk.logistic.domain.auction.Auction;
import com.yk.logistic.domain.member.Member;
import com.yk.logistic.dto.auction.response.AuctionResDto;
import com.yk.logistic.dto.item.request.UpdateItemReqDto;
import com.yk.logistic.repository.auction.AuctionRepository;
import com.yk.logistic.service.file.S3FileService;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final S3FileService s3FileService;


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
        return itemRepository.findAuctionItems();
    }

    @Override
    public void updateItem(Long itemId, UpdateItemReqDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리 ID: " + dto.getCategoryId()));


        Address origin = new Address(dto.getStreet(), dto.getCity(), dto.getZipCode());


        // 기존 이미지 삭제(필요시)
        item.getImages().clear();

        // 새 이미지 저장
        if (dto.getImages() != null) {
            for (MultipartFile file : dto.getImages()) {
                if (file != null && !file.isEmpty()) {
                    String imageUrl = s3FileService.uploadFile(file);
                    ItemImage itemImage = new ItemImage(imageUrl, item);
                    item.getImages().add(itemImage);
                }
            }
        }

        item.updateItem(
                dto.getTitle(),
                origin,
                dto.getDescription(),
                dto.getPrice(),
                category,
                dto.getLatitude(),
                dto.getLongitude()
        );

    }

    @Override
    public void deleteItem(Long itemId,String sellerEmail) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));
        itemRepository.delete(item);
    }

    private ItemResDto transformDomain(Item item) {
        String parentCategoryName = item.getCategory() != null && item.getCategory().getParent() != null
                ? item.getCategory().getParent().getName()
                : "없음";

        String imageUrl = item.getImages().isEmpty() ? null : item.getImages().get(0).getImageUrl();

        // AuctionResDto 생성
        AuctionResDto auctionDto = null;
        String status = null;
        if (item.getAuction() != null) {
            status = item.getAuction().getStatus() != null ? item.getAuction().getStatus().name() : null;
            auctionDto = new AuctionResDto(
                    item.getAuction().getId(),
                    item.getId(),
                    item.getAuction().getStartPrice(),
                    item.getAuction().getCurrentPrice(),
                    item.getAuction().getWinner() != null ? item.getAuction().getWinner().getName() : null,
                    item.getAuction().getStartTime(),
                    item.getAuction().getEndTime(),
                    status
            );
        }

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
                item.getLongitude(),
                status, // status 추가
                auctionDto // 마지막에 auction DTO 추가
        );
    }
}
