<template>
  <el-row>
    <el-col :span="24" align="right">
      <el-button type="primary" @click="calculateData">计算</el-button>
      <el-button type="primary" @click="saveData">保存</el-button>
    </el-col>
    <el-col :span="colspan" v-for="(collist,i) in datalist" :key="i">
      <el-row>
        <el-col>
          <el-row>
            <el-col :span="10" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
              <span>项目</span>
            </el-col>
            <el-col :span="2" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
              <span>行次</span>
            </el-col>
            <el-col :span="6" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
              <span>期末值（元）</span>
            </el-col>
            <el-col :span="6" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
              <span>年初值（元）</span>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" v-for="(item,j) in collist" :key="j">
          <el-row>
            <el-col :span="10">
              <el-input :value="item.name"/>
            </el-col>
            <el-col :span="2">
              <el-input :value="j+1"/>
            </el-col>
            <el-col :span="6" align="right">
              <el-input class="finnumber" v-if="item.isedit=='1'" v-model="curdatainfo[item.colname]" @change="checkNumber($event, curdatainfo, item.colname)"/>
              <el-input class="finnumber" v-else :value="curdatainfo[item.colname] | toThousandFilter" :disabled="true"/>
            </el-col>
            <el-col :span="6" align="right">
              <el-input class="finnumber" v-if="item.isedit=='1'" v-model="lastdatainfo[item.colname]" @change="checkNumber($event, lastdatainfo, item.colname)"/>
              <el-input class="finnumber" v-else :value="lastdatainfo[item.colname] | toThousandFilter" :disabled="true"/>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
import waves from '@/directive/waves'
import { queryFinBalanceSheet, updateFinBalanceSheet } from '@/api/fin/finbalancesheet'
import { queryFinProfit, updateFinProfit } from '@/api/fin/finprofit'
import { queryFinCashFlow, updateFinCashFlow } from '@/api/fin/fincashflow'
import { queryFinConfigColList } from '@/api/fin/finconfigcol'

