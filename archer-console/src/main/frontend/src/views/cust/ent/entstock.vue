<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="100px">

        <el-row>
          <el-col :span="8">
            <el-form-item label="股东名称" prop="name">
              <el-input v-model="datainfo.name"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="企业类型" prop="entkind">
              <el-input v-model="datainfo.entkind"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="证件类型" prop="certtype">
              <el-select v-model="datainfo.certtype" placeholder="">
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
          <el-col :span="8" :offset="4">
            <el-form-item label="证件编号" prop="certid">
              <el-input v-model="datainfo.certid"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="出资方式" prop="type">
              <el-input v-model="datainfo.type"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="出资币种" prop="currency">
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
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="应出资金额" prop="money">
              <el-input v-model="datainfo.money"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="实际投资金额" prop="realmoney">
              <el-input v-model="datainfo.realmoney"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="出资比例(%)" prop="percent">
              <el-input v-model="datainfo.percent"/>
            </el-form-item>
          </el-col>
          <el-col :span="8" :offset="4">
            <el-form-item label="投资日期" prop="occurdate">
              <el-date-picker
                v-model="datainfo.occurdate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="是否有效" prop="scope">
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
import { queryEntStock,createEntStock,updateEntStock } from '@/api/cust/ent'
import { queryCodeList } from '@/api/syscode'



export default {
  name: 'EntStock',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curentstockid: String
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
        name: [{ required: true, message: '请输入股东名称', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curentstockid : function(newV, oldV){
      this.initData();
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','PersonCertType','Currency','IsNot']}).then(response => {
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
          if (this.curentstockid) {
            updateEntStock(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
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
              this.$emit('refreshList')
            })
          }
        }
      })
    }
  }
}
</script>
