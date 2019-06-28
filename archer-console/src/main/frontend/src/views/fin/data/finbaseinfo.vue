<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12">
        <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">
          <el-form-item label="财报日期" prop="findate">
            <el-date-picker
              v-model="datainfo.findate"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          
          <el-form-item label="财报口径" prop="scope">
            <el-select v-model="datainfo.scope" clearable placeholder="">
              <el-option
                v-for="item in codemap.FinReportScope"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="财报周期" prop="period">
            <el-select v-model="datainfo.period" clearable placeholder="">
              <el-option
                v-for="item in codemap.FinReportPeriod"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          
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
          
          <el-form-item label="财报单位" prop="unit">
            <el-select v-model="datainfo.unit" clearable placeholder="">
              <el-option
                v-for="item in codemap.FinReportUnit"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" :row="3" v-model="datainfo.remark" />
          </el-form-item>
          
          <el-form-item label="是否审计" prop="isaudit">
            <el-select v-model="datainfo.isaudit" clearable placeholder="">
              <el-option
                v-for="item in codemap.IsNot"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="审计意见" prop="auditopinion">
            <el-input type="textarea" :row="3" v-model="datainfo.auditopinion" />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" @click="updateData()">
              保存
            </el-button>
          </el-form-item>

        </el-form>
      </el-col>
    </el-row>
    
    <!-- <div slot="footer">
      <el-button type="primary" @click="updateData()">
        保存
      </el-button>
    </div> -->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryFinBaseInfo,updateFinBaseInfo } from '@/api/fin/finbaseinfo'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'FinBaseInfo',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curid: String
  },
  data() {
    return {
      dataLoading : false,
      datainfo: {
      },
      codemap: {
      },
      rules: {
        findate: [{ required: true, message: '请输入报表日期', trigger: 'blur' }],
        scope: [{ required: true, message: '请输入报表口径', trigger: 'blur' }],
        period: [{ required: true, message: '请输入报表周期', trigger: 'blur' }],
        currency: [{ required: true, message: '请输入币种', trigger: 'blur' }],
        unit: [{ required: true, message: '请输入报表单元', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curid : function(newV, oldV){
      this.queryData();
    }
  },
  created() {
    queryCodeList({codelist:['FinReportScope','FinReportPeriod','Currency','FinReportUnit','IsNot','FinReportStatus']}).then(response => {
      this.codemap = response.data
      this.queryData()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    queryData() {
      this.dataLoading = true
      queryFinBaseInfo({id:this.curid}).then(response => {
        this.datainfo = response.data
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    },
    updateData() {
      this.$refs['dataInfoForm'].validate((valid) => {
        if (valid) {
          updateFinBaseInfo(this.datainfo).then((res) => {
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
