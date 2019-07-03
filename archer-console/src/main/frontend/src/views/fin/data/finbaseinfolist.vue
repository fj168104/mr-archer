<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="报表日期">
          <el-date-picker v-model="listQuery.filters.findate" type="month" value-format="yyyy-MM" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
          <el-button v-if="isedit" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
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
      <!-- <el-table-column label="ID" prop="id" align="center" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column> -->

      <el-table-column label="报表日期" fixed="left">
        <template slot-scope="scope">
          <span>{{ scope.row.findate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="报表口径" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.scope | showCodeName(codemap.FinReportScope)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="报表周期" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.period | showCodeName(codemap.FinReportPeriod)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="币种" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.currency | showCodeName(codemap.Currency)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="报表单元" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.unit | showCodeName(codemap.FinReportUnit)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="报表状态" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.status | showCodeName(codemap.FinReportStatus)}}</span>
        </template>
      </el-table-column>
      
      <!-- <el-table-column label="创建人"  align="center" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createuser }}</span>
        </template>
      </el-table-column> -->

      <el-table-column label="创建时间" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="创建机构"  align="center" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createorg }}</span>
        </template>
      </el-table-column> -->

      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <el-tooltip content="详情" placement="top">
            <el-button @click="viewData(scope.row)" size="medium" type="primary" icon="el-icon-edit" circle plain></el-button>
          </el-tooltip>

          <el-tooltip v-if="isedit" content="删除" placement="top">
            <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 新增窗口 start-->
    <el-dialog :title="'新增'" :visible.sync="newDataDialogVisible" :append-to-body="true" >
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="财报模型" prop="findate">
          <el-select v-model="newData.modelid" clearable placeholder="">
            <el-option
              v-for="item in finmodellist"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="财报日期" prop="findate">
          <el-date-picker
            v-model="newData.findate"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        
        <el-form-item label="财报口径" prop="scope">
          <el-select v-model="newData.scope" clearable placeholder="">
            <el-option
              v-for="item in codemap.FinReportScope"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="财报周期" prop="period">
          <el-select v-model="newData.period" clearable placeholder="">
            <el-option
              v-for="item in codemap.FinReportPeriod"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="币种" prop="currency">
          <el-select v-model="newData.currency" clearable placeholder="">
            <el-option
              v-for="item in codemap.Currency"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="财报单位" prop="unit">
          <el-select v-model="newData.unit" clearable placeholder="">
            <el-option
              v-for="item in codemap.FinReportUnit"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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

    <el-dialog :title="'报表详情'" :show-close="false" :append-to-body="true" :fullscreen="true" :visible.sync="viewDataDialogVisible" v-if="viewDataDialogVisible">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">报表详情</span>
          <el-button type="primary" style="float: right;" @click="viewDataDialogVisible = false">返回</el-button>
        </div>
      </template>
      <fin-base-info-view :isedit="isedit" :curid="curid" :curfinmodelid="curfinmodelid" @refreshList="getList"></fin-base-info-view>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFinBaseInfoList, createFinBaseInfo, deleteFinBaseInfo } from '@/api/fin/finbaseinfo'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import FinBaseInfoView from '@/views/fin/data/finbaseinfoview'
import { queryCodeList } from '@/api/syscode'
import { queryFinModelCodeList } from '@/api/fin/finmodel'


export default {
  name: 'FinBaseInfoList',
  components: { Pagination,FinBaseInfoView },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
    isedit: Boolean
  },
  data() {
    return {
      listQuery: {
        tableKey: 0,
        listLoading: true,
        page: 1,
        total: 0,
        limit: 10,
        filters:{
          customerid: this.curcustomerid
        },
        list: null
      },
      codemap: {},
      finmodellist:[],
      newData: {
        customerid: this.curcustomerid,
        modelid: '',
        findate: '',
        scope: '',
        period: '',
        currency: '',
        unit: '',
        remark: ''
      },
      rules: {
        findate: [{ required: true, message: '请输入报表日期', trigger: 'blur' }],
        scope: [{ required: true, message: '请输入报表口径', trigger: 'blur' }],
        period: [{ required: true, message: '请输入报表周期', trigger: 'blur' }],
        currency: [{ required: true, message: '请输入币种', trigger: 'blur' }],
        unit: [{ required: true, message: '请输入报表单元', trigger: 'blur' }]
      },
      newDataDialogVisible: false,
      viewDataDialogVisible: false,
      curid : '',
      curfinmodelid : ''
    }
  },
  created() {
    queryCodeList({codelist:['FinReportScope','FinReportPeriod','Currency','FinReportUnit','IsNot','FinReportStatus']}).then(response => {
      this.codemap = response.data
      queryFinModelCodeList({}).then(response => {
        this.finmodellist = response.data
        this.getList()
      }).catch(() => {
        this.$message.info("获取数据失败！")
      })
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.viewDataDialogVisible = false
      this.listQuery.listLoading = true
      queryFinBaseInfoList(this.listQuery).then(response => {
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
        customerid: this.curcustomerid,
        modelid: '',
        findate: '',
        scope: '',
        period: '',
        currency: '',
        unit: '',
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
          createFinBaseInfo(this.newData).then((res) => {
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
        deleteFinBaseInfo(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curid = row.id
      this.curfinmodelid = row.modelid
      this.viewDataDialogVisible = true
    }
  }
}
</script>