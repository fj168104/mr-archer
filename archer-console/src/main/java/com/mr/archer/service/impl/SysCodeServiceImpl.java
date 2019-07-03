package com.mr.archer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.SysCodeMapper;
import com.mr.archer.entity.SysCode;
import com.mr.archer.service.SysCodeService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 代码表 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-06-16
 */
@Service
public class SysCodeServiceImpl extends ServiceImpl<SysCodeMapper, SysCode> implements SysCodeService {

  public JSONObject queryCodeList(JSONArray aCodeList, JSONArray aTreeList) {
    JSONObject resultJson = new JSONObject();
    if (aCodeList != null) {
      for (int i = 0; i < aCodeList.size(); i++) {
        Object oCode = aCodeList.get(i);
        String sCurId = oCode.toString();
        Wrapper<SysCode> queryParams = new EntityWrapper<>();
        queryParams.eq("id", sCurId);
        queryParams.orderBy("sortno");
        queryParams.setSqlSelect("opt,name");
        List<SysCode> curCodeList = this.selectList(queryParams);
        JSONArray aSelectCode = new JSONArray();
        for (SysCode code : curCodeList) {
          JSONObject oCodeJson = new JSONObject();
          oCodeJson.put("value", code.getOpt());
          oCodeJson.put("label", code.getName());
          aSelectCode.add(oCodeJson);
        }
        resultJson.put(sCurId, aSelectCode);
      }
    }

    if (aTreeList != null) {
      for (int j = 0; j < aTreeList.size(); j++) {
        String sTreeId = aTreeList.get(j).toString();
        Wrapper<SysCode> queryParams = new EntityWrapper<>();
        queryParams.eq("id", sTreeId);
        queryParams.orderBy("sortno");
        queryParams.setSqlSelect("opt,name");
        List<SysCode> curTreeList = this.selectList(queryParams);
        JSONArray aTree = getTreeList(curTreeList);
        resultJson.put(sTreeId, aTree);
      }
    }

    return resultJson;
  }

  private JSONArray getTreeList(List<SysCode> treeList) {
    JSONArray aTreeList = new JSONArray();
    int iLength = treeList.get(0).getOpt().length();

    Iterator<SysCode> treeIt = treeList.iterator();
    // 按长度分类代码列表，L1:[],L2:[]....Ln:[]
    JSONObject oTreeLevelList = new JSONObject();
    // 最大层级
    int iMaxLevel = 0;
    while (treeIt.hasNext()) {
      SysCode curCode = treeIt.next();
      String sOpt = curCode.getOpt();
      int iCurOptLength = sOpt.length();
      int iLevel = iCurOptLength / iLength;
      if (iLevel > iMaxLevel)
        iMaxLevel = iLevel;
      String sLevelName = "L" + iLevel;
      if (oTreeLevelList.containsKey(sLevelName)) {
        oTreeLevelList.getJSONArray(sLevelName).add(curCode);
      } else {
        JSONArray aTemp = new JSONArray();
        oTreeLevelList.put(sLevelName, aTemp);
        aTemp.add(curCode);
      }
    }

    if (iMaxLevel > 0) {
      findLevel(aTreeList, oTreeLevelList, "", iMaxLevel, 1);
    }

    return aTreeList;
  }

  private void findLevel(JSONArray aParentList, JSONObject oTreeLevelList, String sParentOpt, int iMaxLength, int iCurLevel) {
    if (iCurLevel > iMaxLength)
      return;
    JSONArray aLevelList = oTreeLevelList.getJSONArray("L" + iCurLevel);
    Iterator<Object> treeIt = aLevelList.iterator();
    while (treeIt.hasNext()) {
      SysCode curCode = (SysCode) treeIt.next();
      String sOpt = curCode.getOpt();

      if ("".equals(sParentOpt) || sOpt.startsWith(sParentOpt)) {
        String sName = curCode.getName();
        JSONObject oCodeJson = new JSONObject();
        oCodeJson.put("value", sOpt);
        oCodeJson.put("label", sName);
        JSONArray aTemp = new JSONArray();
        oCodeJson.put("children", aTemp);
        findLevel(aTemp, oTreeLevelList, sOpt, iMaxLength, iCurLevel + 1);

        if (aTemp.size() == 0) {
          oCodeJson.remove("children");
        }
        aParentList.add(oCodeJson);
      } else {
        return;
      }
      treeIt.remove();
    }
  }
}