export default {
  name: 'FinDataView',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curid: String,
    curconfig: Object
  },
  data: function() {
    return {
      dataLoading: false,
      curdatainfo:{},
      lastdatainfo:{},
      datalist:[]
    }
  },
  computed:{
    colspan: function(){
      if (this.curconfig.cols) {
        if (this.curconfig.cols == 1) {
          return 16
        } else {
          return 24/this.curconfig.cols;
        }
      } else {
        return 24
      }
    }
  },
  watch: {
    curid : function(newV, oldV){
      this.queryData()
    }
  },
  created() {
    this.queryData()
  },
  methods: {
    checkNumber(val, obj, col){
      let reg = new RegExp("^(-?\\d+)(\\.\\d+)?$")
      if (!reg.test(val)) {
        obj[col] = ''
        this.$message.error('请输入正确的数字格式');
      }
    },
    queryData(){
      this.dataLoading = true
      if (this.curconfig.findatatype == "01") {
        queryFinBalanceSheet({baseinfoid:this.curid}).then(response => {
          this.curdatainfo = response.data.curinfo
          this.lastdatainfo = response.data.lastinfo
          queryFinConfigColList({configid:this.curconfig.configid,cols:this.curconfig.cols}).then(response => {
            this.datalist = response.data
            this.dataLoading = false
            // this.$notify({
            //   title: 'Success',
            //   message: '查询成功',
            //   type: 'success',
            //   duration: 2000
            // })
          }).catch(() => {
            this.$message.info("获取数据失败！")
            this.dataLoading = false
          })
        }).catch(() => {
          this.$message.info("获取数据失败！")
          this.dataLoading = false
        })
      } else if (this.curconfig.findatatype == "02") {
        queryFinProfit({baseinfoid:this.curid}).then(response => {
          this.curdatainfo = response.data.curinfo
          this.lastdatainfo = response.data.lastinfo
          queryFinConfigColList({configid:this.curconfig.configid,cols:this.curconfig.cols}).then(response => {
            this.datalist = response.data
            this.dataLoading = false
            // this.$notify({
            //   title: 'Success',
            //   message: '查询成功',
            //   type: 'success',
            //   duration: 2000
            // })
          }).catch(() => {
            this.$message.info("获取数据失败！")
            this.dataLoading = false
          })
        }).catch(() => {
          this.$message.info("获取数据失败！")
          this.dataLoading = false
        })
      } else if (this.curconfig.findatatype == "03") {
        queryFinCashFlow({baseinfoid:this.curid}).then(response => {
          this.curdatainfo = response.data.curinfo
          this.lastdatainfo = response.data.lastinfo
          queryFinConfigColList({configid:this.curconfig.configid,cols:this.curconfig.cols}).then(response => {
            this.datalist = response.data
            this.dataLoading = false
            // this.$notify({
            //   title: 'Success',
            //   message: '查询成功',
            //   type: 'success',
            //   duration: 2000
            // })
          }).catch(() => {
            this.$message.info("获取数据失败！")
            this.dataLoading = false
          })
        }).catch(() => {
          this.$message.info("获取数据失败！")
          this.dataLoading = false
        })
      }
    },
    stringTrim(src){
      return src.replace(/^\s*|\s*$/g,"")
    },
    calculateData(){
      let formulaList = this.sortData(this.datalist)
      for (let y=0;y<formulaList.length;y++) {
        let curformula = formulaList[y];
        let curvaluedata = this.calculateCol(curformula.formula)
        this.curdatainfo[this.stringTrim(curformula.colname)] = curvaluedata.curvalue
        this.lastdatainfo[this.stringTrim(curformula.colname)] = curvaluedata.lastvalue
      }
    },
    calculateCol(formula){
      let pluslist = formula.split("+");
      let valueData = {
        curvalue: 0,
        lastvalue: 0
      }
      for (let i=0;i<pluslist.length;i++) {
        let curplus = pluslist[i];
        if(curplus.indexOf("-") != -1) {
          this.getMinusData(valueData, curplus)
        } else {
          let curvalue = this.curdatainfo[this.stringTrim(curplus)]
          if (curvalue)
            valueData.curvalue += parseFloat(curvalue)
          let lastvalue = this.lastdatainfo[this.stringTrim(curplus)]
          if (lastvalue)
            valueData.lastvalue += parseFloat(lastvalue)
        }
      }

      return valueData
    },
    getMinusData(valueData, formula){
      let minuslist = formula.split("-");
      for (let i=0;i<minuslist.length;i++) {
        let curminus = minuslist[i];
        if(i == 0) {
          let curvalue = this.curdatainfo[this.stringTrim(curminus)]
          if (curvalue)
            valueData.curvalue += parseFloat(curvalue)
          let lastvalue = this.lastdatainfo[this.stringTrim(curminus)]
          if (lastvalue)
            valueData.lastvalue += parseFloat(lastvalue)
        } else {
          let curvalue = this.curdatainfo[this.stringTrim(curminus)]
          if (curvalue)
            valueData.curvalue -= parseFloat(curvalue)
          let lastvalue = this.lastdatainfo[this.stringTrim(curminus)]
          if (lastvalue)
            valueData.lastvalue -= parseFloat(lastvalue)
        }
      }
    },
    sortData(datalist){
      let formulaList = []
      for (let i=0;i<datalist.length;i++) {
        let collist = datalist[i];
        for (let j=0;j<collist.length;j++) {
          let curcol = collist[j];
          let formulatxt = curcol.formulatxt
          if (formulatxt) {
            let curdata = {
              colname: curcol.colname,
              formula: formulatxt,
              sortno: curcol.formulasortno
            }
            if (formulaList.length == 0) {
              formulaList.push(curdata)
            } else {
              for (let x=formulaList.length-1;x>=0;x--) {
                let curformula = formulaList[x];
                if (curdata.sortno > curformula.sortno) {
                  if (x == formulaList.length - 1) {
                    formulaList.push(curdata)
                  } else {
                    formulaList.splice(x + 1, 0, curdata)
                  }
                } else if(x == 0){
                  formulaList.unshift(curdata);
                } else {
                  continue;
                }
              }
            }
          }
        }
      }

      return formulaList
    },
    saveData(){
      this.dataLoading = true
      this.calculateData()
      if (this.curconfig.findatatype == "01") {
        updateFinBalanceSheet({curinfo:this.curdatainfo,lastinfo:this.lastdatainfo}).then(response => {
          this.$notify({
            title: 'Success',
            message: '保存成功',
            type: 'success',
            duration: 2000
          })
        }).catch(() => {
          this.$message.info("获取数据失败！")
          this.dataLoading = false
        })
      } else if (this.curconfig.findatatype == "02") {
        updateFinProfit({curinfo:this.curdatainfo,lastinfo:this.lastdatainfo}).then(response => {
          this.$notify({
            title: 'Success',
            message: '保存成功',
            type: 'success',
            duration: 2000
          })
        }).catch(() => {
          this.$message.info("获取数据失败！")
          this.dataLoading = false
        })
      } else if (this.curconfig.findatatype == "03") {
        updateFinCashFlow({curinfo:this.curdatainfo,lastinfo:this.lastdatainfo}).then(response => {
          this.$notify({
            title: 'Success',
            message: '保存成功',
            type: 'success',
            duration: 2000
          })
        }).catch(() => {
          this.$message.info("获取数据失败！")
          this.dataLoading = false
        })
      }
      
    }
  }
}
</script>

<style>
.finnumber .el-input__inner{
  text-align: right;
}
</style>
