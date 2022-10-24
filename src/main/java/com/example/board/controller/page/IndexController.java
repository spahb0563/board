package com.example.board.controller.page;


import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.service.CategoryService;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.PipedOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor //생성자를 통한 의존성 주입
@Controller
public class IndexController {

    private final CategoryService categoryService;

    private final PostService postService;

    @GetMapping("/")
    public String index(@LoginUser SessionUser user, Model model) {
        model.addAttribute("categoryList", categoryService.readAllCategoryRecentPost());
        model.addAttribute("dailyBestList", postService.readTop40OfDay(LocalDateTime.now().with(LocalTime.MIN)));
        model.addAttribute("weeklyBestList", postService.readTop40OfWeek(LocalDateTime.now().minusDays(6).with(LocalTime.MIN)));
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }
}
