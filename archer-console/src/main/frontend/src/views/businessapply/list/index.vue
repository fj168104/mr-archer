<template>
  <el-container>
    <el-container>
      <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
        <!-- <el-tree :highlight-current="true" :expand-on-click-node="false" :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree> -->
        <el-menu
          default-active="10001010"
          class="el-menu-vertical-demo"
          default-openeds="['1000','2000']"
          @select="selectMenu">
          <el-submenu index="1000">
            <template slot="title">
              <span>当前任务</span>
            </template>
            <el-menu-item index="10001010">待处理<span style="color:red;padding-left: 10px;" v-if="processcount > 0">[{{processcount}}]</span></el-menu-item>
            <el-menu-item index="10001020">已退回<span style="color:red;padding-left: 10px;" v-if="returncount > 0">[{{returncount}}]</span></el-menu-item>
          </el-submenu>
          <el-submenu index="2000">
            <template slot="title">
              <span>已完成任务</span>
            </template>
            <el-menu-item index="20001010">审批通过</el-menu-item>
            <el-menu-item index="20001020">审批拒绝</el-menu-item>
            <el-menu-item index="20001030">已取消</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <!-- <business-type-list :type="curnodeid" :typename="curnodelabel"></business-type-list> -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
//import BusinessTypeList from '@/views/config/businesstype/treeview/businesstypelist'
import { queryCodeList } from '@/api/syscode'
import { when } from 'q';

export default {
  name: 'BusinessApplyTreeView',
  //components: { BusinessTypeList },
  directives: { waves },
  filters: {},
  props: {
  },
  data() {
    return {
      treedata:[],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      processcount: 2,
      returncount: 1,
      curnodeid: '10001010',
      curnodelabel: '待处理'
    }
  },
  created() {
    // this.getTreeData()
  },
  methods: {
    getTreeData() {
      queryCodeList({treelist:['BusinessTypeFolder']}).then(response => {
        this.treedata = response.data.BusinessTypeFolder
      }).catch(() => {
        this.$message.info("获取数据失败！")
      })
    },
    handleNodeClick(data){
      this.curnodeid = data.value;
      this.curnodelabel = data.label;
    },
    selectMenu(idx){
      if (idx !== '1000' && idx !== '2000') {
        this.curnodeid = idx;
      }
      switch(idx) {
        case '10001010':
          break;
        case '10001020':
          break;
        case '20001010':
          break;
        case '20001020':
          break;
        case '20001030':
          break;
        default:
          break;
      }
    }
  }
}
</script>