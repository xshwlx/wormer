package com.jd.uwp.webcontroller.serviceimpl;

import com.jd.uwp.core.db.service.BaseService;
import com.jd.uwp.core.db.service.BaseServiceImpl;
import com.jd.uwp.core.dbconnction.DBConnection;
import com.jd.uwp.core.ioc.ServiceFactory;
import com.jd.uwp.webcontroller.menu.Module;
import com.jd.uwp.webcontroller.menu.Node;
import com.jd.uwp.webcontroller.po.Uwp_sys_menu;
import com.jd.uwp.webcontroller.service.IPluginService;

import java.util.*;

/**
 * 插件加载实现
 * User: xushiheng
 * Date: 15-1-20
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
public class PluginServiceImpl extends BaseServiceImpl implements IPluginService{


    private Module[] modules = null;

    private Map<String, Module> pluginModules = new LinkedHashMap<String, Module>();

    private Map<String,Node> pluginNodes = new HashMap<String, Node>();

    private Map<String, String> nodeActionMap = new HashMap<String, String>();
    @Override
    public void loadMenus() throws Exception {
        loadPluginModulesDB();
    }


    private void loadPluginModulesDB() {
        BaseService ifs = (BaseService) ServiceFactory.getModules("baseService");
        ifs.setConn(DBConnection.getConn(),false);
        Map<String,String> conditonMap = new HashMap<String, String>();
        conditonMap.put("parent_menuID", "0");
        List<Uwp_sys_menu> voList = ( List<Uwp_sys_menu>)ifs.queryObjectList(conditonMap, Uwp_sys_menu.class," order by menu_order desc");
        for(Uwp_sys_menu vo : voList){
            Module module = new Module();
            module.setId(vo.getSys_menuID()+"");
            module.setName(vo.getMenu_name());
            module.setDisplayOrder(vo.getMenu_order());
            module.setModule(vo.getMenu_model());
            module.setIcon(vo.getIcon());
            module.setModelPath(vo.getMenu_model());
            String parentCode = vo.getMenu_model();
            //二级菜单
            conditonMap.clear();
            conditonMap.put("parent_menuID", vo.getSys_menuID()+"");
            List<Uwp_sys_menu> childs = (List<Uwp_sys_menu>)ifs.queryObjectList(conditonMap, Uwp_sys_menu.class);

            Node[] nodes = new Node[childs.size()];
            int i = 0;
            for(Uwp_sys_menu menu : childs){
                Node node = new Node();
                node.setId(menu.getSys_menuID()+"");
                node.setName(menu.getMenu_name());
                node.setDisplayOrder(menu.getMenu_order());
                node.setModule(menu.getMenu_model());
                node.setIcon(menu.getIcon());
                node.setModelPath(parentCode+"/"+menu.getMenu_model());
                nodes[i] = node;
                i ++;
            }

            module.setNodes(nodes);
            pluginModules.put(module.getId(), module);
        }
        if(pluginModules.size()>0){
            modules = pluginModules.values().toArray(new Module[0]);
            Arrays.sort(modules);
        }else{

        }
        ifs.closeConnection();
    }

    @Override
    public Module[] getModules(){
        Module[] curModules = null;
        curModules = new Module[modules.length];
        for (int i = 0; i < curModules.length; i++) {
            try {
                curModules[i] = modules[i].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return curModules;
    }
}
