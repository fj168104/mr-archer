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

        <el-table-column label="节点名称">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="文件类型" align="center" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.filetype | showCodeName(codemap.FileType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="数量限制" align="center" width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.count > 0">{{ scope.row.count}}</span>
          </template>
        </el-table-column>

        <el-table-column label="是否必输" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.isrequired | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="关联是否可用" align="center" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column>

        <!-- <el-table-column label="节点是否可用" align="center" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.nodeisinuse | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column> -->

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

        <el-form-item label="节点名称" prop="name">
          <el-input v-model="editData.name" :readonly="true">
            <el-button slot="append" icon="el-icon-search" @click="chooseDataDialogVisible = true"></el-button>
          </el-input>
        </el-form-item>
        
        <el-form-item label="排序号" prop="sortno">
          <el-input v-model="editData.sortno" />
        </el-form-item>
        
        <el-form-item label="触发事件" prop="event">
          <el-input v-model="editData.event" />
        </el-form-item>
        
        <el-form-item label="是否必输" prop="isrequired">
          <el-select v-model="editData.isrequired" clearable placeholder="">
            <el-option
              v-for="item in codemap.IsNot"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="关联是否可用" prop="isinuse">
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

    <el-dialog :title="'资料清单节点列表'" :append-to-body="true" :visible.sync="chooseDataDialogVisible" v-if="chooseDataDialogVisible" width="70%">
      <ChooseFilelistNodeList @confirmChooseFilelistNode="confirmChooseFilelistNode"></ChooseFilelistNodeList>
    </el-dialog>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFilelistTreeNodeRelaList, createFilelistTreeNodeRela, updateFilelistTreeNodeRela, deleteFilelistTreeNodeRela } from '@/api/filelist/filelisttreenoderela'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'

import ChooseFilelistNodeList from '@/views/filelist/node/choosefilelistnodelist'

export default {
  name: 'FilelistTreeNodeRelaList',
  components: { Pagination,ChooseFilelistNodeList },
  directives: { waves },
  filters: {},
  props:{
    configid: String,
    treeid: String
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
          treeid: this.treeid,
          name: ''
        },
        list: null
      },
      codemap : {},
      edittype: 'Add',
      editData: {
        treeid: this.treeid,
        name: '',
        isinuse: '',
        sortno: '',
        event: '',
        isrequired: ''
      },
      editDataDialogVisible: false,
      chooseDataDialogVisible: false,
      rules: {
        name: [{ required: true, message: '请选择节点名称', trigger: 'blur' }],
        isinuse: [{ required: true, message: '请选择是否可用', trigger: 'blur' }]
      }
    }
  },
  watch:{
    treeid: function(newv, oldv){
      this.listQuery.filters.treeid = newv
      this.getList()
    }
  },
  created() {
    queryCodeList({codelist:['IsNot','FileType']}).then(response => {
      this.codemap = response.data
      if (this.treeid) {
        this.getList()
      } else {
        this.listQuery.listLoading = false
      }
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryFilelistTreeNodeRelaList(this.listQuery).then(response => {
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
        treeid: this.treeid,
        name: '',
        isinuse: '',
        sortno: '',
        event: '',
        isrequired: ''
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
            createFilelistTreeNodeRela(this.editData).then((res) => {
              this.editDataDialogVisible = false
              this.getList()
              this.$notify({
                title: 'Success',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
            }).catch(() => {
              this.$message.info("处理异常！")
              this.editDataDialogVisible = false
            });
          } else {
            updateFilelistTreeNodeRela(this.editData).then((res) => {
              this.editDataDialogVisible = false
              this.getList()
              this.$notify({
                title: 'Success',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
            }).catch(() => {
              this.$message.info("处理异常！")
              this.editDataDialogVisible = false
            });
          }
        }
      })
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该信息？', '提示', confirm).then(() => {
        deleteFilelistTreeNodeRela(row.id).then(res => {
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
    confirmChooseFilelistNode(data) {
      this.editData.nodeid = data.id
      this.editData.name = data.name
      this.chooseDataDialogVisible = false
    }
  }
}
</script>