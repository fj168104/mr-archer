<template>
  <div class="app-container">
    
    <el-divider content-position="left">基本信息</el-divider>

    <el-form ref="entInfoForm" :rules="rules" :model="entinfo" label-position="left" label-width="100px">

        <template>
          <div>
            <el-divider content-position="left">基本信息</el-divider>
          </div>
        </template>

        <el-row>
          <el-col span="8">
            <el-form-item label="工商营业执照号码" prop="businessno">
              <el-input v-model="entinfo.businessno"/>
            </el-form-item>
          </el-col>
          <el-col span="8" offset="4">
            <el-form-item label="企业类型" prop="entkind">
              <el-input v-model="entinfo.entkind"/>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col span="8">
            <el-form-item label="企业成立日期" prop="funddate">
              <el-input v-model="entinfo.funddate" />
            </el-form-item>
          </el-col>
          <el-col span="8" offset="4">
            <el-form-item label="国标行业分类" prop="industry">
              <el-cascader
                expand-trigger="hover"
                :options="codemap.sysIndustryOptions"
                v-model="entinfo.industry">
              </el-cascader>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col span="8">
            <el-form-item label="营业执照登记日" prop="regbegindate">
              <el-input v-model="entinfo.regbegindate"/>
            </el-form-item>
          </el-col>
          <el-col span="8" offset="4">
            <el-form-item label="营业执照到期日" prop="regenddate">
              <el-input v-model="entinfo.regenddate"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col span="8">
            <el-form-item label="企业规模" prop="scale">
              <el-input v-model="entinfo.scale" />
            </el-form-item>
          </el-col>
          <el-col span="8" offset="4">
            <el-form-item label="职工人数" prop="workers">
              <el-input v-model="entinfo.workers"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col span="16">
            <el-form-item label="经营范围" prop="scope">
              <el-input type="textarea" :rows="3" v-model="entinfo.scope" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" @click="updateData()">
            保存
          </el-button>
        </el-form-item>

      </el-form>

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
      entinfo: {
      },
      codemap:{
        sysIndustryOptions:[]
      },
      rules: {
        name: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        certtype: [{ required: true, message: '请输入证件类型', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curcustomerid : function(newV, oldV){
      this.queryData();
    }
  },
  created() {
    // this.queryData()
  },
  methods: {
    queryData() {
      queryEntInfo({id:this.curcustomerid}).then(response => {
        this.entinfo = response
      }).catch(() => {
        this.$message.info("获取数据失败！")
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
