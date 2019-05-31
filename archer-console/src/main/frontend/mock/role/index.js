import Mock from 'mockjs'
import { deepClone } from '../../src/utils/index.js'
import { asyncRoutes, constantRoutes } from './routes.js'

const routes = deepClone([...constantRoutes, ...asyncRoutes])

const roles = [
  {
    id: 1,
    roleValue: 'admin',
    roleName: 'admin',
    roleDesc: 'Super Administrator. Have access to view all pages.',
    // routes: routes,
    perms:[
      {name:'all', val:'*'}
    ]
  },
  {
    id: 2,
    roleValue: 'editor',
    roleName: 'editor',
    roleDesc: 'Normal Editor. Can see all pages except permission page',
    // routes: routes.filter(i => i.path !== '/permission'),// just a mock
    perms:[
      {name:'system', val:'m:sys'},
      {name:'permission', val:'m:sys:permission'},
      {name:'directive', val:'m:sys:directive'}
      ]
  },
  {
    id: 3,
    roleValue: 'visitor',
    roleName: 'visitor',
    roleDesc: 'Just a visitor. Can only see the home page and the document page',
    routes: [{
      path: '',
      redirect: 'dashboard',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          meta: { title: 'dashboard', icon: 'dashboard' }
        }
      ]
    }]
  }
]

export default [
  // mock get all routes form server
  {
    url: '/routes',
    type: 'get',
    response: _ => {
      return {
        code: 20000,
        data: routes
      }
    }
  },

  // mock get all roles form server
  {
    url: '/roles',
    type: 'get',
    response: _ => {
      return {
        code: 20000,
        data: roles
      }
    }
  },

  // add role
  {
    url: '/role/add',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: roles[2]
      }
    }
  },

  // update role
  {
    url: '/role/update',
    type: 'put',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  },

  // delete role
  {
    url: '/role/delete/[A-Za-z0-9]',
    type: 'delete',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  },

  // sync perm
  {
    url: '/perm/sync',
    type: 'post',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  }

]
