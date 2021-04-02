package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studycommon.dao.CategoryMapper;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CategoryConditionVO;
import com.gyr.studymanage.render.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
     * @return
     */
    @Override
    public PageUtils getParentCategory(CategoryConditionVO conditionVO) {
        if(conditionVO.getPageIndex()==null){
            conditionVO.setPageIndex(1);
        }
        if(conditionVO.getPageSize()==null){
            conditionVO.setPageSize(10);
        }
        //RowBounds rowBounds = new RowBounds(conditionVO.getPageSize()*conditionVO.getPageIndex()+1,conditionVO.getPageSize());
        IPage<CategoryInfo> page = new Page<>(conditionVO.getPageIndex(),conditionVO.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parentId","0");
        if(StringUtils.isNotBlank(conditionVO.getTitle())){
            wrapper.eq("title",conditionVO.getTitle());
        }
        IPage iPage = categoryMapper.selectPage(page, wrapper);
        return new PageUtils(iPage);
    }

    /**
     * 获取第一层级类别信息
     *
     * @return
     */
    @Override
    public List<CategoryInfo> getParentCategory() {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parentId","0");
        List list = categoryMapper.selectList(wrapper);
        return list;
    }

    /**
     * 根据父类别id获取所有层级类别信息
     * @param conditionVO
     * @return
     */
    @Override
    public PageUtils getChildCategoryByParentId(CategoryConditionVO conditionVO) {
        if(conditionVO.getPageIndex()==null){
            conditionVO.setPageIndex(1);
        }
        if(conditionVO.getPageSize()==null){
            conditionVO.setPageSize(10);
        }
        //RowBounds rowBounds = new RowBounds(conditionVO.getPageSize()*conditionVO.getPageIndex()+1,conditionVO.getPageSize());
        IPage<CategoryInfo> page = new Page<>(conditionVO.getPageIndex(),conditionVO.getPageSize());
        QueryWrapper wrapper = new QueryWrapper();
        if(conditionVO.getParentId()!=null){
            wrapper.eq("parentId",Integer.toString(conditionVO.getParentId()));
        }
        if(StringUtils.isNotBlank(conditionVO.getTitle())){
            wrapper.eq("title",conditionVO.getTitle());
        }
        IPage iPage = categoryMapper.selectPage(page, wrapper);
//        List<CategoryInfo> treeList = new ArrayList<>();
//        //查处所有的数据
//        for (CategoryInfo parent : (List<CategoryInfo>)iPage.getRecords()) {
//            //找到parentId为零的节点，即一级目录，
//            if (parent.getParentId().equals(Integer.toString(conditionVO.getParentId()))) {
//                treeList.add(findChildren(parent, (List<CategoryInfo>)iPage.getRecords()));
//            }
//        }
        return new PageUtils(iPage);
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
     * 新增类别信息
     * @param categoryInfo
     * @return
     */
    @Override
    public boolean save(CategoryInfo categoryInfo) {
        int insert = categoryMapper.insert(categoryInfo);
        return insert>0;
    }
    /**
     * 更新类别信息
     * @param categoryInfo
     * @return
     */
    @Override
    public boolean update(CategoryInfo categoryInfo) {
        if(Optional.ofNullable(categoryInfo).isPresent()){
            int i = categoryMapper.updateById(categoryInfo);
            return i>0;
        }
        return false;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param categoryIds
     * @return
     */
    @Override
    public boolean remove(String categoryIds) {
        if(StringUtils.isNotBlank(categoryIds)){
            String[] idList = categoryIds.split(",");
            int i = categoryMapper.deleteBatchIds(Arrays.asList(idList));
            return i>0;
        }
        return false;
    }
}
