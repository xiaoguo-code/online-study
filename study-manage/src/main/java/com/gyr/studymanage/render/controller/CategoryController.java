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
        log.info("查询条件：{}",GsonUtils.toJSON(conditionVO));
        PageUtils page =null;
        List<CategoryInfo> parentCategory = null;
        try {
             page = categoryService.getAllCategory(conditionVO);
        } catch (Exception e) {
            log.error("获取父级类别信息列表异常：", e);
        }
        log.info("page:{}",page.toString());
        //log.info("parentCategory:{}",parentCategory.toString());
        model.addAttribute("categoryList",page.getList());
        model.addAttribute("page",page);
        //model.addAttribute("parentCategory",parentCategory);
        model.addAttribute("parentId",conditionVO.getParentId());
        return new ModelAndView(!async?"category/category01":"category/category01::#tableBox",
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
//    @RequestMapping("/listParent")
////    @RequiresPermissions("generator:category:list")
//    public ModelAndView list(Model model) {
//        log.info("1、获取父级类别信息列表!");
//        List<CategoryInfo> parentCategoryList = null;
//        try {
//            //parentCategoryList = categoryService.getParentCategory();
//        } catch (Exception e) {
//            log.error("获取父级类别信息列表异常：", e);
//        }
//        model.addAttribute("parentCategoryList",parentCategoryList);
//        return new ModelAndView("category/category01","categoryModel01",model);
//    }

    /**
     * 获取一级类别信息列表
     * @param model
     * @return
     */
    @GetMapping("/getParentCategory")
    public ModelAndView getParentCategory(Integer type,
                                        Integer parentId,
                                        Model model) {
        log.info("1、获取一级类别信息列表!");
        List<CategoryInfo> parentCategory = null;
        try {
            parentCategory = categoryService.getParentCategory(0);
        } catch (Exception e) {
            log.error("获取父级类别信息列表异常：", e);
        }
        log.info("parentCategory:{}", GsonUtils.toJSON(parentCategory));
        model.addAttribute("parentCategory", parentCategory);

        if(type==2){
            model.addAttribute("parentId", parentId);
            return new ModelAndView( "category/category01::#add_parentId_select",
                    "categoryModel", model);
        }else{
            model.addAttribute("parentId", parentId);
            return new ModelAndView( "category/category01::#parentId_select",
                    "categoryModel", model);
        }



    }

    /**
     * 获取二级类别信息列表
     * @param model
     * @return
     */
    @GetMapping("/getchildCategory")
    public ModelAndView getchildCategory(Integer type,Integer parentId,
            Model model) {
        log.info("1、获取一级类别信息列表!");
        List<CategoryInfo> parentCategory = null;
        if (parentId==0){
            return new ModelAndView( "category/category01::#childId_select",
                    "categoryModel", model);
        }
        try {
            parentCategory = categoryService.getParentCategory(parentId);
        } catch (Exception e) {
            log.error("获取父级类别信息列表异常：", e);
        }
        log.info("parentCategory:{}", parentCategory.toString());
        model.addAttribute("parentCategory", parentCategory);
        if (type == 2) {
            model.addAttribute("parentId", parentId);
            return new ModelAndView( "category/category01::#add_childId_select",
                    "categoryModel", model);
        }else{
            return new ModelAndView( "category/category01::#childId_select",
                    "categoryModel", model);
        }

    }

    private  static Integer pre;
    private  static Integer count=0;
    /**
     * 获取上一层级类别信息列表
     * @param model
     * @return
     */
    @GetMapping("/getPrechildCategory")
    public ModelAndView getPrechildCategory(Integer type,Integer preParentId,
                                         Model model) {
        log.info("1、获取一级类别信息列表!");
        List<CategoryInfo> parentCategory = null;
        CategoryInfo categoryInfoById2 = null;

        try {
            if(count==0){
                categoryInfoById2 = categoryService.getCategoryInfoById(preParentId);
                log.info("类别：{}",categoryInfoById2.toString());
                parentCategory = categoryService.getParentCategory(Integer.getInteger(categoryInfoById2.getParentId()));
                pre = Integer.getInteger(categoryInfoById2.getParentId());
                count=1;
            }else{
                categoryInfoById2 = categoryService.getCategoryInfoById(pre);
                log.info("类别：{}",categoryInfoById2.toString());
                parentCategory = categoryService.getParentCategory(Integer.getInteger(categoryInfoById2.getParentId()));
                pre = Integer.getInteger(categoryInfoById2.getParentId());
                count=0;
            }

        } catch (Exception e) {
            log.error("获取父级类别信息列表异常：", e);
        }
        log.info("parentCategory:{}", parentCategory.toString());
        model.addAttribute("parentCategory", parentCategory);
        if (type == 2) {
            model.addAttribute("parentId", preParentId);
            return new ModelAndView( "category/category01::#add_childId_select",
                    "categoryModel", model);
        }else{
            model.addAttribute("parentId", pre);
            return new ModelAndView( "category/category01::#add_parentId_select",
                    "categoryModel", model);
        }

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
     * 新增和更新
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
