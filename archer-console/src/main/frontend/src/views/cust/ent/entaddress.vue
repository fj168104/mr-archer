<template>
  <div class="app-container">

    <el-form ref="dataInfoForm" v-loading="dataLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">

        <el-row>
          <el-col :span="16">
            <el-form-item label="地址类型" prop="addtype">
              <el-radio-group v-model="datainfo.addtype">
                <el-radio v-for="item in codemap.AddressType" :key="item.value" :label="item.value">{{item.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="所属国家" prop="country">
              <el-select v-model="datainfo.country" clearable placeholder="">
                <el-option
                  v-for="item in codemap.CountryCode"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="所属城市" prop="city">
              <el-cascader
                  :options="codemap.AreaCode"
                  :show-all-levels="false"
                  v-model="curareacode"
                ></el-cascader>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="16">
            <el-form-item label="地址详情" prop="address">
              <el-input type="textarea" :row="3" v-model="datainfo.address"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="10">
            <el-form-item label="邮政编码" prop="zipcode">
              <el-input v-model="datainfo.zipcode"/>
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
import { queryEntAddress,createEntAddress,updateEntAddress } from '@/api/cust/entaddress'
import { queryCodeList } from '@/api/syscode'


export default {
  name: 'EntAddress',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    curid: String,
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
      curareacode: [],
      rules: {
        addtype: [{ required: true, message: '请输入地址类型', trigger: 'blur' }],
        country: [{ required: true, message: '请输入所属国家', trigger: 'blur' }],
        city: [{ required: true, message: '请输入所属城市', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址详情', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curareacode: function(newV, oldV) {
      this.datainfo.city = newV[newV.length - 1];
    }
  },
  created() {
    queryCodeList({codelist:['AddressType','CountryCode','AddressType'],treelist:['AreaCode']}).then(response => {
      this.codemap = response.data
      this.initData()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getCurTreeList(src, length){
      let a = [];
      if (src) {
        const levellenght = src.length/length;
        for (let i =0;i<levellenght;i++) {
          a[i] = src.substring(0, length * (i+1));
        }
      }
      return a;
    },
    initData() {
      if (this.curid) {
        this.queryData();
      } else {
        this.datainfo = {
          customerid: this.curcustomerid
        };
      }
    },
    queryData() {
      this.dataLoading = true
      queryEntAddress({id:this.curid}).then(response => {
        this.datainfo = response.data
        this.curareacode = this.getCurTreeList(this.datainfo.city, 2);
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
            updateEntAddress(this.datainfo).then((res) => {
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
            createEntAddress(this.datainfo).then((res) => {
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
