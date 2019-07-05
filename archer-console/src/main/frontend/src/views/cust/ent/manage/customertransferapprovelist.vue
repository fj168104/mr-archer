<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="客户名称">
          <el-input v-model="listQuery.filters.customername" placeholder="客户名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
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

      <el-table-column label="转出状态" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.status | showCodeName(codemap.CustTransferStatus)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="权限类型" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.righttype | showCodeName(codemap.CustTransferType)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="客户名称" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.customername}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="证件类型" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.certtype | showCodeName(certTypeOption)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="证件号码" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.certid}}</span>
        </template>
      </el-table-column>

      <el-table-column label="转出申请日期"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.applydate}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="转出人" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.createusername}}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="status == '02'" label="转出确认日期"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.confirmdate}}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="status == '03'" label="转出拒绝日期"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.refusedate}}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="status == '01'" label="操作" align="center" width="180" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button-group>
            <el-button @click="confirmData(scope.row)" type="primary">确认</el-button>
            <el-button @click="refuseData(scope.row)" type="danger">拒绝</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryCustomerTransferApproveList,updateCustomerTransferStatus,createCustomerTransfer } from '@/api/cust/customertransfer'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'CustomerTransferApproveList',
  components: { Pagination },
  directives: { waves },
  filters: {},
  props:{
    status: String
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
          customername: '',
          status: this.status
        },
        list: null
      },
      certTypeOption: [],
      codemap: {}
    }
  },
  watch: {
    status:function(newv, oldv){
      this.listQuery.filters.status = newv
      this.getList()
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','PersonCertType','CustTransferStatus','CustTransferType']}).then(response => {
      this.codemap = response.data
      this.certTypeOption = this.codemap.EntCertType.concat(this.codemap.PersonCertType)
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryCustomerTransferApproveList(this.listQuery).then(response => {
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
    confirmData(data){
      this.$confirm('确认该客户转移信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listQuery.listLoading = true
        updateCustomerTransferStatus({id:data.id,status:'02'}).then(response => {
          this.listQuery.listLoading = false
          this.getList()
        }).catch(() => {
          this.$message.info("更新数据失败！")
          this.listQuery.listLoading = false
        })
      })
    },
    refuseData(data){
      this.$confirm('拒绝该客户转移信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listQuery.listLoading = true
        updateCustomerTransferStatus({id:data.id,status:'03'}).then(response => {
          this.listQuery.listLoading = false
          this.getList()
        }).catch(() => {
          this.$message.info("更新数据失败！")
          this.listQuery.listLoading = false
        })
      })
    }
  }
}
</script>