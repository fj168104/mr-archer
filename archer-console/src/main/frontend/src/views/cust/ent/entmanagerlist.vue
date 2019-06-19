<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="高管名称">
          <el-input v-model="listQuery.filters.name" placeholder="高管名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
              新增高管
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

      <el-table-column label="高管名称" width="300" fixed="left">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="证件类型"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.certtype | showCodeName(codemap.PersonCertType)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="证件号码" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.certid }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="担任职务"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.duty | showCodeName(codemap.Duty)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="是否有效"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.status | showCodeName(codemap.IsNot)}}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="创建人"  align="center" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createuser }}</span>
        </template>
      </el-table-column> -->

      <el-table-column label="创建时间"  align="center" width="180">
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

          <el-tooltip content="删除" placement="top">
            <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="'高管详情'" :append-to-body="true" :visible.sync="viewDataDialogVisible" :v-if="viewDataDialogVisible" width="60%">
      <ent-manager :curentmanagerid="curentmanagerid" :curcustomerid="curcustomerid" @refreshList="getList"></ent-manager>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryEntManagerList, deleteEntManager } from '@/api/cust/entmanager'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import EntManager from '@/views/cust/ent/entmanager'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'EntManagerList',
  components: { Pagination,EntManager },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String,
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
      viewDataDialogVisible: false,
      curentmanagerid : ''
    }
  },
  created() {
    queryCodeList({codelist:['PersonCertType','Duty','IsNot']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.viewDataDialogVisible = false
      this.listQuery.listLoading = true
      queryEntManagerList(this.listQuery).then(response => {
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
    handleCreate() {
      this.curentmanagerid = ""
      this.viewDataDialogVisible = true
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该高管信息？', '提示', confirm).then(() => {
        deleteEntManager(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curentmanagerid = row.id;
      this.viewDataDialogVisible = true;
    }
  }
}
</script>