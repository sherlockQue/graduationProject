package com.a.modules.mis.controller;


import com.a.common.core.R;
import com.a.common.utils.PageUtils;
import com.a.modules.mis.service.StrelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("mis/RenAuthentication")
public class RenZhController {


    @Autowired
    private StrelationServiceImpl strelationService;

    /**
     * 一级列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("mis:appealitem:list")
    public R list(@RequestParam Map<String, Object> params) {

        String StGrade = "2020";  //今年的素拓
        Integer StTerm = 1;       //第一学期
        params.put("StGrade", StGrade);
        params.put("StTerm", StTerm);
        if ((String) params.get("stuId") != null) {
            PageUtils page = strelationService.renListOne(params);
            return R.ok().put("page", page);
        }
        return R.ok().put("page", null);
    }

    /**
     * 二级列表
     */
    @PostMapping("/list2")
    public R list2(@RequestParam Map<String, Object> params) {

        String StGrade = "2020";  //今年的素拓
        Integer StTerm = 1;       //第一学期
        params.put("StGrade", StGrade);
        params.put("StTerm", StTerm);
        if ((String) params.get("stuId") != null) {
            PageUtils page = strelationService.renListTwo(params);
            return R.ok().put("page", page);
        }

        return R.ok().put("page", null);
    }


    /**
     * 一级认证
     *
     * @param sign 判断是否通过
     */
    @PostMapping("/onePass")
    public R passOneItems(@RequestBody Long[] stIds, String sign) {

        String renId = "test";  //认证人的id
        boolean np = false;
        if ("true".equals(sign)) {
            np = true;
        }
        boolean flag = strelationService.AOnePass(stIds, np, renId);
        if (!flag) {
            return R.error("认证失败");
        }
        return R.ok();
    }

    /**
     * 二级认证
     */
    @PostMapping("/twoPass")
    public R passTwoItems(@RequestBody Long[] stIds, String sign) {
        String renId = "test";  //认证人的id
        boolean np = false;
        if ("true".equals(sign)) {
            np = true;
        }
        boolean flag = strelationService.ATwoPass(stIds, np, renId);
        if (!flag) {
            return R.error("认证失败");
        }
        return R.ok();

    }
}
