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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/orders")
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

    @PostMapping("/orders")
    public String order(@RequestParam Long memberId, @RequestParam Long itemId,
                        @RequestParam int count){

        orderService.order(memberId,itemId,count);
        return "redirect:/orders";
    }


    /*
    *전체 주문 목록 todo
    * */
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        //model.addAttribute("orderSearch", orderSearch);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
