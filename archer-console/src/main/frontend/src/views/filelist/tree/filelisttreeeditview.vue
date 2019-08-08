<template>
  <el-container class="app-container">
    <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
      <el-tree :highlight-current="true" :expand-on-click-node="false" :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree>
    </el-aside>
    <el-main>
      <FilelistTreeNodeRelaListView :belongtype="belongtype" :belongid="belongid" :configid="configid" :treeid="treeid"></FilelistTreeNodeRelaListView>
    </el-main>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFilelistTreeView } from '@/api/filelist/filelisttree'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import FilelistTreeNodeRelaListView from '@/views/filelist/tree/filelisttreenoderelalistview'

export default {
  name: 'FilelistTreeEditView',
  components: { Pagination,FilelistTreeNodeRelaListView },
  directives: { waves },
  filters: {},
  props:{
    belongtype: String,
    belongid: String,
    configid: String
  },
  data() {
    return {
      dataLoading: false,
      treedata:[],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      treeid: ''
    }
  },
  created() {
    this.getTreeData()
  },
  methods: {
    getTreeData() {
      this.dataLoading = true
      queryFilelistTreeView({configid:this.configid}).then(response => {
        this.treedata = response.data
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    },
    handleNodeClick(data) {
      this.treeid = data.value
    }
  }
}
</script>