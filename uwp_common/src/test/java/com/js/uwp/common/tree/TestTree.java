package com.js.uwp.common.tree;

import com.jd.uwp.common.tree.TreeContants;
import com.jd.uwp.common.tree.TreeCreator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: xushiheng
 * Date: 14-12-30
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class TestTree {
    public static void main(String[] args) {

        com.jd.uwp.common.tree.TestTree vo4 = new com.jd.uwp.common.tree.TestTree();
        vo4.setNodeId("0");
        vo4.setNodeName("0");
        vo4.setNodeDescription("0");

        com.jd.uwp.common.tree.TestTree vo0 = new com.jd.uwp.common.tree.TestTree();
        vo0.setNodeId("1");
        vo0.setNodeName("1");
        vo0.setNodeDescription("1");

        com.jd.uwp.common.tree.TestTree vo1 = new com.jd.uwp.common.tree.TestTree();
        vo1.setNodeId("2");
        vo1.setNodeName("2");
        vo1.setNodeDescription("2");
        vo1.setNodeParentId("1");

        com.jd.uwp.common.tree.TestTree vo5 = new com.jd.uwp.common.tree.TestTree();
        vo5.setNodeId("5");
        vo5.setNodeName("5");
        vo5.setNodeDescription("5");
        vo5.setNodeParentId("1");

        com.jd.uwp.common.tree.TestTree vo6 = new com.jd.uwp.common.tree.TestTree();
        vo6.setNodeId("6");
        vo6.setNodeName("6");
        vo6.setNodeDescription("6");
        vo6.setNodeParentId("5");

        com.jd.uwp.common.tree.TestTree vo2 = new com.jd.uwp.common.tree.TestTree();
        vo2.setNodeId("3");
        vo2.setNodeName("3");
        vo2.setNodeDescription("3");
        vo2.setNodeParentId("0");

        com.jd.uwp.common.tree.TestTree vo3 = new com.jd.uwp.common.tree.TestTree();
        vo3.setNodeId("4");
        vo3.setNodeName("4");
        vo3.setNodeDescription("4");
        vo3.setNodeParentId("0");


        Collection<Object> treeSource = new ArrayList<Object>();
        treeSource.add(vo4);
        treeSource.add(vo0);
        treeSource.add(vo1);
        treeSource.add(vo2);
        treeSource.add(vo3);
        treeSource.add(vo5);
        treeSource.add(vo6);


        TreeCreator treeTools = new TreeCreator();
//		System.out.println(treeTools.generateTree(TreeContants.JS_TREE_TYPE,TreeContants.TREE_DATA_JSON, treeSource));
        System.out.println(treeTools.generateTree(TreeContants.TREE_DATA_HTML_UL, TreeContants.TREE_DATA_HTML_UL, treeSource));

    }
}
