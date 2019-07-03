<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="投资企业名称">
          <el-input v-model="listQuery.filters.name" placeholder="投资企业名称" @keyup.enter.native="handleFilter"></el-input>
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

      <el-table-column label="投资企业名称" width="300" fixed="left">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="证件类型"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.certtype | showCodeName(codemap.EntCertType)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="证件号码" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.certid }}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="投资方式" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.type | showCodeName(codemap.InvestType)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="出资币种" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.currency | showCodeName(codemap.Currency)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="实际投资金额（元）" align="right" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.realmoney | toThousandFilter}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="是否有效"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot)}}</span>
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

          <el-tooltip v-if="isedit" content="删除" placement="top">
            <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="'对外股权投资详情'" :append-to-body="true" :visible.sync="viewDataDialogVisible" v-if="viewDataDialogVisible" width="70%">
      <ent-invest :isedit="isedit" :curentinvestid="curentinvestid" :curcustomerid="curcustomerid" @refreshList="getList"></ent-invest>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryEntInvestList, deleteEntInvest } from '@/api/cust/entinvest'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import EntInvest from '@/views/cust/ent/entinvest'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'EntInvestList',
  components: { Pagination,EntInvest },
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
      viewDataDialogVisible: false,
      curentinvestid : ''
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','Currency','InvestType','IsNot']}).then(response => {
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
      queryEntInvestList(this.listQuery).then(response => {
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
      this.curentinvestid = ""
      this.viewDataDialogVisible = true
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该家族成员信息？', '提示', confirm).then(() => {
        deleteEntInvest(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curentinvestid = row.id;
      this.viewDataDialogVisible = true;
    }
  }
}
</script>