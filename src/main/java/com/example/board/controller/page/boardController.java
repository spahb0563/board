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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class boardController {

    private final PostService postService;

    @GetMapping("{categoryType}")
    public String post(@LoginUser SessionUser user, @PathVariable String categoryType, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        if(user != null) {
            model.addAttribute("user", user);
        }

        if(CategoryType.contains(categoryType.toUpperCase())) {
            PaginationDto paginationDto = postService.readAllByCategoryType(CategoryType.valueOf(categoryType.toUpperCase()), pageable);
            model.addAttribute("category", CategoryType.valueOf(categoryType.toUpperCase()));
            model.addAttribute("postList", paginationDto.getData());
            model.addAttribute("page", paginationDto.getPagination());
            return "board";
        }

        return "index";
    }// post() end

    @GetMapping("{categoryType}/write")
    public String write(@LoginUser SessionUser user, @PathVariable String categoryType, Model model) {
        model.addAttribute("category", categoryType);
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
    }
}
