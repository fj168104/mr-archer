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
          <el-col :span="6">
            <el-form-item label="是否可用" prop="isinuse">
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
    </el-collapse-item>
    <el-collapse-item title="关联节点配置列表" name="2">
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

        <el-table-column label="节点标题">
          <template slot-scope="scope">
            <span>{{ scope.row.title }}</span>
          </template>
        </el-table-column>

        <el-table-column label="vue路径">
          <template slot-scope="scope">
            <span>{{ scope.row.vuepath }}</span>
          </template>
        </el-table-column>

        <el-table-column label="节点描述">
          <template slot-scope="scope">
            <span>{{ scope.row.nodedesc}}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-tooltip content="删除" placement="top">
              <el-button @click="handleDelete(scope.row,scope.$index)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 新增窗口 start-->
      <el-dialog :title="'新增'" :append-to-body="true" :visible.sync="newDataDialogVisible" width="70%">
        <choose-report-node-list @confirmChoose="confirmChoose"></choose-report-node-list>
      </el-dialog>
      <!-- 新增窗口 end -->
    </el-collapse-item>
  </el-collapse>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { updateReportConfig } from '@/api/report/reportconfig'
import { queryReportConfigRelaList,createReportConfigRela,updateReportConfigRelaList,deleteReportConfigRela } from '@/api/report/reportconfigrela'
import ChooseReportNodeList from '@/views/report/node/choosereportnodelist'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'ReportConfigView',
  components: { ChooseReportNodeList },
  directives: { waves },
  filters: {},
  props: {
    curdata: Object
  },
  data: function() {
    return {
      newDataDialogVisible: false,
      activeNames:["1","2"],
      infoLoading: false,
      datainfo: {
        id: this.curdata.id,
        name: this.curdata.name,
        isinuse: this.curdata.isinuse,
        remark: this.curdata.remark
      },
      codemap:{},
      listLoading: false,
      datalist:[],
      rules:{
        name: [{ required: true, message: '请输入报告名称', trigger: 'blur' }],
        isinuse: [{ required: true, message: '请输入是否可用', trigger: 'blur' }]
      }
    }
  },
  watch: {
    curid : function(newV, oldV){
      this.getList()
    }
  },
  created() {
    queryCodeList({codelist:['IsNot']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    saveFinModel(){
       this.$refs['dataInfoForm'].validate((valid) => {
        if (valid) {
          this.infoLoading = true
          updateReportConfig(this.datainfo).then(response => {
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
      queryReportConfigRelaList({reportid: this.curdata.id}).then(response => {
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
      updateReportConfigRelaList(this.datalist).then(response => {
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
    handleDelete(row,idx){
      if (row.id) {
        this.listLoading = true
        deleteReportConfigRela(row.id).then(response => {
          this.datalist.splice(idx,1);
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
    confirmChoose(nodeid){
      let data = {
        reportid: this.curdata.id,
        nodeid: nodeid
      }
      this.listLoading = true
      createReportConfigRela(data).then(response => {
        this.listLoading = false
        this.newDataDialogVisible = false
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