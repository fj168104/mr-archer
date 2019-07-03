<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="节点标题">
          <el-input v-model="listQuery.filters.title" placeholder="节点标题" @keyup.enter.native="handleFilter"></el-input>
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

      <el-table-column label="节点标题">
        <template slot-scope="scope">
          <span>{{ scope.row.title }}</span>
        </template>
      </el-table-column>

      <el-table-column label="vue路径" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.vuepath }}</span>
        </template>
      </el-table-column>

      <el-table-column label="节点描述">
        <template slot-scope="scope">
          <span>{{ scope.row.nodedesc }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <el-button-group>
            <el-button @click="editDataInfo(scope.row)" type="primary" size="mini">编辑</el-button>
            <el-button @click="handleDelete(scope.$index,scope.row)" type="danger" size="mini">删除</el-button>
          </el-button-group>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 新增窗口 start-->
    <el-dialog :title="'新增'" :visible.sync="newDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="节点标题" prop="title">
          <el-input v-model="newData.title" />
        </el-form-item>
        
        <el-form-item label="vue路径" prop="vuepath">
          <el-input type="textarea" :row="3" v-model="newData.vuepath" />
        </el-form-item>

        <el-form-item label="节点描述" prop="nodedesc">
          <el-input type="textarea" :row="3" v-model="newData.nodedesc" />
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
    
    <!-- 编辑窗口 start-->
    <el-dialog :title="'修改'" :visible.sync="editDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="editData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="节点标题" prop="title">
          <el-input v-model="editData.title" />
        </el-form-item>

        <el-form-item label="vue路径" prop="vuepath">
          <el-input v-model="editData.vuepath" />
        </el-form-item>
        
        <el-form-item label="节点描述" prop="nodedesc">
          <el-input type="textarea" :row="3" v-model="editData.nodedesc" />
        </el-form-item>
        
        <el-form-item label="是否可用" prop="isinuse">
          <el-select v-model="editData.isinuse" clearable placeholder="">
            <el-option
              v-for="item in codemap.IsNot"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="updateData()">
            确定
          </el-button>
          <el-button @click="editDataDialogVisible = false">
            取消
          </el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
    <!-- 编辑窗口 end -->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryReportNodeList, createReportNode, updateReportNode, deleteReportNode } from '@/api/report/reportnode'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'


export default {
  name: 'ReportNodeList',
  components: { Pagination },
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
          title: ''
        },
        list: null
      },
      codemap : {},
      newData: {
        title: '',
        vuepath: '',
        nodedesc: '',
        isinuse: '1'
      },
      editData:{},
      newDataDialogVisible: false,
      editDataDialogVisible: false,
      viewDataDialogVisible: false,
      curid: '',
      curcols: 0,
      curname: '',
      rules: {
        title: [{ required: true, message: '请输入节点标题', trigger: 'blur' }],
        vuepath: [{ required: true, message: '请输入vue路径', trigger: 'blur' }],
        isinuse: [{required: true, message: '请输入是否可用', trigger: 'blur' }]
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
      queryReportNodeList(this.listQuery).then(response => {
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
        title: '',
        vuepath: '',
        nodedesc: '',
        isinuse: '1'
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
          createReportNode(this.newData).then((res) => {
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
    updateData() {
      this.$refs['newDataForm'].validate((valid) => {
        if (valid) {
          updateReportNode(this.editData).then((res) => {
            this.editDataDialogVisible = false
            this.getList()
            this.$notify({
              title: 'Success',
              message: '保存成功',
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
        deleteReportNode(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    editDataInfo(row) {
      this.editData = JSON.parse(JSON.stringify(row));
      this.editDataDialogVisible = true;
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