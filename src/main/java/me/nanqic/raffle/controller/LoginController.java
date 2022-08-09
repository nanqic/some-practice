package me.nanqic.raffle.controller;

import me.nanqic.raffle.model.vo.CodeMsg;
import me.nanqic.raffle.model.vo.LoginVO;
import me.nanqic.raffle.model.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class LoginController {
    @Autowired
    private HttpSession session;
    @Value("${admin.user}")
    private String user;
    @Value("${admin.pass}")
    private String pass;

    @PostMapping("/login")
    Result doLogin(@RequestBody LoginVO vo) {
        if (vo.getUsername().equals(user) && vo.getPassword().equals(pass)) {
            session.setAttribute("user", vo.getUsername());
            return Result.ok();
        }
        return Result.error(CodeMsg.REQUEST_ERROR);

    }

    @GetMapping("/logout")
    void doLogout() {
        session.removeAttribute("user");
    }
}
