<template>
  <div class="app-container">
    <!-- <div class="filter-container">
      <el-input v-model="listQuery.filters.name" placeholder="客户姓名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.filters.certid" placeholder="证件号码" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新增客户
      </el-button>
    </div> -->
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="客户姓名">
          <el-input v-model="listQuery.filters.name" placeholder="客户姓名" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item label="证件号码">
          <el-input v-model="listQuery.filters.certid" placeholder="证件号码" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
              新增客户
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
      <el-table-column label="ID" prop="id" align="center" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="客户名称" align="center" width="300">
        <template slot-scope="scope">
          <span @click="gotoView(scope.row)">{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="证件类型"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.certtype }}</span>
        </template>
      </el-table-column>

      <el-table-column label="证件号码"  align="center" >
        <template slot-scope="scope">
          <span>{{ scope.row.certid }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间"  align="center" >
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">

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

    <!-- 新增客户窗口 start-->
    <el-dialog :title="'新增'" :visible.sync="newDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="100px" style="width: 50%; margin-left:50px;">

        <el-form-item label="客户名称" prop="name">
          <el-input v-model="newData.name" />
        </el-form-item>

        <el-form-item label="证件类型" prop="certtype">
          <el-select v-model="newData.certtype" class="filter-item" placeholder="请选择">
            <el-option v-for="item in certTypeOptions" :key="item.key" :label="item.name" :value="item.key" />
          </el-select>
        </el-form-item>

        <el-form-item label="证件号码" prop="certid">
          <el-input v-model="newData.certid" />
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
    <!-- 新增客户窗口 end -->

    <el-dialog :title="'客户详情'" :visible.sync="viewDataDialogVisible" width="100%" :v-if="viewDataDialogVisible">
      <ent-view :curcustomerid="curcustomerid"></ent-view>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryDataList, createData, deleteData } from '@/api/cust/xw'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import EntView from '@/views/cust/ent/entview' // secondary package based on el-pagination


const certTypeOptions = [
  { key: '0', name: '统一社会信用证代码' },
  { key: '1', name: '营业执照' }
]

export default {
  name: 'CustXwList',
  components: { Pagination,EntView },
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
          name: '',
          certid:''
        },
        list: null
      },
      certTypeOptions,
      newData: {
        name: '',
        certtype: '',
        certid: ''
      },
      newDataDialogVisible: false,
      viewDataDialogVisible: false,
      curcustomerid:'',
      rules: {
        name: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        certtype: [{ required: true, message: '请输入证件类型', trigger: 'blur' }],
        certid: [{ required: true, message: '请输入证件号码', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryDataList(this.listQuery).then(response => {
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
        certtype: '',
        certid: ''
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
          createData(this.newData).then((res) => {
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
      this.$confirm('您确定要永久删除该客户？', '提示', confirm).then(() => {
        deleteData(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curcustomerid = row.id;
      this.viewDataDialogVisible = true;
    }
  }
}
</script>