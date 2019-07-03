<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">

        <el-row>
          <el-col :span="10">
            <el-form-item label="家族关系" prop="relation">
              <el-select v-model="datainfo.relation" clearable placeholder="">
                <el-option
                  v-for="item in codemap.FamilyRelationshipCode"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="成员名称" prop="name">
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
            <el-form-item label="所在企业名称" prop="company">
              <el-input v-model="datainfo.company"/>
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
        <el-button v-if="isedit" type="primary" :loading="btnLoading" @click="updateData()">
          保存
        </el-button>
      </div>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryEntMember,createEntMember,updateEntMember } from '@/api/cust/entmember'
import { queryCodeList } from '@/api/syscode'



export default {
  name: 'EntMember',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curentmemberid: String,
    isedit: Boolean
  },
  data() {
    return {
      dataLoading : false,
      btnLoading: false,
      datainfo: {
      },
      codemap: {
      },
      certTypeOption: [],
      rules: {
        relation: [{ required: true, message: '请输入家族关系', trigger: 'blur' }],
        name: [{ required: true, message: '请输入家族成员名称', trigger: 'blur' }],
        certtype: [{ required: true, message: '请输入证件类型', trigger: 'blur' }],
        certid: [{ required: true, message: '请输入证件编号', trigger: 'blur' }],
        status: [{ required: true, message: '请输入是否有效', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curentmemberid : function(newV, oldV){
      this.initData();
    }
  },
  created() {
    queryCodeList({codelist:['PersonCertType','FamilyRelationshipCode','IsNot']}).then(response => {
      this.codemap = response.data
      this.initData()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    initData() {
      if (this.curentmemberid) {
        this.queryData();
      } else {
        this.datainfo = {
          customerid: this.curcustomerid
        };
      }
    },
    queryData() {
      this.dataLoading = true
      queryEntMember({id:this.curentmemberid}).then(response => {
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
          if (this.curentmemberid) {
            updateEntMember(this.datainfo).then((res) => {
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
            createEntMember(this.datainfo).then((res) => {
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
