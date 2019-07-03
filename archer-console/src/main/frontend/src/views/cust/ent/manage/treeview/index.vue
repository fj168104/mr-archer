<template>
  <el-container>
    <el-container>
      <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
        <!-- <el-tree :highlight-current="true" :expand-on-click-node="false" :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree> -->
        <el-menu
          default-active="200010201010"
          class="el-menu-vertical-demo"
          :default-openeds="['2000','20001020']"
          @select="selectMenu">
          <el-submenu index="2000">
            <template slot="title">
              <span>客户权限转移</span>
            </template>
            <el-menu-item index="20001010">客户权限转移记录</el-menu-item>
            <el-submenu index="20001020">
              <template slot="title">
                <span>客户权限转移任务</span>
              </template>
              <el-menu-item index="200010201010">待处理请求</el-menu-item>
              <el-menu-item index="200010201020">已确认请求</el-menu-item>
              <el-menu-item index="200010201030">已拒绝请求</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <business-apply-list :applyphase="curnodeid"></business-apply-list>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import BusinessApplyList from '@/views/business/apply/list/businessapplylist'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'BusinessApplyTreeList',
  components: { BusinessApplyList },
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
      approvingcount: 2,
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
      // switch(idx) {
      //   case '10001010':
      //     break;
      //   case '10001020':
      //     break;
      //   case '20001010':
      //     break;
      //   case '20001020':
      //     break;
      //   case '20001030':
      //     break;
      //   default:
      //     break;
      // }
    }
  }
}
</script>