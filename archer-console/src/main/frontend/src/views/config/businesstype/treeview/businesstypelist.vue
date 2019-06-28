<template>
  <el-container>

    <el-header height="100px">
      <el-divider content-position="left">【{{typename}}】</el-divider>
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
        <el-table-column label="ID" prop="id" align="center" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="业务品种名称">
          <template slot-scope="scope">
            <a @click="viewData(scope.row)">{{ scope.row.name }}</a>
          </template>
        </el-table-column>

        <el-table-column label="是否有效" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot)}}</span>
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
              <el-button @click="viewData(scope.row)" type="primary" size="mini">详情</el-button>
              <el-button @click="handleDelete(scope.$index,scope.row)" type="danger" size="mini">删除</el-button>
            </el-button-group>
          </template>

        </el-table-column>
      </el-table>

      <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </el-main>

    <!-- 新增窗口 start-->
    <el-dialog :title="'新增'" :visible.sync="newDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="名称" prop="name">
          <el-input v-model="newData.name" />
        </el-form-item>
        
        <el-form-item label="是否有效" prop="isinuse">
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

    <!-- 详情窗口 start-->
    <el-dialog top="5vh" :visible.sync="viewDataDialogVisible" :fullscreen="true" :v-if="viewDataDialogVisible" :show-close="false">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">详情-{{curname}}</span>
          <el-button type="primary" style="float: right;" @click="closeView()">返回</el-button>
        </div>
      </template>
      <!-- <fin-config-view :curid="curid" :curcols="curcols" @closeView="closeView"></fin-config-view> -->
    </el-dialog>
    <!-- 详情窗口 end-->
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryBusinessTypeList, createBusinessType, updateBusinessType, deleteBusinessType } from '@/api/config/businesstype'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'BusinessTypeList',
  components: { Pagination },
  directives: { waves },
  filters: {},
  props:{
    type: String,
    typename: String
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
          type: this.type,
          name: ''
        },
        list: null
      },
      codemap : {},
      newData: {
        type: this.type,
        name: '',
        isinuse: '1'
      },
      newDataDialogVisible: false,
      viewDataDialogVisible: false,
      curid: '',
      curname: '',
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        isinuse: [{ required: true, message: '请输入是否有效', trigger: 'blur' }]
      }
    }
  },
  watch: {
    type: function(newv, oldv){
      this.listQuery.filters = {
        type: this.type,
        name: ''
      }
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
    getList() {
      this.listQuery.listLoading = true
      queryBusinessTypeList(this.listQuery).then(response => {
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
        type: this.type,
        name: '',
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
          createBusinessType(this.newData).then((res) => {
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
        deleteBusinessType(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curid = row.id;
      this.curname = row.name;
      this.viewDataDialogVisible = true;
    },
    closeView(){
      this.viewDataDialogVisible = false;
    }
  }
}
</script>