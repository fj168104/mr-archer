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
      <el-table-column label="ID" prop="id" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="名称">
        <template slot-scope="scope">
          <a @click="viewData(scope.row)">{{ scope.row.name }}</a>
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
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="名称" prop="name">
          <el-input v-model="newData.name" />
        </el-form-item>
        
        <el-form-item label="财报数据类型" prop="findatatype">
          <el-select v-model="newData.findatatype" clearable placeholder="">
            <el-option
              v-for="item in codemap.FinDataType"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="分栏数" prop="cols">
          <el-input v-model="newData.cols" />
        </el-form-item>
        
        <el-form-item label="描述" prop="configdesc">
          <el-input type="textarea" :row="3" v-model="newData.configdesc" />
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
    <el-dialog top="5vh" :visible.sync="viewDataDialogVisible" :fullscreen="true" :v-if="viewDataDialogVisible" :show-close="false">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">详情-{{curname}}</span>
          <el-button type="primary" style="float: right;" @click="closeView()">返回</el-button>
        </div>
      </template>
      <fin-config-view :curid="curid" :curcols="curcols" @closeView="closeView"></fin-config-view>
    </el-dialog>
    <!-- 详情窗口 end-->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFinConfigList, createFinConfig, deleteFinConfig } from '@/api/fin/finconfig'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import FinConfigView from '@/views/fin/config/finconfigview'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'FinConfigList',
  components: { Pagination,FinConfigView },
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
        findatatype: '',
        cols: '',
        configdesc: ''
      },
      newDataDialogVisible: false,
      viewDataDialogVisible: false,
      curid: '',
      curcols: 0,
      curname: '',
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        findatatype: [{ required: true, message: '请输入财报数据类型', trigger: 'blur' }],
        cols: [{ required: true, message: '请输入分栏数', trigger: 'blur' }]
      }
    }
  },
  created() {
    queryCodeList({codelist:['FinDataType']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryFinConfigList(this.listQuery).then(response => {
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
        findatatype: '',
        cols: '',
        configdesc: ''
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
          createFinConfig(this.newData).then((res) => {
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
        deleteFinConfig(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curid = row.id;
      this.curcols = row.cols;
      this.curname = row.name;
      this.viewDataDialogVisible = true;
    },
    closeView(){
      this.viewDataDialogVisible = false;
    }
  }
}
</script>