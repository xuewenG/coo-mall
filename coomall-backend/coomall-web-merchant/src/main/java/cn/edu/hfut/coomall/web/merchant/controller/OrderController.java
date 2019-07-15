package cn.edu.hfut.coomall.web.merchant.controller;

import cn.edu.hfut.coomall.config.CooMallConfig;
import cn.edu.hfut.coomall.config.annotation.LoginRequired;
import cn.edu.hfut.coomall.entity.Merchant;
import cn.edu.hfut.coomall.entity.Message;
import cn.edu.hfut.coomall.entity.Order;
import cn.edu.hfut.coomall.service.OrderService;
import cn.edu.hfut.coomall.util.ResultUtil;
import cn.edu.hfut.coomall.web.merchant.bean.GetByMerchantIDAndStateRespBean;
import cn.edu.hfut.coomall.web.merchant.bean.GetByOrderMerchantIDAndStateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 葛学文
 * @date 2019/7/15 16:08
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    CooMallConfig cooMallConfig;

    @SuppressWarnings("unchecked")
    @LoginRequired
    @PostMapping("/getByMerchantIDAndState")
    public Message getByOrderMerchantIDAndState(@RequestBody @Valid GetByOrderMerchantIDAndStateBean get,
                                                HttpSession httpSession) {

        Integer state = get.getState();
        Integer merchantID = get.getMerchantID();
        Integer currentPage = get.getCurrentPage();
        Integer limit = get.getLimit();
        Merchant merchant = (Merchant) httpSession.getAttribute(cooMallConfig.getIdentifier());
        if (!merchantID.equals(merchant.getID())) {
            return ResultUtil.error(4200, "不能查看此订单");
        }
        Map<String, Object> map = orderService.getOrderByMerchantIDAndState(merchantID, state, currentPage, limit);
        Integer totalPage = (Integer) map.get("totalPage");
        List<Order> orderList = (List<Order>) map.get("list");
        GetByMerchantIDAndStateRespBean getResp = new GetByMerchantIDAndStateRespBean();
        getResp.setOrderList(orderList);
        getResp.setTotalPage(totalPage);
        return ResultUtil.success(getResp);
    }
}
