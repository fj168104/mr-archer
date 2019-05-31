import { asyncRoutes, constantRoutes } from '@/router'

/**
 * Use meta.role to determine if the current user has permission
 * @param perms
 * @param route
 */
function hasPermission(perms, route) {
  // if (route.meta && route.meta.roles) {
  //   return roles.some(role => route.meta.roles.includes(role))
  // } else {
  //   return true
  // }

  if(route.path === '*'){
    return true
  }
  // 如果没有声明meta或者meta.perm，都视为可以公共访问的路由
  if (!route.meta || !route.meta.perm) {
    return false
  }

  return perms.some(p => p.val == route.meta.perm)
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param perms
 */
export function filterAsyncRoutes(routes, perms) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(perms, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, perms)
      }
      res.push(tmp)
    }
  })

  return res
}

export function doGenerateRoutes(perms) {
  let accessedRoutes
  if (perms.some(p => p.val == '*')) {
    accessedRoutes = asyncRoutes || []
  } else {
    accessedRoutes = filterAsyncRoutes(asyncRoutes, perms)
  }

  return accessedRoutes;
}



const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, perms) {
    return new Promise(resolve => {
      let accessedRoutes
      if (perms.some(p => p.val == '*')) {
        accessedRoutes = asyncRoutes || []
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, perms)
      }
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
