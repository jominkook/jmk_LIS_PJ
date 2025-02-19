package com.yk.logistic.controller.item;

import com.yk.logistic.dto.item.ItemResponse;
import com.yk.logistic.dto.item.SaveItemRequest;
import com.yk.logistic.service.item.ItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
  
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long itemId) {
        ItemResponse item = itemService.findItem(itemId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<ItemResponse> register(@RequestBody SaveItemRequest request, @RequestParam Long memberId) {
    	Long currnetId = 1L;
        ItemResponse response = itemService.register(request, currnetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}