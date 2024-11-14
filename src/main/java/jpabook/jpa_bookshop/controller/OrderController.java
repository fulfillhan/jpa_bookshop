package jpabook.jpa_bookshop.controller;

import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderSearch;
import jpabook.jpa_bookshop.domain.item.Item;
import jpabook.jpa_bookshop.service.ItemService;
import jpabook.jpa_bookshop.service.MemberService;
import jpabook.jpa_bookshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);
        return "order/orderForm";
    }

    /*
    * 상품 주문
    * */

    @PostMapping("/order")
    public String order(@RequestParam Long memberId, @RequestParam Long itemId,
                        @RequestParam int count){

        orderService.order(memberId,itemId,count);
        return "redirect:/order/orderList";
    }


    /*
    *전체 주문 목록
    * */
    @GetMapping("/orders")
    public String orderList(@ModelAttribute OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
}
