<template>
  <div class="app-container">

    <el-form ref="entInfoForm" v-loading="dataLoading" :rules="rules" :model="entinfo" label-position="left" label-width="100px">

        <el-row>
          <el-col :span="8">
            <el-form-item label="工商营业执照号码" prop="businessno">
              <el-input v-model="entinfo.businessno"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="企业类型" prop="entkind">
              <el-input v-model="entinfo.entkind"/>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col :span="8">
            <el-form-item label="企业成立日期" prop="funddate">
              <!-- <el-input v-model="entinfo.funddate" /> -->
              <el-date-picker
                v-model="entinfo.funddate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="国标行业分类" prop="industry">
              <el-cascader
                expand-trigger="hover"
                :options="codemap.sysIndustryOptions"
                :show-all-levels="false"
                v-model="curindustry">
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="营业执照登记日" prop="regbegindate">
              <!-- <el-input v-model="entinfo.regbegindate"/> -->
              <el-date-picker
                v-model="entinfo.regbegindate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="营业执照到期日" prop="regenddate">
              <!-- <el-input v-model="entinfo.regenddate"/> -->
              <el-date-picker
                v-model="entinfo.regenddate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="企业规模" prop="scale">
              <el-input v-model="entinfo.scale" />
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="职工人数" prop="workers">
              <el-input v-model="entinfo.workers"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="16">
            <el-form-item label="经营范围" prop="scope">
              <el-input type="textarea" :rows="3" v-model="entinfo.scope" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- <el-form-item>
          <el-button type="primary" @click="updateData()">
            保存
          </el-button>
        </el-form-item> -->

      </el-form>
      
      <div slot="footer">
          <el-button type="primary" @click="updateData()">
            保存
          </el-button>
      </div>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryEntInfo, updateEntInfo } from '@/api/cust/ent'


export default {
  name: 'EntInfo',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
  },
  data() {
    return {
      dataLoading : false,
      entinfo: {
      },
      curindustry: [],
      codemap: {
        sysIndustryOptions: [{
          value: 'A',
          label: '农、林、牧、渔业',
          children: [{
            value: 'A01',
            label: '农业',
            children: [{
              value: 'A011',
              label: '谷物种植',
              children: [{
                value: 'A0111',
                label: '稻谷种植'
              },{
                value: 'A0112',
                label: '小麦种植'
              }]
            }]
          }]
        },{
          value: 'B',
          label: '采矿业',
          children: [{
            value: 'B06',
            label: '煤炭开采和洗选业',
            children: [{
              value: 'B061',
              label: '烟煤和无烟煤开采洗选',
              children: [{
                value: 'B0610',
                label: '烟煤和无烟煤开采洗选'
              }]
            }]
          }]
        }]
      },
      rules: {
        industry: [{ required: true, message: '请输入行业分类', trigger: 'blur' }],
        businessno: [{ required: true, message: '请输入工商营业执照号码', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curcustomerid : function(newV, oldV){
      this.initData();
    },
    curindustry : function(newV, oldV){
      this.entinfo.industry = newV[newV.length - 1];
    }
  },
  created() {
    this.initData()
  },
  methods: {
    initData() {
      this.entinfo = {};
      this.queryData();
    },
    queryData() {
      this.dataLoading = true
      queryEntInfo({id:this.curcustomerid}).then(response => {
        this.entinfo = response.data
        if(this.entinfo.industry) {
          let l1 = this.entinfo.industry.substring(0, 1)
          let l2 = this.entinfo.industry.substring(0, 3)
          let l3 = this.entinfo.industry.substring(0, 4)
          this.curindustry = [l1, l2, l3, this.entinfo.industry];
        } else {
          this.curindustry = [];
        }
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    },
    updateData() {
      this.$refs['entInfoForm'].validate((valid) => {
        if (valid) {
          updateEntInfo(this.entinfo).then((res) => {
            this.$notify({
              title: 'Success',
              message: '保存成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    }
  }
}
</script>
