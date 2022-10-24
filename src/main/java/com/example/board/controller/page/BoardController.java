package com.example.board.controller.page;

import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.model.enumclass.CategoryType;
import com.example.board.model.network.PaginationDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final PostService postService;

    @GetMapping("/{categoryType}")
    public String postList(@LoginUser SessionUser user, @RequestParam(required = false) String keyword, @PathVariable String categoryType, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        if(user != null) {
            model.addAttribute("user", user);
        }
        PaginationDto paginationDto = null;
        CategoryType category = null;

        if(CategoryType.contains(categoryType.toUpperCase())) {
            category = CategoryType.valueOf(categoryType.toUpperCase());
            if(keyword == null) {
                paginationDto = postService.readAllByCategoryType(CategoryType.valueOf(categoryType.toUpperCase()), pageable);
            }else {
                paginationDto = postService.readAllByCategoryTypeAndKeyword(CategoryType.valueOf(categoryType.toUpperCase()), keyword, pageable);
            }
        }else if(categoryType.equals("all")) {
            if(keyword == null) {
                paginationDto = postService.readAll(pageable);
            }else {
                paginationDto = postService.readAllByKeyword(keyword, pageable);
            }
        }else {
            return "redirect:/";
        }

        model.addAttribute("dailyBestList", postService.readTop40OfDay(LocalDateTime.now().with(LocalTime.MIN)));
        model.addAttribute("weeklyBestList", postService.readTop40OfWeek(LocalDateTime.now().minusDays(6).with(LocalTime.MIN)));
        model.addAttribute("category", category);
        model.addAttribute("postList", paginationDto.getPostListResponseDto());
        model.addAttribute("page", paginationDto.getPagination());
        return "board";
    }//postList() end

    @GetMapping("post/write")
    public String write(@LoginUser SessionUser user, Model model) {
        model.addAttribute("categoryList", CategoryType.values());
        model.addAttribute("user", user);

        return "write";
    }// write() end

    @GetMapping("post/{id}")
    public String post(@LoginUser SessionUser user, @PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) {
        if(user != null) {
            model.addAttribute("user", user);
        }

        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        Long userId = user != null ? user.getId() : 0L;

        if(cookies != null) {
            for(Cookie c : cookies) {
                if(c.getName().equals(userId+"_visited")) {
                    cookie = c;
                    break;
                }
            }
        }

        if(cookie != null) {
            if(!cookie.getValue().contains("["+id+"]")) {
                model.addAttribute("post", postService.readAndPlusViewCount(id));
                cookie.setValue(cookie.getValue()+"["+id+"]");
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);
            }else {
                model.addAttribute("post", postService.read(id));
            }
        }else {
            model.addAttribute("post", postService.readAndPlusViewCount(id));
            Cookie newCookie = new Cookie(userId+"_visited", "["+id+"]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }

        return "post";
    }// post() end

    @GetMapping("post/{id}/edit")
    public String edit(@LoginUser SessionUser user, @PathVariable Long id, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("post", postService.readEdit(id));

        return "edit";
    }// edit() end
}
