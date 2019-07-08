package com.mr.archer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mr.archer.entity.FilelistTree;
import com.mr.archer.dao.FilelistTreeMapper;
import com.mr.archer.entity.SysCode;
import com.mr.archer.service.FilelistTreeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 资料清单树图 服务实现类
 * </p>
 *
 * @author jiang.feng
 * @since 2019-07-05
 */
@Service
public class FilelistTreeServiceImpl extends ServiceImpl<FilelistTreeMapper, FilelistTree> implements FilelistTreeService {

  public JSONArray getTreeList(List<FilelistTree> treeList) {
    JSONArray aTreeList = new JSONArray();
    int iLength = treeList.get(0).getSortno().length();

    Iterator<FilelistTree> treeIt = treeList.iterator();
    // 按长度分类代码列表，L1:[],L2:[]....Ln:[]
    JSONObject oTreeLevelList = new JSONObject();
    // 最大层级
    int iMaxLevel = 0;
    while (treeIt.hasNext()) {
      FilelistTree curCode = treeIt.next();
      String sSortno = curCode.getSortno();
      int iCurOptLength = sSortno.length();
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

  private void findLevel(JSONArray aParentList, JSONObject oTreeLevelList, String sParentSortno, int iMaxLength, int iCurLevel) {
    if (iCurLevel > iMaxLength)
      return;
    JSONArray aLevelList = oTreeLevelList.getJSONArray("L" + iCurLevel);
    Iterator<Object> treeIt = aLevelList.iterator();
    while (treeIt.hasNext()) {
      FilelistTree curCode = (FilelistTree) treeIt.next();
      String sSortno = curCode.getSortno();

      if ("".equals(sParentSortno) || sSortno.startsWith(sParentSortno)) {
        String sName = curCode.getName();
        JSONObject oCodeJson = new JSONObject();
        oCodeJson.put("value", curCode.getId());
        oCodeJson.put("label", sName);
        oCodeJson.put("isinuse", curCode.getIsinuse());
        oCodeJson.put("sortno", sSortno);
        JSONArray aTemp = new JSONArray();
        oCodeJson.put("children", aTemp);
        findLevel(aTemp, oTreeLevelList, sSortno, iMaxLength, iCurLevel + 1);

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
