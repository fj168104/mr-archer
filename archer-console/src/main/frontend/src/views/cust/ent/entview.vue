<template>
  <div class="app-container">
    <el-row>
      <el-col :span="4">
        <div class="grid-content">
          <el-tree :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree>
        </div>
      </el-col>
      <el-col :span="20">
        <div class="grid-content">
          <ent-info :curcustomerid="curcustomerid" v-if="curnodeid===2"></ent-info>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import EntInfo from '@/views/cust/ent/entinfo'


export default {
  name: 'EntView',
  components: { EntInfo },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
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
    }
  },
  created() {
  },
  methods: {
    handleNodeClick(data){
      this.curnodeid = data.id;
    }
  }
}
</script>