package com.jsjf.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.TreeNode;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysMenuVo;
import com.jsjf.model.system.SysRoleVo;
import com.jsjf.service.system.SysRoleVoService;

@Controller
@RequestMapping("/role")
public class SysRoleController{
	
	@Autowired
	private SysRoleVoService sysRoleVoService;

	/**
	 * @Description: 跳转到角色列表
	 */
	@RequestMapping("/toList")
	public String toList() {
		return "system/sys/role/sysRoleList";
	}
	
	/**
	 * @Description 显示角色列表数据
	 */
	@RequestMapping(value= "/roleList")
	@ResponseBody
	public PageInfo roleList(SysRoleVo roleVo,Integer page,Integer rows) {
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = PageInfo.CRM_DEFAULT_PAGE_SIZE;
		}
		PageInfo pi = new PageInfo(page,rows);
		BaseResult result = sysRoleVoService.getRoleList(roleVo, pi);
		
		return (PageInfo)result.getMap().get("page");
	}
	
	/**
	 * @param roleKy 指定选中的角色ID
	 * @Description 显示角色下拉框数据
	 */
	@RequestMapping(value= "/roleSelect")
	@ResponseBody
	public JSONArray roleSelect(Integer roleKy) {
		SysRoleVo role = new SysRoleVo();
		role.setStatus(Short.parseShort(1 + ""));
		List<SysRoleVo> result = sysRoleVoService.queryRole(role);
		JSONArray array = JSONArray.fromObject(result);
		for(int i=0;i<array.size();i++){
			if(array.getJSONObject(i).get("roleKy").equals(roleKy)){
				array.getJSONObject(i).put("selected", "true");
			}
		}
		return array;
	}
	
	/**
	 * @Description 检查角色名称和角色编号
	 */
	@RequestMapping("/isExistNameOrCode")
	@ResponseBody
	public Map<String, String> isExistNameOrCode(String roleName,String roleCode) {
		SysRoleVo sysRoleVoByName = new SysRoleVo();
		sysRoleVoByName.setRoleName(roleName);
		SysRoleVo sysRoleVoByCode = new SysRoleVo();
		sysRoleVoByCode.setRoleCode(roleCode);
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		List<SysRoleVo> sysRoleVo1 = sysRoleVoService.queryRole(sysRoleVoByName);
		List<SysRoleVo> sysRoleVo2 = sysRoleVoService.queryRole(sysRoleVoByCode);

		if (sysRoleVo1.size() != 0 || sysRoleVo2.size() != 0) {
			if (sysRoleVo1.size() != 0) {
				jsonMap.put("message", "nameerror");
			}
			if (sysRoleVo2.size() != 0) {
				jsonMap.put("message", "codeerror");
			}
		} else {
			jsonMap.put("message", "ok");
		}
		return jsonMap;
	}
	
	/**
	 * @Description 添加角色和权限
	 */
	@RequestMapping("/tosysRoleAdd")
	@ResponseBody
	public Map<String, String> tosysRoleAdd(SysRoleVo roleVo,String[] selectedRights) {
		Map<String, String> jsonMap = new HashMap<String, String>(); 
		try {
			sysRoleVoService.insertRoleMenuList(roleVo, selectedRights);
			jsonMap.put("message", "ok");
		} catch (Exception e) {
			jsonMap.put("message", "error");
			e.printStackTrace();
		}
		selectedRights = null;
		return jsonMap;
	}
	
	/**
	 * @Description 修改角色状态
	 */
	@RequestMapping("/updateRoleStatus")
	@ResponseBody
	public BaseResult updateRoleStatus(SysRoleVo roleVo) {
		BaseResult br = sysRoleVoService.updateRoleStatus(roleVo);
		return br;
	}
	
	/**
	 * @Description 跳转角色修改页面
	 */
	@RequestMapping("/toUpdateRole")
	public String toUpdateRole(Long roleKy,Map<String,Object> model) {
		SysRoleVo  sysRoleVo = sysRoleVoService.queryRoleByKey(roleKy);
		try {
			model.put("itsSystem",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("itsSystem")));
			model.put("sysRoleVo", sysRoleVo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/system/sys/role/sysRoleModify";
	}
	
	/**
	 * @Description 修改角色和权限
	 */
	@RequestMapping("/updateRoleMenu")
	@ResponseBody
	public Map<String, String> updateRoleMenu(SysRoleVo sysRoleVo,String[] selectedRights) {
		Map<String, String> jsonMap = new HashMap<String, String>(); 
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("isAdmin", sysRoleVo.getRoleKy());
			map.put("selectedRights", selectedRights);
			int flag = sysRoleVoService.isRoleMenu(map);
			
			if (flag > 0) {
				if(sysRoleVo.getRoleKy() != null && sysRoleVo.getRoleKy() == 1 ){
					jsonMap.put("message", "管理员不能分配业务操作菜单");
				}else{
					jsonMap.put("message", "业务操员不能分配管理员菜单");
				}
				return jsonMap;
			}
			
			sysRoleVoService.updateRoleMenu(sysRoleVo, selectedRights);
			jsonMap.put("message", "ok");
		} catch (Exception e) {
			jsonMap.put("message", "error");
			e.printStackTrace();
		}
		return jsonMap;
	}
	
	/**
	 * @Description 根据角色主键查询菜单ID
	 */
	@RequestMapping("/queryRoleMenuByRoleKy")
	@ResponseBody
	public String[] queryRoleMenuByRoleKy(Long roleKy) {
		String[] str = sysRoleVoService.queryRoleMenuByRoleKy(roleKy);
	
		return str;
	}
	
	/**
	 * @Description 显示角色列表数据
	 */
	@RequestMapping("/menuTree")
	@ResponseBody
    public List<TreeNode> getAllMenuTreeNodes(){  
        List<SysMenuVo> sysMenuVoList = sysRoleVoService.getMenuTree(0); //该方法可不用理会,这是内部得到数据的方法,通过父ID,得到下面的数据节点集合  
        List<TreeNode> treeNodes = null;  
        if(!Utils.isEmptyList(sysMenuVoList)){  
            treeNodes = new ArrayList<TreeNode>();  
            for (SysMenuVo sysMenuVo : sysMenuVoList) {  
                TreeNode treeNode = getTreeNode(sysMenuVo); //分别得到每个节点下的子节点集合  
                treeNodes.add(treeNode);  
            }  
        }  
        return treeNodes;  
    }  
      
    /** 
     * @Description 递归模块树 
     * @param SysRoleVo 
     * @return TreeNode
     */  
    public TreeNode getTreeNode(SysMenuVo sysMenuVo){  
        TreeNode treeNode = new TreeNode();  
        treeNode.setId(String.valueOf(sysMenuVo.getMenuKy()));
        treeNode.setIconCls(sysMenuVo.getImage());  
        treeNode.setText(sysMenuVo.getName());  
          
        List<SysMenuVo> sysMenuVoList = sysRoleVoService.getMenuTree(sysMenuVo.getMenuKy().intValue());   //得到子节点集合  
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();  
        for (SysMenuVo m : sysMenuVoList) {  
            TreeNode tn = getTreeNode(m);  //循环子节点,得到子节点下的孙节点集合,调用本身,可一直向下递归,直到modules为空  
            treeNodes.add(tn);  
        }  
        treeNode.setChildren(treeNodes);   //添加封装好数据的子节点集合  
        return treeNode;  
    }  


    
}
