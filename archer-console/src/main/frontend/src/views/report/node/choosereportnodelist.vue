<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="节点标题">
          <el-input v-model="listQuery.filters.title" placeholder="节点标题" @keyup.enter.native="handleFilter"></el-input>
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
      <el-table-column label="ID" prop="id" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="节点标题">
        <template slot-scope="scope">
          <span>{{ scope.row.title }}</span>
        </template>
      </el-table-column>

      <el-table-column label="vue路径">
        <template slot-scope="scope">
          <span>{{ scope.row.vuepath }}</span>
        </template>
      </el-table-column>

      <el-table-column label="节点描述">
        <template slot-scope="scope">
          <span>{{ scope.row.nodedesc }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <el-tooltip content="确认选择" placement="top">
            <el-button @click="$emit('confirmChoose', scope.row.id)" type="primary">确认选择</el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryReportNodeList } from '@/api/report/reportnode'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'ChooseReportNodeList',
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
          title: '',
          isinuse: '1'
        },
        list: null
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryReportNodeList(this.listQuery).then(response => {
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