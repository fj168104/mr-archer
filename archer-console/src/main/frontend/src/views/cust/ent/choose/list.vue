<template>
  <el-container>
    <el-header>
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
        <el-table-column label="ID" prop="id" align="center" width="250">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="客户名称" width="300">
          <template slot-scope="scope">
            <a @click="viewData(scope.row)">{{ scope.row.name }}</a>
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

        <el-table-column label="创建人"  align="center" width="130">
          <template slot-scope="scope">
            <span>{{ scope.row.createusername }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建时间"  align="center" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建机构"  align="center" width="200">
          <template slot-scope="scope">
            <span>{{ scope.row.createorgname }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button @click="$emit('confirmChooseCustomer', scope.row)" type="primary">确认</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </el-main>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryDataList } from '@/api/cust/xw'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'EntChooseList',
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
          name: '',
          certid:''
        },
        list: null
      },
      codemap : {}
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
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
    }
  }
}
</script>