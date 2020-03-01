package com.a.modules.mis.controller;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


import com.a.common.core.ControllerAide;
import com.a.modules.sys.entity.Dict;
import com.a.modules.mis.entity.Student;
import com.a.modules.sys.service.DictServiceImpl;
import com.a.modules.mis.service.StudentServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a.modules.mis.entity.Strelation;
import com.a.modules.mis.service.StrelationServiceImpl;
import com.a.common.utils.PageUtils;
import com.a.common.core.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 学生素拓提交
 */
@RestController
@RequestMapping("mis/strelation")
public class StrelationController {

    @Autowired
    private StrelationServiceImpl strelationService;
    @Autowired
    private DictServiceImpl dictService;
    @Autowired
    private StudentServiceImpl studentService;

    /**
     * 列表,当前登陆学生的信息
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:student")
    public R list(@RequestParam Map<String, Object> params) {

        Long stuId = ControllerAide.getUserId();
        params.put("stu_id",stuId+"");

        PageUtils page = strelationService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{stId}")
    public R info(@PathVariable("stId") Long stId) {
        Strelation strelation = strelationService.getById(stId);
        return R.ok().put("strelation", strelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:student")
    public R save(@RequestBody Strelation strelation, HttpServletRequest request) {

        Dict dict = dictService.getById(1);
        //今年的素拓
        String StGrade = dict.getGrade();
        //第一学期
        Integer StTerm = dict.getTerm();
        String stuId = ControllerAide.getUserId()+"";

        String pubPath = "F:/uploadImage";     //临时保存路径
        LocalDateTime nowtime = LocalDateTime.now(); //当前时间

        Student student = studentService.getById(stuId);

        //图片转存操作
        //   ipath = /university/2020/1/计算机学院/16级软工创新班/学号/xxxx.jpg
        String ipath = "/university/" + StGrade +
                "/" + StTerm +
                "/" + student.getStuCollege() +
                "/" + student.getStuId() +
                "/" + student.getStuClass() + strelation.getStImg();


        if (!strelation.getStImg().equals("00")) {

            //获得临时图片路径
            String imgpath = pubPath + strelation.getStImg();
            File source = new File(imgpath);
            File dest = new File(pubPath + ipath);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // copy files using java.nio.FileChannel
            try {
                copyFileWithFileChannel(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error!");
            }
            dest.deleteOnExit();

        }
        //------
        strelation.setStuId(stuId);
        strelation.setStGrade(StGrade);
        strelation.setStApplytime(nowtime);
        strelation.setStTerm(StTerm);
        strelation.setStImg(ipath);

        if (strelationService.sameStuId(strelation)) {
            strelationService.save(strelation);
            return R.ok();
        }

        return R.error("已存在对应项");

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:student")
    public R update(@RequestBody Strelation strelation) {

        strelationService.updateById(strelation);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:student")
    public R delete(@RequestBody Long[] stIds) {

        for (Long i : stIds)
            System.out.println(i);
        strelationService.removeByIds(Arrays.asList(stIds));
        return R.ok();
    }

    /*
     * 上传图片,临时保存
     * */
    @PostMapping("/uploadImg")
    public R uploadImg(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        String prefix = "";

        //保存上传
        OutputStream out = null;
        InputStream fileInput = null;
        try {
            if (file != null) {
                String originalName = file.getOriginalFilename();
                prefix = originalName.substring(originalName.lastIndexOf(".") + 1);

                //文件暂时存放地址
                String filepath = "F:/uploadImage/publicImg/";
                //学号
                int random = (int)(Math.random()*1000)+1000;
                String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
                String nowTime = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
                //相对地址
                String path = "/publicImg/" + nowTime +random+ "." + prefix;


                filepath = filepath + nowTime + random+"." + prefix;  //路径拼接
                // filepath.replace("\\","/");
                //path.replace("\\", "/");
                System.out.println("path :" + path);
                System.out.println("filepath :" + filepath);

                File fe = new File(filepath);

                if (!fe.getParentFile().exists()) {
                    fe.getParentFile().mkdirs();
                }
                file.transferTo(fe);

                return R.ok().put("filepath", path);

            }
        } catch (Exception e) {

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fileInput != null) {
                    fileInput.close();
                }

            } catch (IOException e) {
            }
        }
        return R.error();
    }

    public static void copyFileWithFileChannel(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            outputChannel.force(true);
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

}

