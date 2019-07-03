export function print(content, header = '', w = null, h = null) {
    // Fixes dual-screen position                         Most browsers      Firefox
    const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left;
    const dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top;

    const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
    w = +w === 0 ? width : w;
    h = +h === 0 ? height : h;
    const left = ((width / 2) - (w / 2)) + dualScreenLeft;
    const top = ((height / 2) - (h / 2)) + dualScreenTop;
    var myWindow = window.open("", "打印", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=" + w + ", height=" + h + ", top=" + top + ", left=" + left);
    var style = '';
    //style += "<style type='text/css'></style>";
    myWindow.document.write(content + style);
    myWindow.document.getElementsByTagName("head")[0].innerHTML = header
    myWindow.focus();
    myWindow.document.close();     //关闭document的输出流, 显示选定的数据
    myWindow.print();   //打印当前窗口
    return myWindow;
}