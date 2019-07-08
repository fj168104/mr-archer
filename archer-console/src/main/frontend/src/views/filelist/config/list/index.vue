<template>
  <el-container class="app-container">
    <el-header>
      <el-form :inline="true" class="demo-form-inline filter-container">
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
    </el-header>

    <el-main>
      <el-table
        :key="listQuery.tableKey"
        v-loading="listQuery.listLoading"
        :data="listQuery.list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="ID" prop="id" align="center" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="名称" width="300">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="所属类型" align="center" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.type | showCodeName(codemap.FilelistType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="描述">
          <template slot-scope="scope">
            <span>{{ scope.row.remark}}</span>
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
              <el-button @click="editViewData(scope.row)" type="primary">编辑</el-button>
              <el-button @click="ViewData(scope.row)" type="primary">详情</el-button>
              <el-button @click="handleDelete(scope.$index,scope.row)" type="danger">删除</el-button>
            </el-button-group>
          </template>

        </el-table-column>
      </el-table>

      <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </el-main>

    <!-- 新增/编辑窗口 start-->
    <el-dialog :title="'详情'" :visible.sync="editDataDialogVisible">
      <el-form ref="editDataForm" :rules="rules" :model="editData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="名称" prop="name">
          <el-input v-model="editData.name" />
        </el-form-item>
        
        <el-form-item label="所属类型" prop="type">
          <el-select v-model="editData.type" clearable placeholder="">
            <el-option
              v-for="item in codemap.FilelistType"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="描述" prop="remark">
          <el-input type="textarea" rows="3" v-model="editData.remark" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createData()">
            确定
          </el-button>
          <el-button @click="editDataDialogVisible = false">
            取消
          </el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
    <!-- 新增/编辑窗口 end -->

    <!-- 详情窗口 start-->
    <el-dialog top="5vh" :visible.sync="viewDataDialogVisible" :append-to-body="true" :fullscreen="true" v-if="viewDataDialogVisible" :show-close="false">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">详情-{{curname}}</span>
          <el-button type="primary" style="float: right;" @click="closeView()">返回</el-button>
        </div>
      </template>
      <FilelistConfgiView :configid="configid" @closeView="closeView"></FilelistConfgiView>
    </el-dialog>
    <!-- 详情窗口 end-->
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFilelistConfigList, createFilelistConfig, updateFilelistConfig, deleteFilelistConfig } from '@/api/filelist/filelistconfig'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'
import FilelistConfgiView from '@/views/filelist/config/filelistconfigview'

export default {
  name: 'FilelistConfigList',
  components: { Pagination,FilelistConfgiView },
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
      edittype: 'Add',
      editData: {
        id: '',
        type: '',
        name: '',
        remark: ''
      },
      editDataDialogVisible: false,
      viewDataDialogVisible: false,
      configid: '',
      curname: '',
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择所属类型', trigger: 'blur' }]
      }
    }
  },
  created() {
    queryCodeList({codelist:['FilelistType']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryFilelistConfigList(this.listQuery).then(response => {
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
      this.editData = {
        id: '',
        type: '',
        name: '',
        remark: ''
      }
    },
    handleCreate() {
      this.resetNewData()
      this.edittype = "Add"
      this.editDataDialogVisible = true
      this.$nextTick(() => {
        this.$refs['editDataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['editDataForm'].validate((valid) => {
        if (valid) {
          if (this.edittype == "Add") {
            createFilelistConfig(this.editData).then((res) => {
              this.editDataDialogVisible = false
              this.getList()
              this.$notify({
                title: 'Success',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            updateFilelistConfig(this.editData).then((res) => {
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
        }
      })
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该信息？', '提示', confirm).then(() => {
        deleteFilelistConfig(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    editViewData(row) {
      this.editData = row
      this.edittype = "Update"
      this.editDataDialogVisible = true;
    },
    ViewData(row) {
      this.configid = row.id
      this.curname = row.name
      this.viewDataDialogVisible = true;
    },
    closeView(){
      this.viewDataDialogVisible = false;
    }
  }
}
</script>