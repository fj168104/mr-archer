<template>
  <el-container>
    <el-main style="font-weight:bold;font-family: KaiTi;">
      <el-row>
        <el-col :span="24">
          <div style="font-size: 40px;padding-top: 10px;padding-bottom: 50px;color:black">
            <span>授信调查报告</span>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 16px;padding-bottom: 30px;color:black">
            <span>（小微企业通用调查报告）</span>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 16px;padding-bottom: 15px;color:black">
            <div style="text-align:left;width:300px;display: inline-block;">
              <span>申请人：</span>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 16px;padding-bottom: 15px;color:black">
            <div style="text-align:left;width:300px;display: inline-block;">
              <span>申报行：</span>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 16px;padding-bottom: 15px;color:black">
            <div style="text-align:left;width:300px;display: inline-block;">
              <span>调查人员（主办）：</span>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 16px;padding-bottom: 15px;color:black">
            <div style="text-align:left;width:300px;display: inline-block;">
              <span>调查人员填报时间：</span>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 30px;padding-top: 100px;padding-bottom: 50px;color:black">
            <span>尽职承诺</span>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="font-size: 16px;padding-bottom: 15px;color:black">
            <div style="text-align:left;display: inline-block;padding:0px 30px">
              <span>&nbsp;&nbsp;&nbsp;&nbsp;我作为本授信的调查人员，已按我行制度规定和业务规范要求对申请人、担保人等进行了实地调查，对授信的有关情况作了全面调查和了解，对其提供的身份证明、授信主体资格、财务状况等资料的合法性、全面性、真实性和有效性进行了认真核实，对涉及的主要情况和业务风险已经全面阐述，所作评估遵循了客观、公正的原则。同时，我声明该授信客户（申请人）不是我的关系人。</span>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div style="text-align: right;font-size: 16px;padding-bottom: 15px;color:black;padding-right:30px">
            <span>调查人员（主办）：</span>
            <span style="padding:0px 150px">调查日期：</span>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'ReportTop',
  data: function() {
    return {
      curdata: {}
    }
  },
  created() {
    
  },
  methods: {

  }
}
</script>
