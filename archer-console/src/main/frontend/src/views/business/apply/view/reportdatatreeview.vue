<template>
  <el-container>
    <el-header>
      <el-button @click="prePrintReport()" type="primary">预览</el-button>
      <el-button @click="printReport()" type="primary">打印</el-button>
    </el-header>
    <el-container>
      <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
        <el-menu
          class="el-menu-vertical-demo"
          @select="selectMenu">
          <el-menu-item index="1000">尽职承诺</el-menu-item>
          <el-menu-item v-for="item in reportlist" :key="item.id" :index="item.id">{{item.title}}</el-menu-item>
        </el-menu>
      </el-aside>
      <el-main id="printview">
        <div style="text-align:center">
          <div id="reportview" style="width:800px;display: inline-block;"></div>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Vue from "vue"
import waves from '@/directive/waves' // waves directive
import ReportTop from '@/views/report/model/reporttop'
import ReportBusinessApply from '@/views/report/model/businessapply'
import ReportCustomerInfo from '@/views/report/model/customerinfo'

import { queryBusinessReportDataList } from '@/api/busi/businessreportdata'
import { queryCodeList } from '@/api/syscode'

import { print } from '@/utils/print'

export default {
  name: 'BusinessReportDataTreeView',
  components: { ReportTop,ReportBusinessApply,ReportCustomerInfo },
  directives: { waves },
  filters: {},
  props: {
    applyid: String,
    customerid: String
  },
  data() {
    return {
      curapplyid: this.applyid,
      curcustomerid: this.customerid,
      reportlist: [],
      curnodeid: ''
    }
  },
  created() {
    queryBusinessReportDataList({applyid: this.applyid}).then(response => {
      this.reportlist = response.data
    }).catch(() => {
      this.$message.info("查询数据失败！")
      this.dataLoading = false
    })
  },
  methods: {
    selectMenu(idx){
      if (idx == '1000') {
        let reportviewcomp = Vue.extend(ReportTop)
        let curcomp = new reportviewcomp({propsData:{params:{applyid: this.curapplyid,customerid: this.curcustomerid},isedit:true}}).$mount()
        let dom = document.querySelector("#reportview")
        while (dom.hasChildNodes()) {
          dom.removeChild(dom.firstChild)
        }
        dom.appendChild(curcomp.$el)
      } else {
        for (let i=0;i<this.reportlist.length;i++) {
          let curreportdata = this.reportlist[i]
          if (curreportdata.id == idx) {
            // let filepath = '@/views' + curreportdata.vuepath +'.vue'
            // console.log('filepath=' + filepath)
            // const reportviewcomp = () => import(filepath);
            let curcomp
            let reportviewcomp
            switch(curreportdata.vuepath) {
              case '/report/model/businessapply':
                reportviewcomp = Vue.extend(ReportBusinessApply)
                break
              case '/report/model/customerinfo':
                reportviewcomp = Vue.extend(ReportCustomerInfo)
                break
              default:
                break
            }
            curcomp = new reportviewcomp({propsData:{params:{title:(i+1) + "、" + curreportdata.title,applyid: this.curapplyid,customerid: this.curcustomerid},isedit:true}}).$mount()
            let dom = document.querySelector("#reportview")
            while (dom.hasChildNodes()) {
              dom.removeChild(dom.firstChild)
            }
            dom.appendChild(curcomp.$el)
            break;
          }
        }
      }
    },
    prePrintReport(){
      let dom = document.querySelector("#reportview")
      while (dom.hasChildNodes()) {
        dom.removeChild(dom.firstChild)
      }
      if (true) {
        let reportviewcomp = Vue.extend(ReportTop)
        let curcomp = new reportviewcomp({propsData:{params:{applyid: this.curapplyid,customerid: this.curcustomerid},isedit:false}}).$mount()
        dom.appendChild(curcomp.$el)
      }
      for (let i=0;i<this.reportlist.length;i++) {
        let curreportdata = this.reportlist[i]
        let reportviewcomp
        switch(curreportdata.vuepath) {
          case '/report/model/businessapply':
            reportviewcomp = Vue.extend(ReportBusinessApply)
            break
          case '/report/model/customerinfo':
            reportviewcomp = Vue.extend(ReportCustomerInfo)
            break
          default:
            break
        }
        let curcomp = new reportviewcomp({propsData:{params:{title:(i+1) + "、" + curreportdata.title,applyid: this.curapplyid,customerid: this.curcustomerid},isedit:false}}).$mount()
        dom.appendChild(curcomp.$el)
      }
    },
    printReport(){
      // let oldHtml = document.body.innerHTML
      // let printHtml = document.getElementById("reportview").innerHTML
      // document.body.innerHTML = printHtml
      // window.print()
      // document.body.innerHTML = oldHtml
      // window.location.reload()
      let printdoc = print(document.getElementById("printview").innerHTML, document.getElementsByTagName("head")[0].innerHTML)
      printdoc.close()
    }
  }
}
</script>