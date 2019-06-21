<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">

        <el-row>
          <el-col :span="10">
            <el-form-item label="担任职务" prop="duty">
              <el-select v-model="datainfo.duty" clearable placeholder="">
                <el-option
                  v-for="item in codemap.Duty"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="高管名称" prop="name">
              <el-input v-model="datainfo.name"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="证件类型" prop="certtype">
              <el-select v-model="datainfo.certtype" clearable placeholder="">
                <el-option
                  v-for="item in codemap.PersonCertType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="证件编号" prop="certid">
              <el-input v-model="datainfo.certid"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="datainfo.sex" clearable placeholder="">
                <el-option
                  v-for="item in codemap.Sex"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="出生日期" prop="birthday">
              <el-date-picker
                v-model="datainfo.birthday"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="学历" prop="education">
              <el-select v-model="datainfo.education">
                <el-option 
                  v-for="item in codemap.Education"
                  :key="item.value"
                  :value="item.value"
                  :label="item.label">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="联系电话" prop="tel">
              <el-input v-model="datainfo.tel"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="担任该职务时间" prop="dutytime">
              <el-date-picker
                v-model="datainfo.dutytime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="相关行业从业年限" prop="workyears">
              <el-input v-model="datainfo.workyears"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="持股比例（%）" prop="stock">
              <el-input v-model="datainfo.stock"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="20">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :row="3" v-model="datainfo.remark"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="是否有效" prop="status">
              <el-select v-model="datainfo.status" clearable placeholder="">
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
      
      <div slot="footer">
          <el-button type="primary" @click="updateData()">
            保存
          </el-button>
      </div>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryEntManager,createEntManager,updateEntManager } from '@/api/cust/entmanager'
import { queryCodeList } from '@/api/syscode'



export default {
  name: 'EntManager',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curentmanagerid: String
  },
  data() {
    return {
      dataLoading : false,
      datainfo: {
      },
      codemap: {
      },
      certTypeOption: [],
      rules: {
        name: [{ required: true, message: '请输入股东名称', trigger: 'blur' }],
        duty: [{ required: true, message: '请输入担任职务', trigger: 'blur' }],
        certtype: [{ required: true, message: '请输入证件类型', trigger: 'blur' }],
        certid: [{ required: true, message: '请输入证件编号', trigger: 'blur' }],
        status: [{ required: true, message: '请输入是否有效', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curentmanagerid : function(newV, oldV){
      this.initData();
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','PersonCertType','Duty','Education','Sex','IsNot']}).then(response => {
      this.codemap = response.data
      this.certTypeOption = [{
        label: '企业证件类型',
        options: this.codemap.EntCertType,
      },{
        label: '个人证件类型',
        options: this.codemap.PersonCertType,
      }]
      this.initData()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    initData() {
      if (this.curentmanagerid) {
        this.queryData();
      } else {
        this.datainfo = {
          customerid: this.curcustomerid
        };
      }
    },
    queryData() {
      this.dataLoading = true
      queryEntManager({id:this.curentmanagerid}).then(response => {
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
          if (this.curentmanagerid) {
            updateEntManager(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.$emit('refreshList')
            })
          } else {
            createEntManager(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.$emit('refreshList')
            })
          }
        }
      })
    }
  }
}
</script>
