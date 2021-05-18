package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studybusiness.render.service.CategoryService;
import com.gyr.studycommon.dao.CategoryMapper;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CategoryConditionVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 递归，寻找子节点
     * @param parent
     * @param listGoodsCategory
     * @return
     */
    private static CategoryInfo findChildren(CategoryInfo parent, List<CategoryInfo> listGoodsCategory) {
        for (CategoryInfo child : listGoodsCategory) {
            if (Integer.toString(parent.getCategoryId()).equals(child.getParentId())) {
                if (parent.getChildrenList() == null) {
                    parent.setChildrenList(new ArrayList<CategoryInfo>());
                }
                parent.getChildrenList().add(findChildren(child, listGoodsCategory));
            }
        }
        return parent;
    }


    /**
     * 获取所有层级类别信息
     * @return
     */
    @Override
    public List<CategoryInfo> findAllCategory() {
        List<CategoryInfo> treeList = new ArrayList<>();
        //查处所有的数据
        List<CategoryInfo> listCategoryInfo=categoryMapper.selectList(null);
        for (CategoryInfo parent : listCategoryInfo) {
            //找到parentId为零的节点，即一级目录，
            if ("0".equals(parent.getParentId())) {
                treeList.add(findChildren(parent, listCategoryInfo));
            }
        }
        return treeList;
    }


    /**
     * 获取第一层级类别信息
     *
     * @return
     */
    @Override
    public List<CategoryInfo> getParentCategory(Integer parentId) {

        QueryWrapper wrapper = new QueryWrapper();
        if(parentId!=null){
            wrapper.eq("parentId",parentId);

        }
        List list = categoryMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<CategoryInfo> getCategoryByCondition(CategoryConditionVO conditionVO) {
        QueryWrapper wrapper = new QueryWrapper();
        if(conditionVO.getParentId()!=null){
            wrapper.eq("parentId",conditionVO.getParentId());
        }
        if(conditionVO.getCategoryId()!=null){
            wrapper.eq("categoryId",conditionVO.getCategoryId());
        }
        if(StringUtils.isNotBlank(conditionVO.getTitle())){
            wrapper.like("title",conditionVO.getTitle());
        }
        List list = categoryMapper.selectList(wrapper);
        return list;
    }

    /**
     * 获取上一层级类别信息
     *
     * @param parentId
     * @return
     */
    @Override
    public List<CategoryInfo> getPreParentCategory(Integer parentId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(parentId!=null){
            wrapper.eq("categoryId",parentId);

        }
        List list = categoryMapper.selectList(wrapper);
        return list;
    }

    /**
     * 根据类别id获取类别信息
     * @param categoryId
     * @return
     */
    @Override
    public CategoryInfo getCategoryInfoById(Integer categoryId) {
        if(categoryId!=null){
            return categoryMapper.selectById(categoryId);
        }
        return null;
    }

    /**
     * 返回id字符串   “1,2,3"
     *
     * @param categoryId
     * @return
     */
    @Override
    public String getParentChildIdToStr(String categoryId) {
        CategoryInfo three = getCategoryInfoById(Integer.parseInt(categoryId));
        CategoryInfo two = getCategoryInfoById(Integer.parseInt(three.getParentId()));
        CategoryInfo one = getCategoryInfoById(Integer.parseInt(two.getParentId()));
        return one.getCategoryId()+","+two.getCategoryId()+","+three.getCategoryId();
    }

}
