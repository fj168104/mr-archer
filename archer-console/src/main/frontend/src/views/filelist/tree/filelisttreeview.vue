<template>
  <el-row>
    <el-col :span="10">
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
            @cell-click="chooseTree"
            border
            fit
            highlight-current-row
            style="width: 100%;"
          >

            <el-table-column label="名称">
              <template slot-scope="scope">
                <span>{{ scope.row.name }}</span>
              </template>
            </el-table-column>

            <el-table-column label="排序号" width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.sortno}}</span>
              </template>
            </el-table-column>

            <el-table-column label="是否可用" align="center" width="80">
              <template slot-scope="scope">
                <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot)}}</span>
              </template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width" fixed="right">

              <template slot-scope="scope">
                <el-button-group>
                  <el-button @click="editViewData(scope.row)" type="primary">编辑</el-button>
                  <el-button @click="handleDelete(scope.$index,scope.row)" type="danger">删除</el-button>
                </el-button-group>
              </template>

            </el-table-column>
          </el-table>

          <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
        </el-main>

        <!-- 新增/编辑窗口 start-->
        <el-dialog :title="'详情'" :append-to-body="true" :visible.sync="editDataDialogVisible">
          <el-form ref="editDataForm" :rules="rules" :model="editData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

            <el-form-item label="名称" prop="name">
              <el-input v-model="editData.name" />
            </el-form-item>
            
            <el-form-item label="排序号" prop="sortno">
              <el-input v-model="editData.sortno" />
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
      </el-container>
  
    </el-col>
    <el-col :span="14">
      <FileListTreeNodeRelaList :configid="configid" :treeid="curid" v-if="curid != ''"></FileListTreeNodeRelaList>
    </el-col>
  </el-row>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFilelistTreeList, createFilelistTree, updateFilelistTree, deleteFilelistTree } from '@/api/filelist/filelisttree'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'
import FileListTreeNodeRelaList from '@/views/filelist/tree/filelisttreenoderelalist'

export default {
  name: 'FilelistTreeView',
  components: { Pagination,FileListTreeNodeRelaList },
  directives: { waves },
  filters: {},
  props:{
    belongtype: String,
    belongid: String,
    configid: String
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
          configid: this.configid,
          name: ''
        },
        list: null
      },
      codemap : {},
      edittype: 'Add',
      editData: {
        configid: this.configid,
        name: '',
        isinuse: '',
        sortno: ''
      },
      editDataDialogVisible: false,
      curid: '',
      curname: '',
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        isinuse: [{ required: true, message: '请选择是否可用', trigger: 'blur' }],
        sortno: [{ required: true, message: '请输入排序号', trigger: 'blur' }]
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
      queryFilelistTreeList(this.listQuery).then(response => {
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
        configid: this.configid,
        isinuse: '',
        name: '',
        sortno: ''
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
            createFilelistTree(this.editData).then((res) => {
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
            updateFilelistTree(this.editData).then((res) => {
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
        deleteFilelistTree(row.id).then(res => {
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
    chooseTree(row,column,cell){
      // console.log(row);
      // console.log(column);
      // console.log(cell);
      if (column.label != "操作") {
        this.curid = row.id
        this.curname = row.name
      }
    }
  }
}
</script>