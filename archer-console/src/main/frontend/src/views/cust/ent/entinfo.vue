<template>
  <el-container>
    <el-header>
      <el-button type="primary" @click="updateData()">保存</el-button>
    </el-header>
    <el-main>
      <el-form
        ref="entInfoForm"
        v-loading="dataLoading"
        :rules="rules"
        :model="entinfo"
        label-position="left"
        label-width="160px"
      >
        <el-collapse v-model="activeNames">

          <!-- 基本信息 -->
          <el-collapse-item name="1010">
            <template slot="title">
              <span style="color:red;font-weight: bold;">【基本信息】</span>
            </template>
            <el-row>
              <el-col :span="8">
                <el-form-item label="客户编号" prop="id">
                  <span>{{entinfo.id}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="客户名称" prop="name">
                  <span>{{entinfo.name}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="证件类型" prop="certtype">
                  <span>{{entinfo.certtype | showCodeName(codemap.EntCertType)}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="证件编号" prop="certid">
                  <span>{{entinfo.certid}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="税务登记证号（国税）" prop="statetaxno">
                  <el-input v-model="entinfo.statetaxno"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="税务登记证号（地税）" prop="localtaxno">
                  <el-input v-model="entinfo.localtaxno"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-collapse-item>
          
          <!-- 注册信息 -->
          <el-collapse-item name="1020">
            <template slot="title">
              <span style="color:red;font-weight: bold;">【注册信息】</span>
            </template>
            <el-row>
              <el-col :span="8">
                <el-form-item label="企业规模" prop="scale">
                  <span>{{entinfo.scale | showCodeName(codemap.EntScale)}}</span>
                  <!-- <el-select v-model="entinfo.scale" clearable placeholder="">
                    <el-option
                      v-for="item in codemap.EntScale"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select> -->
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="企业类型" prop="entkind">
                  <el-cascader
                    :options="codemap.OrgType"
                    :show-all-levels="false"
                    v-model="curentkind"
                  ></el-cascader>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="国标行业分类" prop="industry">
                  <el-cascader
                    :options="sysIndustryOptions"
                    :show-all-levels="false"
                    v-model="curindustry"
                  ></el-cascader>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="工商营业执照号码" prop="businessno">
                  <el-input v-model="entinfo.businessno"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="企业成立日期" prop="funddate">
                  <el-date-picker v-model="entinfo.funddate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="营业执照登记日" prop="regbegindate">
                  <!-- <el-input v-model="entinfo.regbegindate"/> -->
                  <el-date-picker v-model="entinfo.regbegindate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="营业执照到期日" prop="regenddate">
                  <!-- <el-input v-model="entinfo.regenddate"/> -->
                  <el-date-picker v-model="entinfo.regenddate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="8">
                <el-form-item label="注册资本币种" prop="regcurrency">
                  <el-select v-model="entinfo.regcurrency" clearable placeholder="">
                    <el-option
                      v-for="item in codemap.Currency"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="注册资本（元）" prop="regcapital">
                  <el-input v-model="entinfo.regcapital"/>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="8">
                <el-form-item label="实收资本币种" prop="realcurrency">
                  <el-select v-model="entinfo.realcurrency" clearable placeholder="">
                    <el-option
                      v-for="item in codemap.Currency"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="实收资本（元）" prop="realcapital">
                  <el-input v-model="entinfo.realcapital"/>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="8">
                <el-form-item label="所在国家（地区）" prop="country">
                  <el-input v-model="entinfo.country"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="省份、直辖市、自治区" prop="province">
                  <el-input v-model="entinfo.province"/>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="16">
                <el-form-item label="注册地址" prop="regaddress">
                  <el-input v-model="entinfo.regaddress"/>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="8">
                <el-form-item label="注册地址邮政编码" prop="regpostcode">
                  <el-input v-model="entinfo.regpostcode"/>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="16">
                <el-form-item label="办公地址" prop="office">
                  <el-input v-model="entinfo.country"/>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row>
              <el-col :span="16">
                <el-form-item label="公司网址" prop="website">
                  <el-input v-model="entinfo.website"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-collapse-item>

          <!-- 经营信息 -->
          <el-collapse-item name="1030">
            <template slot="title">
              <span style="color:red;font-weight: bold;">【经营信息】</span>
            </template>

            <el-row>
              <el-col :span="8">
                <el-form-item label="有无进出口经营权" prop="istrade">
                  <el-select v-model="entinfo.istrade" clearable placeholder="">
                    <el-option
                      v-for="item in codemap.HasNot"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="是否担保公司" prop="isguaranty">
                  <el-select v-model="entinfo.isguaranty" clearable placeholder="">
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
              <el-col :span="8">
                <el-form-item label="职工人数" prop="workers">
                  <el-input v-model="entinfo.workers"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="16">
                <el-form-item label="经营范围" prop="scope">
                  <el-input type="textarea" :rows="3" v-model="entinfo.scope"/>
                </el-form-item>
              </el-col>
            </el-row>

          </el-collapse-item>

          <!-- 【账户信息】 -->
          <el-collapse-item name="1040">
            <template slot="title">
              <span style="color:red;font-weight: bold;">【账户信息】</span>
            </template>
            <el-row>
              <el-col :span="8">
                <el-form-item label="财务部联系电话" prop="financetel">
                  <el-input v-model="entinfo.financetel"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="财务报表类型" prop="financereport">
                  <el-input v-model="entinfo.financereport"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="基本帐户行" prop="basebank">
                  <el-input v-model="entinfo.basebank"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="基本帐户号" prop="baseaccount">
                  <el-input v-model="entinfo.baseaccount"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="我行开户行" prop="mybank">
                  <el-input v-model="entinfo.mybank"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="我行开户帐号" prop="myaccount">
                  <el-input v-model="entinfo.myaccount"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="他行开户行" prop="otherbank">
                  <el-input v-model="entinfo.otherbank"/>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="4">
                <el-form-item label="他行开户帐号" prop="otheraccount">
                  <el-input v-model="entinfo.otheraccount"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-collapse-item>
        </el-collapse>

        <!-- <el-form-item>
            <el-button type="primary" @click="updateData()">
              保存
            </el-button>
        </el-form-item>-->
      </el-form>

    <!-- <div slot="footer">
      <el-button type="primary" @click="updateData()">保存</el-button>
    </div> -->
    </el-main>
  </el-container>
</template>

<script>
import waves from "@/directive/waves"; // waves directive
import { queryEntInfo, updateEntInfo } from "@/api/cust/ent";
import { queryCodeList, queryIndustryList } from '@/api/syscode'

export default {
  name: "EntInfo",
  components: {},
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String
  },
  data() {
    return {
      dataLoading: false,
      entinfo: {},
      activeNames: ["1010", "1020", "1030", "1040"],
      curindustry: [],
      curentkind: [],
      codemap: {},
      rules: {
        industry: [
          { required: true, message: "请输入行业分类", trigger: "blur" }
        ]
      },
      sysIndustryOptions: []
    };
  },
  watch: {
    curcustomerid: function(newV, oldV) {
      this.initData();
    },
    curindustry: function(newV, oldV) {
      this.entinfo.industry = newV[newV.length - 1];
    },
    curentkind: function(newV, oldV) {
      this.entinfo.entkind = newV[newV.length - 1];
    }
  },
  created() {
    queryIndustryList().then(response => {
      this.sysIndustryOptions = response.data
      queryCodeList({codelist:['EntCertType','Currency','IsNot','HasNot','EntScale'], treelist:['OrgType']}).then(response => {
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
      this.entinfo = {};
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
      queryEntInfo({ id: this.curcustomerid })
        .then(response => {
          this.entinfo = response.data;
          if (this.entinfo.industry) {
            let l1 = this.entinfo.industry.substring(0, 1);
            let l2 = this.entinfo.industry.substring(0, 3);
            let l3 = this.entinfo.industry.substring(0, 4);
            this.curindustry = [l1, l2, l3, this.entinfo.industry];
          } else {
            this.curindustry = [];
          }
          this.curentkind = this.getCurTreeList(this.entinfo.entkind, 3);
          this.dataLoading = false;
        })
        .catch(() => {
          this.$message.info("获取数据失败！");
          this.dataLoading = false;
        });
    },
    updateData() {
      this.$refs["entInfoForm"].validate(valid => {
        if (valid) {
          updateEntInfo(this.entinfo).then(res => {
            this.$notify({
              title: "Success",
              message: "保存成功",
              type: "success",
              duration: 2000
            });
          });
        }
      });
    }
  }
};
</script>
