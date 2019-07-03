<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">

        <el-row>
          <el-col :span="16">
            <el-form-item label="股东名称" prop="name">
              <el-input v-model="datainfo.name"/>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="10" :offset="2">
            <el-form-item label="企业类型" prop="entkind">
              <el-input v-model="datainfo.entkind"/>
            </el-form-item>
          </el-col> -->
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="证件类型" prop="certtype">
              <el-select v-model="datainfo.certtype" clearable placeholder="">
                <el-option-group
                  v-for="group in certTypeOption"
                  :key="group.label"
                  :label="group.label">
                  <el-option
                    v-for="item in group.options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-option-group>
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
            <el-form-item label="出资方式" prop="type">
              <el-select v-model="datainfo.type" clearable placeholder="">
                <el-option
                  v-for="item in codemap.InvestType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="出资币种" prop="currency">
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
          <el-col :span="10">
            <el-form-item label="应出资金额（元）" prop="money">
              <el-input v-model="datainfo.money"/>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="实际投资金额（元）" prop="realmoney">
              <el-input v-model="datainfo.realmoney"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="出资比例（%）" prop="percent">
              <el-input v-model="datainfo.percent"/>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="投资日期" prop="occurdate">
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
        <el-button v-if="isedit" type="primary" :loading="btnLoading" @click="updateData()">
          保存
        </el-button>
      </div>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryEntStock,createEntStock,updateEntStock } from '@/api/cust/entstock'
import { queryCodeList } from '@/api/syscode'



export default {
  name: 'EntStock',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curentstockid: String,
    isedit: Boolean
  },
  data() {
    return {
      dataLoading: false,
      btnLoading: false,
      datainfo: {
      },
      codemap: {
      },
      certTypeOption: [],
      rules: {
        name: [{ required: true, message: '请输入股东名称', trigger: 'blur' }],
        certtype: [{ required: true, message: '请输入证件类型', trigger: 'blur' }],
        certid: [{ required: true, message: '请输入证件编号', trigger: 'blur' }],
        type: [{ required: true, message: '请输入出资方式', trigger: 'blur' }],
        currency: [{ required: true, message: '请输入出资币种', trigger: 'blur' }],
        money: [{ required: true, message: '请输入应出资金额', trigger: 'blur' }],
        realmoney: [{ required: true, message: '请输入实际投资金额', trigger: 'blur' }],
        percent: [{ required: true, message: '请输入出资比例（%）', trigger: 'blur' }],
        occurdate: [{ required: true, message: '请输入投资日期', trigger: 'blur' }],
        status: [{ required: true, message: '请输入是否有效', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curentstockid : function(newV, oldV){
      this.initData();
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','PersonCertType','InvestType','Currency','IsNot']}).then(response => {
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
      if (this.curentstockid) {
        this.queryData();
      } else {
        this.datainfo = {
          customerid: this.curcustomerid
        };
      }
    },
    queryData() {
      this.dataLoading = true
      queryEntStock({id:this.curentstockid}).then(response => {
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
          this.btnLoading = true
          if (this.curentstockid) {
            updateEntStock(this.datainfo).then((res) => {
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
            createEntStock(this.datainfo).then((res) => {
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
