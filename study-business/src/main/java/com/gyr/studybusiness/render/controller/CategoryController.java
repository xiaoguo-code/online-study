package com.gyr.studybusiness.render.controller;

import java.util.Arrays;
import java.util.Map;

import com.gyr.studybusiness.render.service.CategoryService;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-14 20:10:30
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("save")
    public void save(){
        CategoryInfo categoryInfo = new CategoryInfo();
        categoryInfo.setTitle("工科");
        boolean save = categoryService.save(categoryInfo);
        System.out.println(save);
    }
//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    @RequiresPermissions("generator:category:list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = categoryService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }
//
//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{categoryid}")
//    @RequiresPermissions("generator:category:info")
//    public R info(@PathVariable("categoryid") Integer categoryid){
//		CategoryEntity category = categoryService.getById(categoryid);
//
//        return R.ok().put("category", category);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("generator:category:save")
//    public R save(@RequestBody CategoryEntity category){
//		categoryService.save(category);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("generator:category:update")
//    public R update(@RequestBody CategoryEntity category){
//		categoryService.updateById(category);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("generator:category:delete")
//    public R delete(@RequestBody Integer[] categoryids){
//		categoryService.removeByIds(Arrays.asList(categoryids));
//
//        return R.ok();
//    }

}
