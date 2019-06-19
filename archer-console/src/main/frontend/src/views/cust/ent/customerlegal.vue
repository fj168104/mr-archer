<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">

        <el-row>
          <el-col :span="10">
            <el-form-item label="起诉人名称" prop="name">
              <el-input v-model="datainfo.name"/>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="判决执行日期" prop="occurdate">
              <el-date-picker
                v-model="datainfo.occurdate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="币种" prop="currency">
              <el-select v-model="datainfo.currency" placeholder="">
                <el-option
                  v-for="item in codemap.Currency"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="判决执行金额" prop="money">
              <el-input v-model="datainfo.money"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="20">
            <el-form-item label="执行结果" prop="result">
              <el-input type="textarea" :row="4" v-model="datainfo.result"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="20">
            <el-form-item label="被起诉原因" prop="reason">
              <el-input type="textarea" :row="4" v-model="datainfo.reason"/>
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
              <el-select v-model="datainfo.status" placeholder="">
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
import { queryCustomerLegal,createCustomerLegal,updateCustomerLegal } from '@/api/cust/customerlegal'
import { queryCodeList } from '@/api/syscode'



export default {
  name: 'CustomerLegal',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curcustomerlegalid: String
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
        name: [{ required: true, message: '请输入起诉人名称', trigger: 'blur' }],
        currency: [{ required: true, message: '请输入币种', trigger: 'blur' }],
        money: [{ required: true, message: '请输入判决执行金额', trigger: 'blur' }],
        occurdate: [{ required: true, message: '请输入判决执行日期', trigger: 'blur' }],
        status: [{ required: true, message: '请输入是否有效', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curcustomerlegalid : function(newV, oldV){
      this.initData();
    }
  },
  created() {
    queryCodeList({codelist:['Currency','IsNot']}).then(response => {
      this.codemap = response.data
      this.initData()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    initData() {
      if (this.curcustomerlegalid) {
        this.queryData();
      } else {
        this.datainfo = {
          customerid: this.curcustomerid
        };
      }
    },
    queryData() {
      this.dataLoading = true
      queryCustomerLegal({id:this.curcustomerlegalid}).then(response => {
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
          if (this.curcustomerlegalid) {
            updateCustomerLegal(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.$emit('refreshList')
            })
          } else {
            createCustomerLegal(this.datainfo).then((res) => {
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
