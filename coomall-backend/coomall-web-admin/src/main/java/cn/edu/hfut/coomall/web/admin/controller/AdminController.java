package cn.edu.hfut.coomall.web.admin.controller;

import cn.edu.hfut.coomall.config.CooMallConfig;
import cn.edu.hfut.coomall.config.annotation.LoginRequired;
import cn.edu.hfut.coomall.entity.Admin;
import cn.edu.hfut.coomall.entity.Message;
import cn.edu.hfut.coomall.service.AdminService;
import cn.edu.hfut.coomall.service.CustomService;
import cn.edu.hfut.coomall.service.exception.AdminNotFoundException;
import cn.edu.hfut.coomall.service.exception.BaseException;
import cn.edu.hfut.coomall.util.ResultUtil;
import cn.edu.hfut.coomall.web.admin.bean.EditPasswordRequestBean;
import cn.edu.hfut.coomall.web.admin.bean.GetAdminByIDReqBean;
import cn.edu.hfut.coomall.web.admin.bean.GetAdminByIDRespBean;
import cn.edu.hfut.coomall.web.admin.bean.LoginReqBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author 葛学文
 * @date 2019/7/14 15:30
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    CooMallConfig cooMallConfig;
    @Autowired
    AdminService adminService;
    @Autowired
    CustomService customService;

    @PostMapping("/login")
    public Message login(@RequestBody @Valid LoginReqBean loginReqBean,
                         HttpSession httpSession) {

        String phoneNumber = loginReqBean.getPhoneNumber();
        String password = loginReqBean.getPassword();

        Admin admin;
        try {
            admin = adminService.login(phoneNumber, password);
        } catch (AdminNotFoundException e) {
            return ResultUtil.error(4101, e.getMessage());
        } catch (BaseException e) {
            return ResultUtil.error(4102, e.getMessage());
        }

        httpSession.setAttribute(cooMallConfig.getIdentifier(), admin);
        admin.setPassword(null);
        return ResultUtil.success(admin);
    }

    @PostMapping("/logout")
    public Message login(HttpSession httpSession) {

        httpSession.removeAttribute(cooMallConfig.getIdentifier());
        return ResultUtil.success();
    }

    /**
     * @author 郑力煽
     * @date 2019/7/15
     */
    @LoginRequired
    @PostMapping("/getByID")
    public Message getAdminByID(@RequestBody @Valid
                                        GetAdminByIDReqBean getAdminByIDReqBean) {

        Integer adminID = getAdminByIDReqBean.getAdminID();

        Admin admin = adminService.getAdminByID(adminID);
        GetAdminByIDRespBean getAdminByIDRespBean = new GetAdminByIDRespBean();
        getAdminByIDRespBean.setAdmin(admin);

        return ResultUtil.success(getAdminByIDRespBean);
    }

    @PostMapping("/editPassword")
    public Message editPassword(@RequestBody @Valid EditPasswordRequestBean editPasswordRequestBean,
                                HttpSession httpSession) {

        String codeInSession = (String) httpSession.getAttribute("emailCode");
        String email = (String) httpSession.getAttribute("emailAddr");
        String smsCode = editPasswordRequestBean.getEmailCode();
        String newPassword = editPasswordRequestBean.getNewPassword();

        if (!smsCode.equals(codeInSession)) {
            return ResultUtil.error(4500, "验证码不正确");
        }

        adminService.editPassword(email, newPassword);
        return ResultUtil.success();
    }
}
