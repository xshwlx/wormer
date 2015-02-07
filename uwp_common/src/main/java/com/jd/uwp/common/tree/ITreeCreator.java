package com.jd.uwp.common.tree;

import java.util.Collection;

/**
 * 树形结构创建执行接口
 *
 * @author xu shiheng
 */
public interface ITreeCreator {
    /**
     * 创建树形数据
     *
     * @param treeType   树类型
     * @param dataType   数据格式
     * @param treeSource 源数据
     * @return
     */
    public String generateTree(int treeType, int dataType, Collection<Object> treeSource);
}
