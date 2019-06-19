<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">

        <el-row>
          <el-col :span="10">
            <el-form-item label="关联方客户类型" prop="enttype">
              <el-select v-model="datainfo.enttype" placeholder="">
                <el-option
                  v-for="item in codemap.EntScale"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="关联方名称" prop="name">
              <el-input v-model="datainfo.name"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="关联方证件类型" prop="certtype">
              <el-select v-model="datainfo.certtype" placeholder="">
                <el-option
                  v-for="item in codemap.EntCertType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="关联方证件编号" prop="certid">
              <el-input v-model="datainfo.certid"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="关联关系" prop="type">
              <el-select v-model="datainfo.type" placeholder="">
                <el-option
                  v-for="item in codemap.PartnerRelationType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="关系建立时间" prop="occurdate">
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
          <el-col :span="20">
            <el-form-item label="供应（销售）产品" prop="product">
              <el-input type="textarea" :rows="3" v-model="datainfo.product"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="供应（销售）额币种" prop="currency">
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
            <el-form-item label="供应（销售）额" prop="money">
              <el-input v-model="datainfo.money"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="供应（销售）比例" prop="percent">
              <el-input v-model="datainfo.percent"/>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="联系电话" prop="tel">
              <el-input v-model="datainfo.tel"/>
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
            <el-form-item label="是否有效" prop="isinuse">
              <el-select v-model="datainfo.isinuse" placeholder="">
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
import { queryEntRelate,createEntRelate,updateEntRelate } from '@/api/cust/entrelate'
import { queryCodeList } from '@/api/syscode'



export default {
  name: 'EntRelate',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curentrelateid: String
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
        name: [{ required: true, message: '请输入关联方名称', trigger: 'blur' }],
        certtype: [{ required: true, message: '请输入关联方证件类型', trigger: 'blur' }],
        certid: [{ required: true, message: '请输入关联方证件编号', trigger: 'blur' }],
        type: [{ required: true, message: '请输入关联关系', trigger: 'blur' }],
        isinuse: [{ required: true, message: '请输入是否有效', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curentrelateid : function(newV, oldV){
      this.initData();
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','EntScale','PartnerRelationType','Currency','IsNot']}).then(response => {
      this.codemap = response.data
      this.initData()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    initData() {
      if (this.curentrelateid) {
        this.queryData();
      } else {
        this.datainfo = {
          customerid: this.curcustomerid
        };
      }
    },
    queryData() {
      this.dataLoading = true
      queryEntRelate({id:this.curentrelateid}).then(response => {
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
          if (this.curentrelateid) {
            updateEntRelate(this.datainfo).then((res) => {
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.$emit('refreshList')
            })
          } else {
            createEntRelate(this.datainfo).then((res) => {
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
