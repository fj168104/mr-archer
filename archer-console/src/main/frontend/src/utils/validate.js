/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  // const valid_map = ['admin', 'editor']
  // return valid_map.indexOf(str.trim()) >= 0
  return true
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
  if (typeof str === 'string' || str instanceof String) {
    return true
  }
  return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
  if (typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

/**
 * 判断是否超过最大长度
 * @param src
 * @param {number} maxlegnth
 * @returns {Boolean}
 */
export function maxLength(src, maxlegnth) {
  if (typeof src === 'undefined') {
    return true;
  }
  var realLength = 0, len = str.length, charCode = -1;
  for (var i = 0; i < len; i++) {
      charCode = str.charCodeAt(i);
      if (charCode >= 0 && charCode <= 128) 
        realLength += 1;
      else
        realLength += 2;
  }
  return maxlegnth >= realLength;
}

/**
 * 返回字符文本的长度：中文2，英文1
 * @param src
 * @returns {number}
 */
export function getTextLength(src) {
  var realLength = 0, len = str.length, charCode = -1;
  for (var i = 0; i < len; i++) {
      charCode = str.charCodeAt(i);
      if (charCode >= 0 && charCode <= 128) 
        realLength += 1;
      else
        realLength += 2;
  }
  return realLength;
}


/**
 * 字符最大长度验证
 * @param {*} val 
 * @param {*} obj 
 * @param {*} col 
 */
export function validMaxLength(val, obj, col, max){
  if (val !== '') {
    var newval = ''
    var realLength = 0, len = val.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = val.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) 
          realLength += 1;
        else
          realLength += 2;
        if (max != null && realLength > max) {
          obj[col] = newval
          this.$message.error('字符长度最大为' + max + '！（中文占2字符）');
          return
        }
        newval += String.fromCharCode(charCode)
    }
  }
}

/**
 * 整数验证
 * @param {*} val 
 * @param {*} obj 
 * @param {*} col 
 */
export function validInt(val, obj, col){
  if (val !== '' && val != '0') {
    // let reg = new RegExp("^((\\+|-)?[1-9])\\d*$")
    let reg = /^((\+|-)?[1-9])\d*$/
    if (!reg.test(val)) {
      obj[col] = ''
      this.$message.error('请输入正确的整数格式');
    }
  }
}

/**
 * 数字验证
 * @param {*} val 
 * @param {*} obj 
 * @param {*} col 
 */
export function validNumber(val, obj, col){
  if (val !== '' && val != '0') {
    // let reg = new RegExp("^(-?\\d+)(\\.\\d+)?$")
    let reg = /(^((\+|-)?[1-9])\d*(\.\d+)?$)|(^((\+|-)?0)(\.\d+)?$)/
    if (!reg.test(val)) {
      obj[col] = ''
      this.$message.error('请输入正确的数字格式');
    }
  }
}
