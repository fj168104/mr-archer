<template>
  <el-container>
    <!-- <el-header>
      <span class="report_node_title">{{title}}</span>
    </el-header> -->
    <el-main class="report-data">
      <el-row>
        <el-col :span="24" class="node-title">
          <span>{{title}}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" class="row-title">
          <span>（一）申请人概况</span>
        </el-col>
      </el-row>
      <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="客户名称" prop="name">
              <span class="floatcenter">{{datainfo.name}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">客户名称</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.name}}</span></div>
          </el-col>
          <el-col :span="12" >
            <!-- <el-form-item label="成立时间" prop="funddate">
              <span class="floatcenter">{{datainfo.funddate}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">成立时间</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.funddate}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="法定代表人" prop="legalname">
              <span class="floatcenter">{{legalname}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">法定代表人</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{legalname}}</span></div>
          </el-col>
          <el-col :span="12" >
            <!-- <el-form-item label="行业归属" prop="industryname">
              <span class="floatcenter">{{datainfo.industryname}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">行业归属</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.industryname}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="企业规模" prop="scalename">
              <span class="floatcenter">{{datainfo.scalename}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">企业规模</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.scalename}}</span></div>
          </el-col>
          <el-col :span="12" >
            <!-- <el-form-item label="实际控制人" prop="controllername">
              <span class="floatcenter">{{controllername}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">实际控制人</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{controllername}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="注册资本币种" prop="regcurrencyname">
              <span class="floatcenter">{{datainfo.regcurrencyname}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">注册资本币种</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.regcurrencyname}}</span></div>
          </el-col>
          <el-col :span="12" >
            <!-- <el-form-item label="注册资本（元）" prop="regcapital">
              <span class="floatcenter">{{datainfo.regcapital | toThousandFilter}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">注册资本（元）</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.regcapital | toThousandFilter}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="实收资本币种" prop="realcurrencyname">
              <span class="floatcenter">{{datainfo.realcurrencyname}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">实收资本币种</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.realcurrencyname}}</span></div>
          </el-col>
          <el-col :span="12" >
            <!-- <el-form-item label="实收资本（元）" prop="realcapital">
              <span class="floatcenter">{{datainfo.realcapital | toThousandFilter}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">实收资本（元）</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.realcapital | toThousandFilter}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <!-- <el-form-item label="注册地址" prop="regaddress">
              <span class="floatcenter">{{datainfo.regaddress}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">注册地址</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.regaddress}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <!-- <el-form-item label="办公地址" prop="office">
              <span class="floatcenter">{{datainfo.office}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">办公地址</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.office}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="联系电话" prop="tel">
              <span class="floatcenter">{{datainfo.tel}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">联系电话</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.tel}}</span></div>
          </el-col>
          <el-col :span="12" >
            <!-- <el-form-item label="财务部联系电话" prop="financetel">
              <span class="floatcenter">{{datainfo.financetel}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">财务部联系电话</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.financetel}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="基本户开户行" prop="basebank">
              <span class="floatcenter">{{datainfo.basebank}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">基本户开户行</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.basebank}}</span></div>
          </el-col>
          <el-col :span="12">
            <label class="el-form-item__label">开户行账号</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.baseaccount}}</span></div>
          </el-col>
        </el-row>
      </el-form>

      <el-row>
        <el-col :span="24" class="row-title">
          <span>（二）股权结构</span>
        </el-col>
      </el-row>
      <el-table
        :key="1"
        v-loading="dataLoading"
        :data="entstocklist"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="股东名称">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="出资方式" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.type | showCodeName(codemap.InvestType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="出资币种" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.currency | showCodeName(codemap.Currency)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="应出资金额（元）" align="right">
          <template slot-scope="scope">
            <span>{{ scope.row.money | toThousandFilter }}</span>
          </template>
        </el-table-column>
        
        <el-table-column label="实际投资金额（元）" align="right">
          <template slot-scope="scope">
            <span>{{ scope.row.realmoney | toThousandFilter}}</span>
          </template>
        </el-table-column>
        
        <el-table-column label="出资比例（%）" align="right">
          <template slot-scope="scope">
            <span>{{ scope.row.percent }}</span>
          </template>
        </el-table-column>
        
        <el-table-column label="投资日期" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.occurdate }}</span>
          </template>
        </el-table-column>
      </el-table>

      <el-row>
        <el-col :span="24" class="row-title">
          <span>（三）对外投资情况</span>
        </el-col>
      </el-row>
      <el-table
        :key="2"
        v-loading="dataLoading"
        :data="entinvestlist"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="投资企业名称" >
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        
        <el-table-column label="投资方式" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.type | showCodeName(codemap.InvestType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="出资币种" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.currency | showCodeName(codemap.Currency)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="实际投资金额（元）" align="right">
          <template slot-scope="scope">
            <span>{{ scope.row.realmoney | toThousandFilter}}</span>
          </template>
        </el-table-column>
      </el-table>

      <el-row>
        <el-col :span="24" class="row-title">
          <span>（四）高管信息</span>
        </el-col>
      </el-row>
      <el-table
        :key="3"
        v-loading="dataLoading"
        :data="entmanagerlist"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="高管类型" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.duty | showCodeName(codemap.Duty)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="高管名称">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="出生日期">
          <template slot-scope="scope">
            <span>{{ scope.row.birthday }}</span>
          </template>
        </el-table-column>

        <el-table-column label="最高学历"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.education | showCodeName(codemap.Education)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="任职时间">
          <template slot-scope="scope">
            <span>{{ scope.row.dutytime }}</span>
          </template>
        </el-table-column>

        <el-table-column label="从业年限">
          <template slot-scope="scope">
            <span>{{ scope.row.workyears }}</span>
          </template>
        </el-table-column>

        <el-table-column label="持股比例（%）">
          <template slot-scope="scope">
            <span>{{ scope.row.stock }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import { queryEntInfoReport } from '@/api/cust/ent'

export default {
  name: 'ReportCustomerInfo',
  props:{
    params: Object,
    isedit: Boolean
  },
  data: function() {
    return {
      dataLoading: false,
      codemap: {},
      title: this.params.title,
      datainfo: {},
      entstocklist: [],
      entmanagerlist: [],
      entinvestlist: [],
      rules:{}
    }
  },
  computed:{
    legalname:function(){
      if(this.entmanagerlist) {
        for (let i=0;i<this.entmanagerlist.length;i++) {
          if (this.entmanagerlist[i].duty == '01') {
            return this.entmanagerlist[i].name
          }
        }
        return ''
      }
    },
    controllername:function(){
      if(this.entmanagerlist) {
        for (let i=0;i<this.entmanagerlist.length;i++) {
          if (this.entmanagerlist[i].duty == '09') {
            return this.entmanagerlist[i].name
          }
        }
        return ''
      }
    }
  },
  created() {
    console.log(this.params)
    this.getData()
  },
  methods: {
    getData(){
      this.dataLoading = true;
      queryEntInfoReport({id: this.params.customerid,codelist:['InvestType','Currency','Education','Duty']}).then(response => {
        this.codemap = response.data.codemap
        this.datainfo = response.data.entinfo
        this.entstocklist = response.data.entstocklist
        this.entmanagerlist = response.data.entmanagerlist
        this.entinvestlist = response.data.entinvestlist
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("查询数据失败！")
        this.dataLoading = false
      })
    }
  }
}
</script>
