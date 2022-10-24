package com.example.board.controller.page;

import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {


    @GetMapping("/users/mypage")
    public String myPage(@LoginUser SessionUser user, Model model) {

        return null;
    }

    @GetMapping("/users/mypage/mypost")
    public String myPost(@LoginUser SessionUser user, Model model) {

        return null;
    }
}
