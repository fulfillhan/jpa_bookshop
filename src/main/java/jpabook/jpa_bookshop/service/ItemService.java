package jpabook.jpa_bookshop.service;

import jpabook.jpa_bookshop.domain.item.Item;
import jpabook.jpa_bookshop.dto.UpdateItemDTO;
import jpabook.jpa_bookshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, UpdateItemDTO dto) {
        //만약에 파라미터 필드가 많다면 dto를 생성해서 활용하기.
        Item findItem = itemRepository.findOne(itemId);//영속성에 있는 엔티티로 조회한다.
        findItem.setName(dto.getName());
        findItem.setPrice(dto.getPrice());
        findItem.setStockQuantity(dto.getStockQuantity());
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
