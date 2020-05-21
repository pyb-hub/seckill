package com.example.seckill.controller;


import com.example.seckill.domain.Order;
import com.example.seckill.domain.User;
import com.example.seckill.rabbitmq.MQSend;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.prefix.GoodPrefix;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.impl.GoodService;
import com.example.seckill.service.impl.SeckillService;
import com.example.seckill.vo.GoodVo;
import com.example.seckill.vo.GoodsDetailVo;
import com.example.seckill.vo.MQMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/goods")
public class GoodController {

    @Autowired
    private GoodService goodService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SeckillService seckillService;

    /*页面缓存需要注入de对象*/
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /*消息队列*/
    @Autowired
    private MQSend send;

    // 售罄商品列表
    public static ConcurrentHashMap<Long, Boolean> productSoldOutMap = new ConcurrentHashMap<>();

    /*这个类启动的时候初始化方法：加载商品进入redis*/
    @PostConstruct
    public void init(){
        List<GoodVo> seckill_goods = goodService.listGoodsVo();
        for (GoodVo good:seckill_goods) {
            redisService.set(GoodPrefix.goodStock,good.getId()+"",good.getStockSeckill()+"");
            productSoldOutMap.put(good.getId(),false);
        }
    }

    /* @RequestMapping("/list")*//*@RequestParam:手机端可能把cookie放在RequestParam里面，兼容手机端*//*
    public String good_list(HttpServletResponse response, Model model,
                            @CookieValue(value = UserService.COOKI_NAME_TOKEN,required = false) String cookieToken,
                            @RequestParam(value = UserService.COOKI_NAME_TOKEN,required = false) String mobileCookieToken) {

        if (cookieToken == null && mobileCookieToken == null){
            return "login";
        }
        if (cookieToken!=null){
            *//*获取并更新分布式session里面的user*//*
            User user = userService.getByToken(cookieToken, response);
            model.addAttribute("user",user);
        }if (mobileCookieToken!=null){
            *//*兼容手机端*//*
     *//*获取并更新分布式session里面的user*//*
            User mobileUser = userService.getByToken(mobileCookieToken, response);
            model.addAttribute("user",mobileUser);
        }
        return "goods_list";
    }*/



