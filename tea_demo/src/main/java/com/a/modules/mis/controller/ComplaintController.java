package com.a.modules.mis.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import com.a.common.core.ControllerAide;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.a.modules.mis.entity.Complaint;
import com.a.modules.mis.service.ComplaintServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;

/**
 *
 *
 */
@RestController
@RequestMapping("/mis/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintServiceImpl complaintService;

    /**
     * 列表
     * 分页查询,对应页面complaint.html
     * 未通过的项目
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:student")
    public R list(@RequestParam Map<String, Object> params) {

        Long stuId = ControllerAide.getUserId();
        params.put("stuId",stuId+"");
        PageUtils page = complaintService.AqueryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * @param params 分页查询,对应页面cpresult.html
     *               已经处理过的请求
     * @return
     */
    @PostMapping("/list2")
    @RequiresPermissions("sys:student")
    public R list2(@RequestParam Map<String, Object> params) {

        Long stuId = ControllerAide.getUserId();
        params.put("stuId",stuId+"");
        PageUtils page = complaintService.AqueryPage2(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{Id}")
    //@RequiresPermissions("mis:complaint:info")
    public R info(@PathVariable("Id") Long Id) {
        Complaint complaint = complaintService.getById(Id);
        return R.ok().put("entity", complaint);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:complaint:save")
    public R save(@RequestBody Complaint complaint) {
        complaintService.save(complaint);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:complaint:update")
    public R update(@RequestBody Complaint complaint) {
        complaintService.updateById(complaint);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:complaint:delete")
    public R delete(@RequestBody Long[] Ids) {
        complaintService.removeByIds(Arrays.asList(Ids));
        return R.ok();
    }

    /**
     * complaint的id
     * 提交申诉原因
     *
     * @return
     */

    @GetMapping("/appeal/{id}/{cpReason}")
    @RequiresPermissions("sys:student")
    public R addAppeal(@PathVariable("id") Long id, @PathVariable("cpReason") String cpReason) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setCpReason(cpReason);
        complaint.setCpStatus(1);
        complaint.setCpApplytime(localDateTime);
        complaintService.updateById(complaint);
        return R.ok();
    }

    /**
     * 需要处理的申诉列表
     *
     * @param params
     * @return
     */
    @PostMapping("/showList")
    @RequiresPermissions("sys:appeal")
    public R showList(@RequestParam Map<String, Object> params) {
        if (params.get("page") != null && params.get("limit") != null) {

            long current = (long) Integer.valueOf((String) params.get("page"));
            long size = (long) Integer.valueOf((String) params.get("limit"));
            if ((String) params.get("nian") != null && (String) params.get("xue") != null) {
                PageUtils page = complaintService.AqueryPage3(params, new Page<Complaint>(current, size));
                return R.ok().put("page", page);
            }
        }
        return R.ok().put("page", "NaNa");
    }

    @PostMapping("/passAppeal")
    @RequiresPermissions("sys:appeal")
    public R passAppeal(@RequestBody Long[] ids, String sign, String cpDealReason) {

        //负责人id
        Long cpChargeid = 3L;
        boolean np = false;
        if ("true".equals(sign)) {
            np = true;
        }
        Boolean flag = complaintService.isAppeal(ids, np, cpChargeid, cpDealReason);
        return R.ok();
    }


}
