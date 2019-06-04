import Mock from 'mockjs'

const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    username: 'admin',
    perms: [
      {name:'all', val:'*'}
    ]
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    username: 'editor',
    perms: [
      {name:'system', val:'m:sys'},
      {name:'permission', val:'m:sys:permission'},
      {name:'directive', val:'m:sys:directive'}
      ]
  }
}

const uList = []
const count = 100

for (let i = 0; i < count; i++) {
  uList.push(Mock.mock({
    id: '@increment',
    createTime: +Mock.Random.date('T'),
    username: '@first',
    nick: '@first',
    mail: '@first' + '@xxx.com',
    phone: '@integer(1, 11)',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    'type|1': ['CN', 'US', 'JP', 'EU'],
    'status|1': ['published', 'draft', 'deleted'],
    'lock|1': ['1', '0'],
    roleList: [{id:1, roleValue: 'admin', roleName: 'admin'}, {id:2, roleValue: 'editor', roleName: 'editor'}, {id:3, roleValue: 'visitor', roleName: 'visitor'}]

  }))
}

export default [
  // user login
  {
    url: '/auth/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          code: 60204,
          message: 'Account and password are incorrect.'
        }
      }

      return {
        code: 20000,
        data: token
      }
    }
  },

  // get user info
  {
    url: '/auth/info\.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          code: 50008,
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 20000,
        data: info
      }
    }
  },

  // user logout
  {
    url: '/auth/logout',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: 'success'
      }
    }
  },

  // user roles
  {
    url: '/user/role',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: 'success'
      }
    }
  },

  // user delete
  {
    url: '/user/[A-Za-z0-9]',
    type: 'delete',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  },

  {
    url: '/user/create',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: {id : 1000}
      }
    }
  },

  {
    url: '/user/update',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: 'success'
      }
    }
  },

  //list with page
  {
    url: '/user/list',
    type: 'post',
    response: config => {
      const { username, nick, page = 1, limit = 20, sort } = config.body

      let mockList = uList.filter(item => {
        if (username && item.username.indexOf(username) < 0) return false
        if (nick && item.nick.indexOf(nick) < 0) return false
        return true
      })

      if (sort === '-id') {
        mockList = mockList.reverse()
      }

      const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

      return {
        code: 20000,
        data: {
          total: mockList.length,
          records: pageList
        }
      }
    }
  }
]