    /*商品列表：加页面缓存，要自定义返回的内容*/
    @RequestMapping(value = "/list", produces = "text/html")/*,produces 表示返回就是html代码*/
    @ResponseBody/*config配置文件实现了在session取user的功能，代码简洁化*/
    public String good_list(Model model, User user, HttpServletRequest request,
                            HttpServletResponse response) {
        /*把分布式session中user放进model，前端可以获取*/
        model.addAttribute("user", user);
        /*查询商品列表*/
        //取缓存
        String html = redisService.get(GoodPrefix.goodList, "");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        /*缓存中不存在，自己渲染页面*/
        List<GoodVo> goods = goodService.listGoodsVo();
        model.addAttribute("goods", goods);

        /*跳转页面*/
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染,返回html，到页面；第一个参数为返回的页面地址，第二个为html文本
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        /*添加到缓存中*/
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodPrefix.goodList, "", html);
        }
        return html;
    }

    /*订单详情：页面缓存*/
    @RequestMapping(value = "/detail/{id}", produces = "text/html")/*config配置文件实现了在session取user的功能，代码简洁化*/
    @ResponseBody
    public String good_detail(Model model, User user, @PathVariable Long id, HttpServletRequest request,
                              HttpServletResponse response) {
        model.addAttribute("user", user);

        /*商品详情页*/
        //取缓存
        String html = redisService.get(GoodPrefix.goodDetail, "" + id);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        /*缓存中不存在，自己渲染页面*/
        GoodVo goodvo = goodService.getGoodsVoByGoodsId(id);
        model.addAttribute("good", goodvo);

        long startTime = goodvo.getStartDate().getTime();
        long endTime = goodvo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int status = -1;
        int remainSeconds = 0;
        if (now > endTime) {
            status = 2;
            remainSeconds = -1;
        } else if (now > startTime) {
            status = 1;
            remainSeconds = 0;
        } else if (now < startTime) {
            status = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("status", status);

        /*跳转页面*/
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染,返回html，到页面；第一个参数为返回的页面地址，第二个为html文本
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);

        /*添加到缓存中*/
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodPrefix.goodDetail, "" + id, html);
        }
        return html;
    }

    /*订单详情页：页面静态化，前后端分离，在客户端页面完成跳转，数据是在html页面加载完成后通过ajax和服务端取*/
    @RequestMapping(value = "/detail2/{goodId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(User user, @PathVariable("goodId") long goodId) {
        GoodVo goods = goodService.getGoodsVoByGoodsId(goodId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int status = -1;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀还没开始，倒计时
            status = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            status = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            status = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoodVo(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setStatus(status);
        return Result.success(vo);
    }

    /*秒杀功能实现*/
    @RequestMapping("/seckill")
    public String seckill(Model model, User user, @RequestParam("goodId") Long goodId) {

        if (user == null) {
            return "login";
        }

        /*秒杀*/
        try {
            Order order = seckillService.seckill(user, goodId);
            model.addAttribute("sucMsg", CodeMsg.SECKILL_SUC.getMsg());
            model.addAttribute("order", order);
            return "order_detail";
        } catch (Exception e) {
            //秒杀失败
            //e.printStackTrace();
            model.addAttribute("errMsg", e.getMessage());
            return "seckill_fail";
        }
    }

    /*隐藏秒杀接口*/
    @RequestMapping(value="/path")
    @ResponseBody
    public Result<String> getMiaoshaPath(User user, @RequestParam("goodId") long goodId) {
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        /*接口防刷，限流*/
        /*获得该段时间内访问的次数*/
        String time = redisService.get(GoodPrefix.access, user.getId() + "_" + goodId);
        if (StringUtils.isEmpty(time)){
            redisService.set(GoodPrefix.access, user.getId() + "_" + goodId,1+"");
        }else {
            int t = Integer.parseInt(time);
            if (t > 5){
                return Result.error(CodeMsg.ACCESS_LIMIT);
            }else {
                redisService.incr(GoodPrefix.access, user.getId() + "_" + goodId);
            }
        }


        String path  = seckillService.createPath(user, goodId);
        return Result.success(path);
    }

    /*秒杀处理：静态化*/
    @RequestMapping(value = "/seckill2/{path}", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> seckill2(User user, @PathVariable("path") String path, @RequestParam("goodId") long goodId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        /*判断路径是否正确*/
        Boolean p = seckillService.checkPath(user,goodId,path);
        if (!p){
            return Result.error(CodeMsg.PATH_ERROR);
        }

        /*加jvm内存标记，减少对redis的访问*/
        Boolean a = productSoldOutMap.get(goodId);
        if (a){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        /*redis预减库存*/
        Long decr = redisService.decr(GoodPrefix.goodStock, goodId + "");

        if (decr < 0){
            productSoldOutMap.put(goodId,true);
            redisService.incr(GoodPrefix.goodStock, goodId + "");
            return Result.error(CodeMsg.SECKILL_FAIL);
        }

        /*入消息队列,异步下单*/
        MQMsgVo msg = new MQMsgVo();
        msg.setUser(user);
        msg.setGoodId(goodId);
        send.send(msg);

        return Result.success("稍等，排队中~");

    }

    /*异步下单后的查看秒杀处理结果
    * orderId：成功
     * -1：秒杀失败
     * 0： 排队中*/
    @RequestMapping(value = "/result/{goodId}")
    @ResponseBody
    public Result<Long> result(User user, @PathVariable("goodId") long goodId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Long result = seckillService.getResult(user, goodId);
        return Result.success(result);

    }
}
