<template>
  <el-container>
    <el-main>
      <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="基础年利率" prop="rate">
              <el-input class="finnumber" v-model="datainfo.rate" style="width:200px">
                <template slot="append">%</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="调查报告模板" prop="reportid">
              <el-select v-model="datainfo.reportid" clearable placeholder="">
                <el-option
                  v-for="item in ReportModelList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="资料清单模板" prop="filelistid">
              <el-select v-model="datainfo.filelistid" clearable placeholder="">
                <el-option
                  v-for="item in FilelistModelList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="审批流程模板" prop="flowno">
              <el-select v-model="datainfo.flowno" clearable placeholder="">
                <el-option
                  v-for="item in FlowModelList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="是否有效" prop="isinuse">
              <el-select v-model="datainfo.isinuse" clearable placeholder="">
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

      </el-form>
    </el-main>

    <el-footer>
      <el-button v-if="isedit" type="primary" :loading="btnLoading" @click="updateData()">
        保存
      </el-button>
    </el-footer>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryBusinessType,createBusinessType,updateBusinessType } from '@/api/config/businesstype'
import { queryCodeList } from '@/api/syscode'
import { queryFilelistConfigCodeList } from '@/api/filelist/filelistconfig'


export default {
  name: 'BusinessType',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curid: String,
    isedit: Boolean
  },
  data() {
    return {
      dataLoading : false,
      btnLoading: false,
      ReportModelList: [],
      FlowModelList: [],
      FilelistModelList: [],
      datainfo: {
      },
      codemap: {
      },
      rules: {
        rate: [{ required: true, message: '请输入基础年利率', trigger: 'blur' }],
        reportid: [{ required: true, message: '请选择调查报告模板', trigger: 'blur' }],
        // filelistid: [{ required: true, message: '请选择资料清单模板', trigger: 'blur' }],
        // flowno: [{ required: true, message: '请选择审批流程模板', trigger: 'blur' }],
        isinuse: [{ required: true, message: '请选择是否有效', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.initData()
  },
  methods: {
    initData() {
      if (this.curid) {
        this.queryData();
      }
    },
    queryData() {
      this.dataLoading = true
      queryBusinessType({id:this.curid,codelist:['IsNot']}).then(response => {
        this.ReportModelList = response.data.reportmodellist
        this.FilelistModelList = response.data.filelistmodellist
        this.codemap = response.data.codemap
        this.datainfo = response.data.datainfo
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    },
    updateData() {
      this.$refs['dataInfoForm'].validate((valid) => {
        if (valid) {
          this.btnLoading = true
          if (this.curid) {
            updateBusinessType(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.btnLoading = false
              this.$emit('refreshList')
            })
          } else {
            createBusinessType(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.btnLoading = false
              this.$emit('refreshList')
            })
          }
        }
      })
    }
  }
}
</script>
