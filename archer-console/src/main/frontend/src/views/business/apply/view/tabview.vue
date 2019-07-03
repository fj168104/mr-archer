<template>
  <el-container>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="width:100%">
      <el-tab-pane label="申请信息" name="applyinfo">
        <business-apply-tree-view :applyid="curapplyid"></business-apply-tree-view>
      </el-tab-pane>
      <el-tab-pane label="客户信息" name="customerinfo">
        <ent-view :isedit="false" :curcustomerid="customerid"></ent-view>
      </el-tab-pane>
      <el-tab-pane label="调查报告" name="reportinfo">
        <business-report-data-tree-view :applyid="curapplyid" :customerid="customerid"></business-report-data-tree-view>
      </el-tab-pane>
    </el-tabs>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import BusinessApplyTreeView from '@/views/business/apply/view/treeview'
import BusinessReportDataTreeView from '@/views/business/apply/view/reportdatatreeview'
import EntView from '@/views/cust/ent/entview'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'BusinessApplyTabView',
  components: { BusinessApplyTreeView,EntView,BusinessReportDataTreeView },
  directives: { waves },
  filters: {},
  props: {
    applyid: String,
    customerid: String
  },
  data() {
    return {
      activeName: 'applyinfo',
      curtab: 'applyinfo',
      curapplyid: this.applyid
    }
  },
  created() {
  },
  methods: {
    handleClick(idx){
      this.curtab = idx
    }
  }
}
</script>