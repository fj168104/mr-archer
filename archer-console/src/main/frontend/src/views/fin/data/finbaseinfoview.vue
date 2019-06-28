<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="基本信息" name="finbaseinfo">
      <fin-base-info :curid="curid"></fin-base-info>
    </el-tab-pane>
    <!-- <el-tab-pane v-for="item in datalist" :key="item.id" :label="item.name" :name="item.id">
      <fin-data-view :curconfig="item"></fin-data-view>
    </el-tab-pane> -->
    <el-tab-pane label="资产负债表" name="finbalancesheet" v-if="sheetconfig">
      <fin-data-view :curid="curid" :curconfig="sheetconfig"></fin-data-view>
    </el-tab-pane>
    <el-tab-pane label="利润表" name="finprofit" v-if="profitconfig">
      <fin-data-view :curid="curid" :curconfig="profitconfig"></fin-data-view>
    </el-tab-pane>
    <el-tab-pane label="现金流量表" name="fincashflow" v-if="cashconfig">
      <fin-data-view :curid="curid" :curconfig="cashconfig"></fin-data-view>
    </el-tab-pane>
  </el-tabs>

</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryFinModelConfigRefList } from '@/api/fin/finmodelconfigref'
import FinBaseInfo from '@/views/fin/data/finbaseinfo'
import FinDataView from '@/views/fin/data/findataview'

export default {
  name: 'FinBaseInfoView',
  components: { FinBaseInfo,FinDataView },
  directives: { waves },
  filters: {},
  props: {
    curid: String,
    curfinmodelid: String
  },
  data: function() {
    return {
      activeName:'finbaseinfo',
      dataLoading: false,
      datalist:[]
    }
  },
  watch: {
    curid : function(newV, oldV){
      this.getList()
    }
  },
  computed:{
    sheetconfig: function(){
      for (let i=0;i<this.datalist.length;i++){
        let data = this.datalist[i];
        if (data.findatatype == "01")
          return data;
      }
      return null;
    },
    profitconfig: function(){
      for (let i=0;i<this.datalist.length;i++){
        let data = this.datalist[i];
        if (data.findatatype == "02")
          return data;
      }
      return null;
    },
    cashconfig: function(){
      for (let i=0;i<this.datalist.length;i++){
        let data = this.datalist[i];
        if (data.findatatype == "03")
          return data;
      }
      return null;
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(){
      this.dataLoading = true
      queryFinModelConfigRefList({modelid:this.curfinmodelid}).then(response => {
        this.datalist = response.data
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    },
    handleClick(){

    }
  }
}
</script>

