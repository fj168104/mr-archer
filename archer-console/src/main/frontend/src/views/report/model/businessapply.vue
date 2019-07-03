<template>
  <el-container>
    <!-- <el-header>
      <span class="report_node_title">{{title}}</span>
    </el-header> -->
    <el-main class="report-data" >
      <el-row>
        <el-col :span="24" class="node-title">
          <span>{{title}}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" class="row-title">
          <span>（一）业务概况</span>
        </el-col>
      </el-row>
      <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">
        <el-row>
          <el-col :span="24">
            <!-- <el-form-item label="经办机构" prop="orgname">
              <span class="floatcenter">{{datainfo.orgname}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">经办机构</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.createorgname}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <!-- <el-form-item label="客户名称" prop="custmername">
              <span class="floatcenter">{{datainfo.custmername}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">客户名称</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.customername}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <!-- <el-form-item label="业务发生方式" prop="occurtype">
              <span class="floatcenter">{{datainfo.occurtype | showCodeName(codemap.BusiOccurType)}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">业务发生方式</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.occurtype | showCodeName(codemap.BusiOccurType)}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <!-- <el-form-item label="业务品种" prop="businesstype">
              <span class="floatcenter">{{datainfo.businesstypename}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">业务品种</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.businesstypename}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="币种" prop="currency">
              <span class="floatcenter">{{datainfo.currency | showCodeName(codemap.Currency)}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">币种</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.currency | showCodeName(codemap.Currency)}}</span></div>
          </el-col>
          <el-col :span="12">
            <!-- <el-form-item label="期限" prop="currency">
              <span class="floatcenter">{{datainfo.term}}{{datainfo.termunit | showCodeName(codemap.TermUnit)}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">期限</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.term}}{{datainfo.termunit | showCodeName(codemap.TermUnit)}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="申请金额" prop="money">
              <span class="floatcenter">{{datainfo.money | toThousandFilter}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">申请金额</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.money | toThousandFilter}}</span></div>
          </el-col>
          <el-col :span="12">
            <!-- <el-form-item label="执行年利率（%）" prop="rate">
              <span class="floatcenter">{{datainfo.rate}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">执行年利率（%）</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.rate}}</span></div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <!-- <el-form-item label="还款方式" prop="repaytype">
              <span class="floatcenter">{{datainfo.repaytype | showCodeName(codemap.RepayType)}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">还款方式</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.repaytype | showCodeName(codemap.RepayType)}}</span></div>
          </el-col>
          <el-col :span="12">
            <!-- <el-form-item label="还款频率" prop="repayfrequency">
              <span class="floatcenter">{{datainfo.repayfrequency | showCodeName(codemap.RepayFrequency)}}</span>
            </el-form-item> -->
            <label class="el-form-item__label">还款频率</label>
            <div class="el-form-item__content" style="margin-left: 160px;"><span>{{datainfo.repayfrequency | showCodeName(codemap.RepayFrequency)}}</span></div>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { queryCodeList } from '@/api/syscode'
import { queryBusinessApply } from '@/api/busi/businessapply'

export default {
  name: 'ReportBusinessApply',
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
      rules:{}
    }
  },
  created() {
    console.log(this.params)
    this.getData()
  },
  methods: {
    getData(){
      this.dataLoading = true;
      queryCodeList({codelist:['BusiOccurType','Currency','TermUnit','RepayType','RepayFrequency']}).then(response => {
        this.codemap = response.data
        queryBusinessApply({id: this.params.applyid}).then(response => {
          this.datainfo = response.data
          this.dataLoading = false
        }).catch(() => {
          this.$message.info("查询业务申请数据失败！")
          this.dataLoading = false
        })
      }).catch(() => {
        this.$message.info("查询代码数据失败！")
        this.dataLoading = false
      })
    }
  }
}
</script>
