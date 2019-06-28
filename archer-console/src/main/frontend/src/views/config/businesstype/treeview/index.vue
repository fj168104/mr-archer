<template>
  <el-container>
    <el-container>
      <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
        <el-tree :highlight-current="true" :expand-on-click-node="false" :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      </el-aside>
      <el-main>
        <business-type-list :type="curnodeid" :typename="curnodelabel"></business-type-list>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import BusinessTypeList from '@/views/config/businesstype/treeview/businesstypelist'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'BusinessTypeTreeView',
  components: { BusinessTypeList },
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
      curnodeid: '1000',
      curnodelabel: '所有业务'
    }
  },
  created() {
    this.getTreeData()
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
    }
  }
}
</script>