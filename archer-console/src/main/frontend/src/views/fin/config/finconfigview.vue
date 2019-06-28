<template>
  <el-container v-loading="dataLoading" >
    <el-header>
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="saveList()">保存</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-row>
        <el-col :span="colspan" v-for="(collist,i) in datalist" :key="i">
          <el-row>
            <el-col :span="24">
              <span style="font-weight:bold">【分栏-{{i+1}}】</span>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-row>
                <el-col :span="8" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>名称</span>
                </el-col>
                <el-col :span="2" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>字段名</span>
                </el-col>
                <el-col :span="5" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>计算公式</span>
                </el-col>
                <el-col :span="2" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>序号</span>
                </el-col>
                <el-col :span="3" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>可编辑</span>
                </el-col>
                <el-col :span="2" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>排序号</span>
                </el-col>
                <el-col :span="2" style="padding:5px;font-weight:bold;border: 1px solid #DCDFE6;background-color: rgb(245, 246, 248);">
                  <span>操作</span>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" v-for="(item,j) in collist" :key="j">
              <el-row>
                <el-col :span="8">
                  <el-input v-model="item.name" placeholder=""/>
                </el-col>
                <el-col :span="2">
                  <el-input v-model="item.colname" placeholder=""/>
                </el-col>
                <el-col :span="5">
                  <el-input v-model="item.formulatxt" placeholder=""/>
                </el-col>
                <el-col :span="2">
                  <el-input v-model="item.formulasortno" placeholder=""/>
                </el-col>
                <el-col :span="3" align="center">
                  <!-- <el-radio v-model="item.isedit" label="1">是</el-radio>
                  <el-radio v-model="item.isedit" label="0">否</el-radio> -->
                  <el-radio-group v-model="item.isedit">
                    <el-radio-button label="1">是</el-radio-button>
                    <el-radio-button label="0">否</el-radio-button>
                  </el-radio-group>
                </el-col>
                <el-col :span="2">
                  <el-input v-model="item.sortno" placeholder=""/>
                </el-col>
                <el-col :span="2" align="center">
                  <el-button @click="handleDelete(item,i,j)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" align="center">
              <el-button type="primary" @click="addFinConfigCol(i)">新增</el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { queryFinConfigColList, updateFinConfigColList, deleteFinConfigCol } from '@/api/fin/finconfigcol'


export default {
  name: 'FinConfigView',
  components: {  },
  directives: { waves },
  filters: {},
  props: {
    curid: String,
    curcols: Number
  },
  data: function() {
    return {
      dataLoading: false,
      datalist:[],
      rules:{
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      }
    }
  },
  computed:{
    colspan: function(){
      return 24/this.curcols;
    }
  },
  watch: {
    curid : function(newV, oldV){
      this.getList()
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(){
      this.dataLoading = true
      queryFinConfigColList({configid:this.curid,cols:this.curcols}).then(response => {
        this.datalist = response.data
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    },
    addFinConfigCol(colidx){
      let list = this.datalist[colidx];
      let curDataInfo = {
        id:'',
        configid:this.curid,
        col:colidx,
        name:'',
        tablename:'',
        colname:'',
        sortno:'',
        createuser:'',
        createtime:'',
        createorg:'',
        updateuser:'',
        updatetime:'',
        updateorg:''
      }
      list.push(curDataInfo);
    },
    saveList(){
      this.dataLoading = true
      updateFinConfigColList(this.datalist).then(response => {
        this.datalist = response.data
        this.dataLoading = false
        this.$notify({
          title: 'Success',
          message: '保存成功',
          type: 'success',
          duration: 2000
        })
      }).catch(() => {
        this.$message.info("保存数据失败！")
        this.dataLoading = false
      })
    },
    handleDelete(row,i,j){
      if (row.id) {
        this.dataLoading = true
        deleteFinConfigCol(row.id).then(response => {
          this.datalist[i].splice(j,1);
          this.dataLoading = false
          this.$notify({
            title: 'Success',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        }).catch(() => {
          this.$message.info("删除数据失败！")
          this.dataLoading = false
        })
      } else {
        this.datalist[i].splice(j,1);
        this.$notify({
          title: 'Success',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
      }
    }
  }
}
</script>