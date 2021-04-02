package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.GsonUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.CategoryConditionVO;
import com.gyr.studycommon.vo.CategoryVO;
import com.gyr.studymanage.render.service.CategoryService;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author guoyr
 */
@Controller
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 根据条件查询父类别
     * @param async
     * @param conditionVO
     * @param model
     * @return
     */
    @GetMapping("/listParent")
    public ModelAndView toCategory01(@RequestParam(value = "async",required = false) boolean async,
                                     CategoryConditionVO conditionVO,
                                     Model model){
        log.info("1、获取父级类别信息列表!");
        PageUtils page =null;
        try {
             page = categoryService.getParentCategory(conditionVO);

        } catch (Exception e) {
            log.error("获取父级类别信息列表异常：", e);
        }
        log.info("page:{}",page.toString());
        model.addAttribute("categoryList",page.getList());
        model.addAttribute("page",page);
        return new ModelAndView(!async?"category/category01":"category/category01::#tableBox",
                "categoryModel",model);
    }



    @GetMapping("/listChild2")
    public ModelAndView toCategory02(@RequestParam(value = "async",required = false) boolean async,
                               CategoryConditionVO conditionVO,
                               Model model){
        log.info("1、listChild2根据父级类别id:{}，查询类别信息!", conditionVO.getParentId());
        PageUtils page =null;
        List<CategoryInfo> parentCategory = null;
        try {
            page = categoryService.getChildCategoryByParentId(conditionVO);
             parentCategory = categoryService.getParentCategory();
        } catch (Exception e) {
            log.error("根据父级类别id：{}查询异常：{}", conditionVO.getParentId(), e);
        }
        log.info("page:{}",page.toString());
        model.addAttribute("categoryList",page.getList());
        model.addAttribute("parentCategory",parentCategory);
        model.addAttribute("parentId",conditionVO.getParentId());
        model.addAttribute("page",page);
        return new ModelAndView(!async?"category/category02":"category/category02::#tableBox",
                "categoryModel",model);
    }

    @GetMapping("/listChild3")
    public ModelAndView toCategory03(@RequestParam(value = "async",required = false) boolean async,
                                     CategoryConditionVO conditionVO,
                                     Model model){
        log.info("1、listChild3根据父级类别id:{}，查询类别信息!", conditionVO.getParentId());
        PageUtils page =null;
        try {
            page = categoryService.getChildCategoryByParentId(conditionVO);
        } catch (Exception e) {
            log.error("根据父级类别id：{}查询异常：{}", conditionVO.getParentId(), e);
        }
        log.info("page:{}",page.toString());
        model.addAttribute("categoryList",page.getList());
        model.addAttribute("page",page);
        return new ModelAndView(!async?"category/category03":"category/category03::#tableBox",
                "categoryModel",model);
    }

//    /**
//     * 获取所有类别信息列表
//     */
//    @RequestMapping("/listAll")
////    @RequiresPermissions("generator:category:list")
//    public R listAll() {
//        log.info("1、获取所有类别信息列表!");
//        List<CategoryInfo> categoryList = null;
//        try {
//            categoryList = categoryService.findAllCategory();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error();
//        }
//        return R.ok().put("categoryList", categoryList);
//    }

    /**
     * 获取父级类别信息列表
     */
    @RequestMapping("/listParent")
//    @RequiresPermissions("generator:category:list")
    public ModelAndView list(Model model) {
        log.info("1、获取父级类别信息列表!");
        List<CategoryInfo> parentCategoryList = null;
        try {
            //parentCategoryList = categoryService.getParentCategory();
        } catch (Exception e) {
            log.error("获取父级类别信息列表异常：", e);
        }
        model.addAttribute("parentCategoryList",parentCategoryList);
        return new ModelAndView("category/category01","categoryModel01",model);
    }

    /**
     * 根据父id查询子类别
     */
    @RequestMapping("/listChild/{parentId}")
//    @RequiresPermissions("generator:category:list")
    public R listChildByParentId(@PathVariable("parentId") Integer parentId) {
        log.info("1、根据父级类别id:{}，查询类别信息!", parentId);
        List<CategoryInfo> childCategoryList = null;
        try {
//            childCategoryList = categoryService.getChildCategoryByParentId(parentId);
        } catch (Exception e) {
            log.error("根据父级类别id：{}查询异常：{}", parentId, e);
            return R.error();
        }
        return R.ok().put("childCategoryList", childCategoryList);
    }

    /**
     * 根据id获取类别信息
     */
    @GetMapping("/info/{categoryId}")
//    @RequiresPermissions("generator:category:info")
    public R info(@PathVariable("categoryId") Integer categoryId) {
        log.info("1、根据该id:{}，查询类别信息!", categoryId);
        CategoryInfo categoryInfo = null;
        try {
            categoryInfo = categoryService.getCategoryInfoById(categoryId);
        } catch (Exception e) {
            log.error("根据id:{}获取类别信息异常：{}", categoryId, e);
            return R.error();
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(categoryInfo, categoryVO);
        log.info("根据类别id：{}查询成功！", categoryId);
        return R.ok().put("categoryVO", categoryVO);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    //@RequiresPermissions("generator:category:save")
    public R save(@RequestBody CategoryInfo categoryInfo) {
        log.info("1、类别-save-参数：{}", GsonUtils.toJSON(categoryInfo));
        try {
            categoryInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            categoryInfo.setIsDelete("0");
            categoryInfo.setParentId("0");
            boolean flag = categoryService.save(categoryInfo);
            log.info("类别新增成功！-{}", flag);
        } catch (Exception e) {
            log.error("类别新增异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ResponseBody
//    @RequiresPermissions("generator:category:update")
    public R update(CategoryInfo categoryInfo) {
        log.info("1、类别-update-参数：{}", GsonUtils.toJSON(categoryInfo));
        try {
            boolean flag = false;
            if(categoryInfo.getCategoryId()==null){
                categoryInfo.setCreateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
                categoryInfo.setIsDelete("0");
                 flag = categoryService.save(categoryInfo);
                log.info("类别新增成功！-{}", flag);
            }else{
                categoryInfo.setUpdateTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
                flag = categoryService.update(categoryInfo);
                log.info("类别更新成功！-{}", flag);
            }

        } catch (Exception e) {
            log.error("类别更新异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R delete(@RequestParam String categoryIds) {
        log.info("类别删除categoryIds：{}" , categoryIds);
        try {
            //Map<String, String> stringObjectMap = GsonUtils.toMaps(categoryIds);
            boolean flag = categoryService.remove(categoryIds);
            log.info("类别删除成功！-{}", flag);
        } catch (Exception e) {
            log.error("类别除异常：", e);
            return R.error();
        }
        return R.ok();
    }


}
