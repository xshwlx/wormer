package com.jd.uwp.common.tree;

import com.jd.uwp.common.tree.builder.AbstractTreeBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 树形结构工具类
 *
 * @author xu shiheng
 */
public class TreeCreator implements ITreeCreator {

    @SuppressWarnings("serial")
    public static Map<Integer, String> builderRegiter = new HashMap<Integer, String>() {
        {
            put(TreeContants.JS_TREE_TYPE, "com.jd.uwp.common.tree.builder.JsTreeBuilder");
            put(TreeContants.TREE_DATA_HTML_UL, "com.jd.uwp.common.tree.builder.ULTreeBuilder");
        }
    };

    /**
     * 创建树形数据
     *
     * @param treeType   树类型
     * @param dataType   数据格式
     * @param treeSource 源数据
     * @return
     */
    public String generateTree(int treeType, int dataType, Collection<Object> treeSource) {
        String treeData = "";
        try {
            //初始化数据源构建对象
            TreeDSGenerator generator = new TreeDSGenerator();
            //设置源数据（包含父子关系的离散数据）
            generator.setTreeSource(treeSource);
            //初始化树形离散数据结构
            generator.initTreeData();
            //初始化树形构建对象
            AbstractTreeBuilder treeBuilder = (AbstractTreeBuilder) Class.forName(builderRegiter.get(treeType)).newInstance();
            //将构建的离散数据结构注入树形构建对象，树形构建对象自动生产树形数据
            treeBuilder.setDataList(generator.getTreeData());
//			switch (dataType) {
//			case TreeContants.TREE_DATA_JSON:
            //获取json格式树形数据
            treeData = treeBuilder.buileTree().toString();
//				break;
//			default:
//				break;
//			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return treeData;
    }

}
