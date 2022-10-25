package com.example.board.controller.page;

import com.example.board.model.network.PaginationDto;
import com.example.board.security.LoginUser;
import com.example.board.security.dto.SessionUser;
import com.example.board.service.NotificationService;
import com.example.board.service.OpinionService;
import com.example.board.service.PostService;
import com.example.board.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users/mypage")
public class MyPageController {

    private final UsersService usersService;

    private final PostService postService;

    private final OpinionService opinionService;

    private final NotificationService notificationService;

    @GetMapping(value = {"", "/profile"})
    public String myPage(@LoginUser SessionUser user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("profile", usersService.read(user.getId()));

        return "profile";
    }

    @GetMapping("/mypost")
    public String myPost(@LoginUser SessionUser user, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PaginationDto paginationDto = postService.readAllByUserId(user.getId(), pageable);
        model.addAttribute("user", user);
        model.addAttribute("postList", paginationDto.getData());
        model.addAttribute("page", paginationDto.getPagination());
        return "mypost";
    }

    @GetMapping("/myopinion")
    public String myOpinion(@LoginUser SessionUser user, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PaginationDto paginationDto = opinionService.readAllByUserId(user.getId(), pageable);
        model.addAttribute("user", user);
        model.addAttribute("opinionList", paginationDto.getData());
        model.addAttribute("page", paginationDto.getPagination());

        return "myopinion";
    }

    @GetMapping("/notification")
    public String notification(@LoginUser SessionUser user, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PaginationDto paginationDto = notificationService.readAllByUserId(user.getId(), pageable);
        model.addAttribute("user", user);
        model.addAttribute("notificationList", paginationDto.getData());
        model.addAttribute("page", paginationDto.getPagination());

        return "notification";
    }
}
