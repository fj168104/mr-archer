<template>
  <div class="app-container">
    <!-- <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>客户详情</span>
        <el-button style="float: right;" @click="this.$emit('closeView')">返回</el-button>
      </div>
      <div class="text item"> -->
        <el-row>
          <el-col :span="24">
            <span style="font-weight: bold;font-size: 20px;">客户详情-{{curcustomername}}</span>
            <el-button type="primary" style="float: right;" @click="$emit('closeView')">返回</el-button>
          </el-col>
        </el-row>
        <!-- <el-divider></el-divider> -->
        <el-row>
          <el-col :span="4">
            <div class="grid-content">
              <el-tree :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree>
            </div>
          </el-col>
          <el-col :span="20">
            <div class="grid-content">
              <ent-info :curcustomerid="curcustomerid" v-if="curnodeid===2"></ent-info>
              <ent-stock-list :curcustomerid="curcustomerid" v-if="curnodeid===3"></ent-stock-list>
            </div>
          </el-col>
        </el-row>
      <!-- </div>
    </el-card> -->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import EntInfo from '@/views/cust/ent/entinfo'
import EntStockList from '@/views/cust/ent/entstocklist'


export default {
  name: 'EntView',
  components: { EntInfo,EntStockList },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curcustomername: String
  },
  data() {
    return {
      treedata:[
        {
          id: 1,
          label: '客户信息',
          children: [
            {
              id: 2,
              label: '基本信息',
            },
            {
              id: 3,
              label: '股东信息',
            }
          ]
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      customerid: this.curcustomerid,
      curnodeid: 2
    }
  },
  watch: {
    curcustomerid : function(newV, oldV){
      this.curnodeid = 2
    }
  },
  created() {
  },
  methods: {
    handleNodeClick(data){
      if (data.id !== 1) {
        this.curnodeid = data.id;
      }
    }
  }
}
</script>