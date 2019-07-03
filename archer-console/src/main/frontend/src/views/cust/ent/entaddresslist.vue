<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="地址信息">
          <el-input v-model="listQuery.filters.address" placeholder="地址信息" @keyup.enter.native="handleFilter"></el-input>
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

      <el-table-column label="地址类型"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.addtype | showCodeName(codemap.AddressType)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="所属国家/地区" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.country | showCodeName(codemap.CountryCode)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="所属城市" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.city | showCodeName(codemap.AreaCode)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="地址详情">
        <template slot-scope="scope">
          <span>{{ scope.row.address}}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <el-tooltip content="详情" placement="top">
            <el-button @click="viewData(scope.row)" size="medium" type="primary" icon="el-icon-edit" circle plain></el-button>
          </el-tooltip>

          <el-tooltip content="删除" placement="top">
            <el-button v-if="isedit" @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="'客户地址详情'" :append-to-body="true" :visible.sync="viewDataDialogVisible" v-if="viewDataDialogVisible" width="70%">
      <ent-address :isedit="isedit" :curid="curid" :curcustomerid="curcustomerid" @refreshList="getList"></ent-address>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryEntAddressList, deleteEntAddress } from '@/api/cust/entaddress'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import EntAddress from '@/views/cust/ent/entaddress'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'EntAddressList',
  components: { Pagination,EntAddress },
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
      curid : ''
    }
  },
  created() {
    queryCodeList({codelist:['AddressType','CountryCode','AreaCode']}).then(response => {
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
      queryEntAddressList(this.listQuery).then(response => {
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
      this.curid = ""
      this.viewDataDialogVisible = true
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该信息？', '提示', confirm).then(() => {
        deleteEntAddress(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.curid = row.id;
      this.viewDataDialogVisible = true;
    }
  }
}
</script>