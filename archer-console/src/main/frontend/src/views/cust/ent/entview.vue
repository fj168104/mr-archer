<template>
  <el-container>
    <el-container>
      <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
        <!-- <el-tree :data="treedata" node-key="id" default-expand-all :props="defaultProps" @node-click="handleNodeClick"></el-tree> -->
        <el-menu
          default-active="1010"
          class="el-menu-vertical-demo"
          default-openeds="['1000','2000']"
          @select="selectMenu">
          <el-submenu index="1000">
            <template slot="title">
              <span>客户基本信息</span>
            </template>
              <el-menu-item index="1010">客户概况</el-menu-item>
              <el-menu-item index="1020">高管信息</el-menu-item>
              <el-menu-item index="1030">法人代表家族成员信息</el-menu-item>
              <el-menu-item index="1040">股东信息</el-menu-item>
              <el-menu-item index="1050">对外股权投资情况</el-menu-item>
              <el-menu-item index="1060">上下游关联方情况</el-menu-item>
              <el-menu-item index="1070">客户涉诉信息</el-menu-item>
          </el-submenu>
          <el-submenu index="2000">
            <template slot="title">
              <span>客户财务信息</span>
            </template>
              <el-menu-item index="2010">财务报表</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <ent-info :curcustomerid="curcustomerid" v-if="curnodeid==='1010'"></ent-info>
        <ent-manager-list :curcustomerid="curcustomerid" v-if="curnodeid==='1020'"></ent-manager-list>
        <ent-member-list :curcustomerid="curcustomerid" v-if="curnodeid==='1030'"></ent-member-list>
        <ent-stock-list :curcustomerid="curcustomerid" v-if="curnodeid==='1040'"></ent-stock-list>
        <ent-invest-list :curcustomerid="curcustomerid" v-if="curnodeid==='1050'"></ent-invest-list>
        <ent-relate-list :curcustomerid="curcustomerid" v-if="curnodeid==='1060'"></ent-relate-list>
        <customer-legal-list :curcustomerid="curcustomerid" v-if="curnodeid==='1070'"></customer-legal-list>
        <fin-base-info-list :curcustomerid="curcustomerid" v-if="curnodeid==='2010'"></fin-base-info-list>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import EntInfo from '@/views/cust/ent/entinfo'
import EntStockList from '@/views/cust/ent/entstocklist'
import EntManagerList from '@/views/cust/ent/entmanagerlist'
import EntMemberList from '@/views/cust/ent/entmemberlist'
import EntInvestList from '@/views/cust/ent/entinvestlist'
import EntRelateList from '@/views/cust/ent/entrelatelist'
import CustomerLegalList from '@/views/cust/ent/customerlegallist'
import FinBaseInfoList from '@/views/fin/data/finbaseinfolist'


export default {
  name: 'EntView',
  components: { EntInfo,EntStockList,EntManagerList,EntMemberList,EntInvestList,EntRelateList,CustomerLegalList,FinBaseInfoList },
  directives: { waves },
  filters: {},
  props: {
    curcustomerid: String
  },
  data() {
    return {
      /*treedata:[
        {
          id: '1000',
          label: '客户基本信息',
          children: [
            {
              id: '1010',
              label: '客户概况',
            },
            {
              id: '1020',
              label: '高管信息',
            },
            {
              id: '1030',
              label: '法人代表家族成员信息',
            },
            {
              id: '1040',
              label: '股东信息',
            },
            {
              id: '1050',
              label: '对外股权投资情况',
            },
            {
              id: '1060',
              label: '上下游关联方情况',
            },
            {
              id: '1070',
              label: '客户涉诉信息',
            }
          ]
        },{
           id: '2000',
          label: '客户财务信息',
          children: [
            {
              id: '2010',
              label: '财务报表',
            }
          ]
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },*/
      customerid: this.curcustomerid,
      curnodeid: '1010'
    }
  },
  watch: {
    curcustomerid : function(newV, oldV){
      this.curnodeid = '1010'
    }
  },
  created() {
  },
  methods: {
    handleNodeClick(data){
      if (data.id !== '1000' && data.id !== '2000') {
        this.curnodeid = data.id;
      }
    },
    selectMenu(idx){
      if (idx !== '1000' && idx !== '2000') {
        this.curnodeid = idx;
      }
    }
  }
}
</script>