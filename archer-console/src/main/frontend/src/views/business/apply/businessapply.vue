<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>申请基本信息</span>
      <el-button style="float: right;" type="primary" @click="updateData()">保存</el-button>
    </div>
    <el-form
      ref="dataInfoForm"
      v-loading="dataLoading"
      :rules="rules"
      :model="datainfo"
      label-position="left"
      label-width="160px"
    >
      <el-collapse v-model="activeNames">

        <!-- 基本信息 -->
        <el-collapse-item name="1010">
          <template slot="title">
            <span style="color:#ee7e7e;font-weight: bold;">【基本信息】</span>
          </template>
          <el-row>
            <el-col :span="8">
              <el-form-item label="客户名称" prop="name">
                <span>{{datainfo.customername}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="发生类型" prop="name">
                <span>{{datainfo.occurtype | showCodeName(codemap.BusiOccurType)}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="业务品种" prop="businesstypename">
                <span>{{datainfo.businesstypename}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="币种" prop="currency">
                <el-select v-model="datainfo.currency" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.Currency"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row>
            <el-col :span="8">
              <el-form-item label="申请金额" prop="money">
                <el-input class="finnumber" v-model="datainfo.money">
                  <template slot="append">元</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="贷款投向" prop="industry">
                <el-cascader
                  :options="sysIndustryOptions"
                  :show-all-levels="false"
                  v-model="curindustry"
                  style="width:300px"
                ></el-cascader>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row>
            <el-col :span="8">
              <el-form-item label="主要担保方式" prop="guaranttype">
                <el-select v-model="datainfo.guaranttype" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.GuarantType"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="是否循环" prop="iscycle">
                <el-select v-model="datainfo.iscycle" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.IsNot"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row>
            <el-col :span="16">
              <el-form-item label="贷款用途" prop="purpose">
                <el-input type="textarea" v-model="datainfo.purpose"/>
              </el-form-item>
            </el-col>
          </el-row>

        </el-collapse-item>
        
        <!-- 利率期限信息 -->
        <el-collapse-item name="1020">
          <template slot="title">
            <span style="color:#ee7e7e;font-weight: bold;">【利率期限信息】</span>
          </template>
          <el-row>
            <el-col :span="8">
              <el-form-item label="期限单位" prop="termunit">
                <el-select v-model="datainfo.termunit" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.TermUnit"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="期限" prop="term">
                <el-input class="finnumber" v-model="datainfo.term"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="基准年利率" prop="baserate">
                <el-input class="finnumber" v-model="datainfo.baserate" disabled>
                  <template slot="append">%</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="利率浮动方式" prop="ratefloattype">
                <el-select v-model="datainfo.ratefloattype" clearable placeholder="" @change="calculateRate">
                  <el-option
                    v-for="item in codemap.RateFloatType"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="利率浮动值" prop="ratefloatval">
                <el-input class="finnumber" v-model="datainfo.ratefloatval" @change="calculateRate"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="执行年利率" prop="rate">
                <el-input class="finnumber" v-model="datainfo.rate" disabled>
                  <template slot="append">%</template>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="执行月利率" prop="monthrate">
                <el-input class="finnumber" v-model="datainfo.monthrate" disabled>
                  <template slot="append">‰</template>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          
        </el-collapse-item>

        <!-- 还款信息 -->
        <el-collapse-item name="1030">
          <template slot="title">
            <span style="color:#ee7e7e;font-weight: bold;">【还款信息】</span>
          </template>

          <el-row>
            <el-col :span="8">
              <el-form-item label="还款方式" prop="repaytype">
                <el-select v-model="datainfo.repaytype" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.RepayType"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8" :offset="4">
              <el-form-item label="还款频率" prop="repayfrequency">
                <el-select v-model="datainfo.repayfrequency" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.RepayFrequency"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="还息频率" prop="interestfrequency">
                <el-select v-model="datainfo.interestfrequency" clearable placeholder="">
                  <el-option
                    v-for="item in codemap.RepayFrequency"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="16">
              <el-form-item label="还款说明" prop="repaydesc">
                <el-input type="textarea" :rows="3" v-model="datainfo.repaydesc"/>
              </el-form-item>
            </el-col>
          </el-row>

        </el-collapse-item>
      </el-collapse>
    </el-form>
  </el-card>
</template>

<script>
import waves from "@/directive/waves"; // waves directive
import { queryBusinessApply, updateBusinessApply } from '@/api/busi/businessapply'
import { queryCodeList, queryIndustryList } from '@/api/syscode'

export default {
  name: "BusinessApply",
  components: {},
  directives: { waves },
  filters: {},
  props: {
    applyid: String
  },
  data() {
    return {
      dataLoading: false,
      datainfo: {},
      activeNames: ["1010", "1020", "1030"],
      curindustry: [],
      sysIndustryOptions: [],
      codemap: {},
      rules: {
        industry: [{ required: true, message: "请输入贷款投向", trigger: "blur" }],
        currency: [{ required: true, message: "请输入币种", trigger: "blur" }],
        money: [{ required: true, message: "请输入申请金额", trigger: "blur" }],
        termunit: [{ required: true, message: "请输入期限单位", trigger: "blur" }],
        term: [{ required: true, message: "请输入期限", trigger: "blur" }],
        baseratetype: [{ required: true, message: "请输入基准利率类型", trigger: "blur" }],
        purpose: [{ required: true, message: "请输入授信用途", trigger: "blur" }],
        repaytype: [{ required: true, message: "请输入还款方式", trigger: "blur" }],
        repayfrequency: [{ required: true, message: "请输入还款频率", trigger: "blur" }],
        ratefloattype: [{ required: true, message: "请输入利率浮动方式", trigger: "blur" }],
        ratefloatval: [{ required: true, message: "请输入利率浮动值", trigger: "blur" }]
      }
    };
  },
  watch: {
    curindustry: function(newV, oldV) {
      this.datainfo.industry = newV[newV.length - 1];
    }
  },
  created() {
    queryIndustryList().then(response => {
      this.sysIndustryOptions = response.data
      queryCodeList({codelist:['RepayFrequency','TermUnit','GuarantType','BusiOccurType','RateFloatType','RepayType','RepayFrequency','Currency','IsNot']}).then(response => {
        this.codemap = response.data
        this.initData();
      }).catch(() => {
        this.$message.info("获取代码表数据失败！")
      })
    }).catch(() =>{
      this.$message.info("获取行业分类数据失败！")
    })
  },
  methods: {
    initData() {
      this.datainfo = {};
      this.queryData();
    },
    getCurTreeList(src, length){
      let a = [];
      if (src) {
        const levellenght = src.length/length;
        for (let i =0;i<levellenght;i++) {
          a[i] = src.substring(0, length * (i+1));
        }
      }
      return a;
    },
    queryData() {
      this.dataLoading = true;
      queryBusinessApply({ id: this.applyid })
        .then(response => {
          this.datainfo = response.data;
          let industry = this.datainfo.industry
          if (industry) {
            let l1 = industry.substring(0, 1);
            let l2 = industry.substring(0, 3);
            let l3 = industry.substring(0, 4);
            this.curindustry = [l1, l2, l3, industry];
          } else {
            this.curindustry = [];
          }
          this.dataLoading = false;
        })
        .catch(() => {
          this.$message.info("获取数据失败！");
          this.dataLoading = false;
        });
    },
    updateData() {
      this.$refs["dataInfoForm"].validate(valid => {
        if (valid) {
          updateBusinessApply(this.datainfo).then(res => {
            this.$notify({
              title: "Success",
              message: "保存成功",
              type: "success",
              duration: 2000
            });
          });
        }
      });
    },
    calculateRate(){
      let baserate = parseFloat(this.datainfo.baserate)
      let ratefloattype = this.datainfo.ratefloattype
      let ratefloatval = parseFloat(this.datainfo.ratefloatval)
      if (ratefloattype && (ratefloatval || ratefloatval == 0)) {
        if (ratefloattype == "01") {
          // 浮动比率
          if (ratefloatval == 0) {
            this.datainfo.rate = baserate
          } else {
            this.datainfo.rate = Math.round(baserate * (1 + ratefloatval/100) * 1000000) / 1000000
          }
        } else if (ratefloattype == "02") {
          // 浮动点
          this.datainfo.rate = Math.round(baserate + ratefloatval * 1000000) / 1000000
        }
        this.datainfo.monthrate = Math.round(this.datainfo.rate/12 * 1000000) / 1000000
      }
    }
  }
};
</script>
