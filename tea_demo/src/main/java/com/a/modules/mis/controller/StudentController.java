package com.a.modules.mis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.a.common.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.modules.mis.entity.Student;
import com.a.modules.mis.service.StudentServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;



/**
 * 
 *
 */
@RestController
@RequestMapping("mis/student")
public class StudentController {
    @Autowired
    private StudentServiceImpl studentService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("mis:student:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = studentService.queryPage(params);
        System.out.println("----------------");
        return R.ok().put("page", page);
    }

    @GetMapping("/showstudent/{nian}/{xue}/{mc}")
    public  R showStudent(@PathVariable("nian")String nian,@PathVariable("xue")String xue,@PathVariable("mc")String mc){

        List <Student> student=studentService.AgetStudent(nian,xue,mc);
        return R.ok().put("student",student);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{stuId}")
   //@RequiresPermissions("mis:student:info")
    public R info(@PathVariable("stuId") String stuId){
		Student student = studentService.getById(stuId);
        return R.ok().put("student", student);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("mis:student:save")
    public R save(@RequestBody Student student){
        System.out.println(student.toString());
        studentService.ASave(student);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("mis:student:update")
    public R update(@RequestBody Student student){
		studentService.updateById(student);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("mis:student:delete")
    public R delete(@RequestBody String[] stuIds){
		studentService.removeByIds(Arrays.asList(stuIds));
        return R.ok();
    }

}
