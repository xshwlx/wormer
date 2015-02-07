package com.jd.uwp.common.tree;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: xushiheng
 * Date: 14-12-16
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public class TestTree extends BaseTree {
    public static void main(String[] args) {

        TestTree vo4 = new TestTree();
        vo4.setNodeId("0");
        vo4.setNodeName("0");
        vo4.setNodeDescription("0");

        TestTree vo0 = new TestTree();
        vo0.setNodeId("1");
        vo0.setNodeName("1");
        vo0.setNodeDescription("1");

        TestTree vo1 = new TestTree();
        vo1.setNodeId("2");
        vo1.setNodeName("2");
        vo1.setNodeDescription("2");
        vo1.setNodeParentId("1");

        TestTree vo5 = new TestTree();
        vo5.setNodeId("5");
        vo5.setNodeName("5");
        vo5.setNodeDescription("5");
        vo5.setNodeParentId("1");

        TestTree vo6 = new TestTree();
        vo6.setNodeId("6");
        vo6.setNodeName("6");
        vo6.setNodeDescription("6");
        vo6.setNodeParentId("5");

        TestTree vo2 = new TestTree();
        vo2.setNodeId("3");
        vo2.setNodeName("3");
        vo2.setNodeDescription("3");
        vo2.setNodeParentId("0");

        TestTree vo3 = new TestTree();
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
