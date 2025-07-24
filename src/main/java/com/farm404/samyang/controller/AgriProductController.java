package com.farm404.samyang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AgriProductController {

    @GetMapping("/agri-products")
    public String getAgriProducts(Model model) {
        List<AgriProduct> products = new ArrayList<>();
        try {
            products.add(new AgriProduct("사과", "1,000원", "빨갛고 달콤한 사과"));
            products.add(new AgriProduct("바나나", "1,500원", "노랗고 부드러운 바나나"));
            products.add(new AgriProduct("당근", "800원", "주황색과 아삭한 당근"));
            products.add(new AgriProduct("오이", "700원", "초록색과 시원한 오이"));
            products.add(new AgriProduct("포도", "2,000원", "보라색과 달콤한 포도"));
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "농산물 목록을 불러오는 중 오류가 발생했습니다.");
        }
        return "agriProducts";
    }

    public static class AgriProduct {
        private final String name;
        private final String price;
        private final String description;

        public AgriProduct(String name, String price, String description) {
            this.name = name;
            this.price = price;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }
}