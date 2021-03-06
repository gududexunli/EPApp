package com.bl.ep.controller;

import com.alibaba.fastjson.JSON;
import com.bl.ep.constant.Resource;
import com.bl.ep.constant.ResultModel;
import com.bl.ep.model.UserCollect;
import com.bl.ep.param.CollectParam;
import com.bl.ep.param.HomeParam;
import com.bl.ep.param.PageParam;
import com.bl.ep.param.UserParam;
import com.bl.ep.pojo.Home;
import com.bl.ep.service.UserService;
import com.bl.ep.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Resource resource;

    /**
     * 登录
     * @param param 接收用户名和密码
     * @return 1成功  2失败
     */
    @PostMapping(value = "/login", params = {"username","password"})
    public ResultModel login(UserParam param){
        int result = userService.signIn(param);
        logger.info("login UserParam = {}",JSON.toJSONString(param));
        return ResultModel.response(result);
    }

    /**
     * 获取首页新闻列表
     * @param homeParam 输入查询框查询条件 （查询"环保" 检索出所有title带环保字段的）
     * @param pageParam 分页条件 默认 获取第1页 查询999条数据
     * @return 新闻列表
     */
    @PostMapping("/home/list")
    public ResultModel homeList(HomeParam homeParam, PageParam pageParam){
        List<Home> homes = userService.homeList(homeParam, pageParam);
        logger.info("homeList HomeParam = {};PageParam = {}",
                JSON.toJSONString(homeParam), JSON.toJSONString(pageParam));
        return ResultModel.response(homes);
    }

    /**
     * 添加收藏（商品）
     * @param userId 用户id
     * @param categoryId 收藏的商品种类id
     * @return true （收藏成功）
     */
    @PostMapping(value = "/merchandize/collect/{userId}/{categoryId}")
    public ResultModel merchandizeCollect(@PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId){
        userService.addMerchandizeCollect(userId, categoryId);
        logger.info("merchandizeCollect userId = {};categoryId = {}",
                userId, categoryId);
        return ResultModel.response(true);
    }

    /**
     * 添加收藏(新闻)
     * @param userId 用户Id
     * @param homeId 首页信息id
     * @return true （收藏成功）
     */
    @PostMapping(value = "/home/collect/{userId}/{homeId}")
    public ResultModel homeCollect(@PathVariable("userId") Integer userId, @PathVariable("homeId") Integer homeId){
        logger.info("homeCollect userId = {};homeId = {}",
                userId, homeId);
        userService.addHomeCollect(userId, homeId);
        return ResultModel.response(true);
    }

    /**
     * 获取收藏内容
     * @param param collectType： 1=新闻 、2=商品 、默认=所有
     * @return 收藏信息列表
     */
    @PostMapping(value = "/user/collect")
    public ResultModel userCollect(CollectParam param){
        logger.info("userCollect param = {}",
                JSON.toJSONString(param));
        List<UserCollect> collects = userService.getUserCollects(param);
        return ResultModel.response(collects);
    }
}
