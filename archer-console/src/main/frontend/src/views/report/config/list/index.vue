<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="listQuery.filters.name" placeholder="名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
              新增
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      :key="listQuery.tableKey"
      v-loading="listQuery.listLoading"
      :data="listQuery.list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="名称">
        <template slot-scope="scope">
          <a @click="viewData(scope.row)">{{ scope.row.name }}</a>
        </template>
      </el-table-column>

      <el-table-column label="是否可用" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="备注">
        <template slot-scope="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <el-tooltip content="详情" placement="top">
            <el-button @click="viewData(scope.row)" size="medium" type="primary" icon="el-icon-edit" circle plain></el-button>
          </el-tooltip>

          <el-tooltip content="删除" placement="top">
            <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 新增窗口 start-->
    <el-dialog :title="'新增'" :visible.sync="newDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="100px" style="width: 50%; margin-left:50px;">

        <el-form-item label="名称" prop="name">
          <el-input v-model="newData.name" />
        </el-form-item>
        
        <el-form-item label="是否可用" prop="isinuse">
          <el-select v-model="newData.isinuse" clearable placeholder="">
            <el-option
              v-for="item in codemap.IsNot"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :row="3" v-model="newData.remark" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createData()">
            确定
          </el-button>
          <el-button @click="newDataDialogVisible = false">
            取消
          </el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
    <!-- 新增窗口 end -->

    <!-- 详情窗口 start-->
    <el-dialog top="5vh" :visible.sync="viewDataDialogVisible" :fullscreen="true" v-if="viewDataDialogVisible" :show-close="false">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">详情-{{curdata.name}}</span>
          <el-button type="primary" style="float: right;" @click="closeView()">返回</el-button>
        </div>
      </template>
      <report-config-view :curdata="curdata" @closeView="closeView"></report-config-view>
    </el-dialog>
    <!-- 详情窗口 end-->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryReportConfigList, createReportConfig, deleteReportConfig } from '@/api/report/reportconfig'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import ReportConfigView from '@/views/report/config/reportconfigview'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'ReportConfigList',
  components: { Pagination,ReportConfigView },
  directives: { waves },
  filters: {},
  data() {
    return {
      listQuery: {
        tableKey: 0,
        listLoading: true,
        page: 1,
        total: 0,
        limit: 10,
        filters:{
          name: ''
        },
        list: null
      },
      codemap : {},
      newData: {
        name: '',
        isinuse: '1',
        remark: ''
      },
      newDataDialogVisible: false,
      viewDataDialogVisible: false,
      curdata: {},
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      }
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
    getList() {
      this.listQuery.listLoading = true
      queryReportConfigList(this.listQuery).then(response => {
        this.listQuery.list = response.data.records
        this.listQuery.total = response.data.total
        this.listQuery.listLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.listQuery.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetNewData() {
      this.newData = {
        name: '',
        isinuse: '1',
        remark: ''
      }
    },
    handleCreate() {
      this.resetNewData()
      this.newDataDialogVisible = true
      this.$nextTick(() => {
        this.$refs['newDataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['newDataForm'].validate((valid) => {
        if (valid) {
          createReportConfig(this.newData).then((res) => {
            this.newDataDialogVisible = false
            this.getList()
            this.$notify({
              title: 'Success',
              message: '新增成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该信息？', '提示', confirm).then(() => {
        deleteReportConfig(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curdata = row
      this.viewDataDialogVisible = true
    },
    closeView(){
      this.viewDataDialogVisible = false
      this.getList()
    }
  }
}
</script>