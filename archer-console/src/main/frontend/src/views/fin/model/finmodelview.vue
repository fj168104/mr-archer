<template>
  <el-collapse v-model="activeNames">
    <el-collapse-item title="基本信息" name="1">
      <el-form ref="dataInfoForm" v-loading="infoLoading" :rules="rules" :model="datainfo" label-position="left" label-width="160px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="名称" prop="name">
              <el-input v-model="datainfo.name"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :row="3" v-model="datainfo.remark"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveFinModel()">保存</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-collapse-item>
    <el-collapse-item title="关联配置列表" name="2">
      <el-row>
        <el-col :span="24" align="right" style="padding:10px">
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="saveList">
            保存
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
            新增
          </el-button>
        </el-col>
      </el-row>

      <el-table
        :key="1"
        v-loading="listLoading"
        :data="datalist"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="排序号" prop="sortno">
          <template slot-scope="scope">
            <input v-model="scope.row.sortno"/>
          </template>
        </el-table-column>

        <el-table-column label="ID" prop="id" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="名称">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="财报数据类型">
          <template slot-scope="scope">
            <span>{{ scope.row.findatatype | showCodeName(codemap.FinDataType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="分栏数">
          <template slot-scope="scope">
            <span>{{ scope.row.cols }}</span>
          </template>
        </el-table-column>

        <el-table-column label="描述">
          <template slot-scope="scope">
            <span>{{ scope.row.configdesc }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建时间"  align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-tooltip content="删除" placement="top">
              <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 新增窗口 start-->
      <el-dialog :title="'新增'" :append-to-body="true" :visible.sync="newDataDialogVisible" width="70%">
        <choose-fin-config-list @confirmChooseConfig="confirmChooseConfig"></choose-fin-config-list>
      </el-dialog>
      <!-- 新增窗口 end -->
    </el-collapse-item>
  </el-collapse>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { updateFinModel } from '@/api/fin/finmodel'
import { queryFinModelConfigRefList,createFinModelConfigRef,updateFinModelConfigRefList,deleteFinModelConfigRef } from '@/api/fin/finmodelconfigref'
import ChooseFinConfigList from '@/views/fin/config/choosefinconfiglist'

export default {
  name: 'FinModelView',
  components: { ChooseFinConfigList },
  directives: { waves },
  filters: {},
  props: {
    curid: String,
    curname: String,
    curremark: String
  },
  data: function() {
    return {
      newDataDialogVisible: false,
      activeNames:["1","2"],
      infoLoading: false,
      datainfo: {
        id: this.curid,
        name: this.curname,
        remark: this.curremark
      },
      listLoading: false,
      datalist:[],
      rules:{
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      }
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
    saveFinModel(){
       this.$refs['dataInfoForm'].validate((valid) => {
        if (valid) {
          this.infoLoading = true
          updateFinModel(this.datainfo).then(response => {
            this.infoLoading = false
            this.$notify({
              title: 'Success',
              message: '保存成功',
              type: 'success',
              duration: 2000
            })
          }).catch(() => {
            this.$message.info("获取数据失败！")
            this.infoLoading = false
          })
        }
      })
    },
    getList(){
      this.listLoading = true
      queryFinModelConfigRefList({modelid: this.curid}).then(response => {
        this.datalist = response.data
        this.listLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.listLoading = false
      })
    },
    handleCreate(){
      this.newDataDialogVisible = true
    },
    saveList(){
      this.listLoading = true
      updateFinModelConfigRefList(this.datalist).then(response => {
        this.listLoading = false
        this.$notify({
          title: 'Success',
          message: '保存成功',
          type: 'success',
          duration: 2000
        })
      }).catch(() => {
        this.$message.info("保存数据失败！")
        this.listLoading = false
      })
    },
    handleDelete(row,i,j){
      if (row.id) {
        this.listLoading = true
        deleteFinModelConfigRef(row.id).then(response => {
          this.datalist[i].splice(j,1);
          this.listLoading = false
          this.$notify({
            title: 'Success',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        }).catch(() => {
          this.$message.info("删除数据失败！")
          this.listLoading = false
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
    },
    confirmChooseConfig(configid){
      let data = {
        modelid: this.curid,
        configid: configid
      }
      this.listLoading = true
      createFinModelConfigRef(data).then(response => {
        this.listLoading = false
        this.getList()
        this.$notify({
          title: 'Success',
          message: '保存成功',
          type: 'success',
          duration: 2000
        })
      }).catch(() => {
        this.$message.info("保存数据失败！")
        this.listLoading = false
      })
    }
  }
}
</script>