package jpabook.jpa_bookshop.controller;

import jpabook.jpa_bookshop.domain.item.Book;
import jpabook.jpa_bookshop.domain.item.Item;
import jpabook.jpa_bookshop.dto.UpdateItemDTO;
import jpabook.jpa_bookshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private ItemService itemService;

    /*
    * 상품 등록
    * */
    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    /*
     * 상품 등록
     * */
    @PostMapping("/items/new")
    public String create(BookForm form){
        Book newBook = Book.createBook(form);
        itemService.saveItem(newBook);
        return "redirect:/";
    }

    /*
    * 상품목록
    * */
    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "itmes/itemList";
    }

    /*
    * 상품 수정
    * */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(Model model, @PathVariable Long itemId){
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setIsbn(item.getIsbn());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable Long itemId, @ModelAttribute BookForm form){
        UpdateItemDTO dto = new UpdateItemDTO();
        dto.setName(form.getName());
        dto.setPrice(form.getPrice());
        dto.setStockQuantity(form.getStockQuantity());

        itemService.updateItem(itemId,dto);

        return "redirect:/items"; //목록을 되돌아가기
    }
}
